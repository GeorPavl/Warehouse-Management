package com.api.service;

import com.api.dto.InvoiceProductDTO;
import com.api.dto.InvoiceProductIndexDTO;
import com.api.dto.ProductStockDTO;
import com.api.model.InvoiceProduct;
import com.api.model.Product;
import com.api.repository.InvoiceProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService{

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private RackService rackService;
    @Autowired
    private ProductService productService;

    private static final String dateString = " 23:59:59";

    @Override
    public InvoiceProduct dtoToEntity(InvoiceProductDTO invoiceProductDTO) {
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        BeanUtils.copyProperties(invoiceProductDTO,invoiceProduct);
        if (invoiceProductDTO.getInvoiceId() != null){
            invoiceProduct.setInvoice(invoiceService.get(invoiceProductDTO.getInvoiceId()));
        }
        if (invoiceProductDTO.getProductId() != null){
            invoiceProduct.setProduct(productService.get(invoiceProductDTO.getProductId()));
        }
        if (invoiceProductDTO.getRackId() != null){
            invoiceProduct.setRack(rackService.get(invoiceProductDTO.getRackId()));
        }
        return invoiceProduct;
    }

    @Override
    public List<InvoiceProductDTO> findAll() {
        List<InvoiceProductDTO> invoiceProductDTOS = new ArrayList<>();
        for (InvoiceProduct invoiceProduct : invoiceProductRepository.findAll()){
            invoiceProductDTOS.add(new InvoiceProductDTO(invoiceProduct));
        }
        return invoiceProductDTOS;
    }

    @Override
    public InvoiceProduct get(Long id) {
        Optional<InvoiceProduct> invoiceProduct = invoiceProductRepository.findById(id);
        if (invoiceProduct.isEmpty()){
            throw new RuntimeException("Did not find invoiceProduct id: " + id);
        }
        return invoiceProduct.get();
    }

    @Override
    public InvoiceProduct save(InvoiceProductDTO invoiceProductDTO) {
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        BeanUtils.copyProperties(invoiceProductDTO,invoiceProduct);
        if (invoiceProductDTO.getInvoiceId() != null){
            invoiceProduct.setInvoice(invoiceService.get(invoiceProductDTO.getInvoiceId()));
        }
        if (invoiceProductDTO.getProductId() != null){
            invoiceProduct.setProduct(productService.get(invoiceProductDTO.getProductId()));
        }
        if (invoiceProductDTO.getRackId() != null){
            invoiceProduct.setRack(rackService.get(invoiceProductDTO.getRackId()));
        }
        return invoiceProductRepository.save(invoiceProduct);
    }

//    @Override
//    public InvoiceProduct update(InvoiceProductDTO invoiceProductDTO) {
//        InvoiceProduct invoiceProduct = get(invoiceProductDTO.getId());
//        BeanUtils.copyProperties(invoiceProductDTO,invoiceProduct);
//        if (invoiceProductDTO.getInvoiceId() != null){
//            invoiceProduct.setInvoice(invoiceService.get(invoiceProductDTO.getInvoiceId()));
//        }
//        if (invoiceProductDTO.getProductId() != null){
//            invoiceProduct.setProduct(productService.get(invoiceProductDTO.getProductId()));
//        }
//        if (invoiceProductDTO.getRackId() != null){
//            invoiceProduct.setRack(rackService.get(invoiceProductDTO.getRackId()));
//        }
//        return invoiceProductRepository.save(invoiceProduct);
//    }

    @Override
    public void delete(Long id) {
        if (get(id) != null){
            invoiceProductRepository.deleteById(id);
        }
    }

    @Override
    public List<InvoiceProductIndexDTO> index(InvoiceProductIndexDTO invoiceProductIndexDTO) {
        return invoiceProductRepository.invoiceProductQuery(invoiceProductIndexDTO);
    }

    @Override
    public List<InvoiceProduct> findByProductIdAndActionDate(ProductStockDTO productStockDTO) throws ParseException {
        Product product = productService.get(productStockDTO.getProductId());
        productStockDTO.setDate(productStockDTO.getDate() + dateString);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateObj = format.parse(productStockDTO.getDate());
        return invoiceProductRepository.findByProductIdAndActionDate(product,dateObj);
    }
}
