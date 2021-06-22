package com.api.service;

import com.api.dto.InvoiceProductDTO;
import com.api.dto.ProductDTO;
import com.api.dto.ProductIndexDTO;
import com.api.dto.ProductStockDTO;
import com.api.model.InvoiceProduct;
import com.api.model.Product;
import com.api.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private RackService rackService;
    @Autowired
    private InvoiceProductService invoiceProductService;

    @Override
    public Product dtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        if (productDTO.getInvoices() != null){
            List<InvoiceProduct> invoiceProducts = new ArrayList<>();
            for (InvoiceProductDTO invoiceProductDTO : productDTO.getInvoices()){
                invoiceProducts.add(invoiceProductService.dtoToEntity(invoiceProductDTO));
            }
            product.setInvoices(invoiceProducts);
        }
        return product;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : productRepository.findAll()){
            productDTOS.add(new ProductDTO(product));
        }
        return productDTOS;
    }

    @Override
    public Product get(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new RuntimeException("Did not find product id: " + id);
        }
        return product.get();
    }

    // TODO: 6/15/2021 Όταν κάνω create και update μπορώ να προσθέσω και κανούργιες ενέργειες αποδείξεων

    @Override
    public Product save(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        if (productDTO.getInvoices() != null){
            List<InvoiceProduct> invoiceProducts = new ArrayList<>();
            for (InvoiceProductDTO invoiceProductDTO : productDTO.getInvoices()){
//                Invoice invoice = invoiceService.get(invoiceProductDTO.getInvoiceId());
//                Rack rack = rackService.get(invoiceProductDTO.getRackId());
//                product.addInvoiceProduct(new InvoiceProduct(rack,invoice,invoiceProductDTO.getQuantity()));
                InvoiceProduct invoiceProduct = invoiceProductService.dtoToEntity(invoiceProductDTO);
                product.addInvoiceProduct(invoiceProduct);
            }
        }
        return productRepository.save(product);
    }

//    @Override
//    public Product update(ProductDTO productDTO) {
//        Product product = get(productDTO.getId());
//        if (product != null){
//            BeanUtils.copyProperties(productDTO,product);
//            if (productDTO.getInvoices() != null){
//                product.setInvoices(new ArrayList<>());
//                for (InvoiceProductDTO invoiceProductDTO : productDTO.getInvoices()){
//                    Invoice invoice = invoiceService.get(invoiceProductDTO.getInvoiceId());
//                    Rack rack = rackService.get(invoiceProductDTO.getRackId());
//                    product.addInvoiceProduct(new InvoiceProduct(rack,invoice,invoiceProductDTO.getQuantity()));
//                }
//            }
//        }
//        return productRepository.save(product);
//    }

    @Override
    public void delete(Long id) {
        if (get(id) != null){
            productRepository.deleteById(id);
        }
    }

    @Override
    public List<ProductIndexDTO> index(ProductIndexDTO productIndexDTO) {
        return productRepository.productQuery(productIndexDTO);
    }

    @Override
    public ProductStockDTO getStock(ProductStockDTO productStockDTO) throws ParseException {
        List<InvoiceProduct> invoices = invoiceProductService.findByProductIdAndActionDate(productStockDTO);
        productStockDTO.setStock(0);
        for (InvoiceProduct tempInvoiceProduct : invoices){
            if (tempInvoiceProduct.getInvoice().isInvoiceType()){
                productStockDTO.setStock(productStockDTO.getStock() + tempInvoiceProduct.getQuantity());
            }else{
                productStockDTO.setStock(productStockDTO.getStock() - tempInvoiceProduct.getQuantity());
            }
        }
        return productStockDTO;
    }
}
