package com.api.service;

import com.api.dto.InvoiceProductDTO;
import com.api.dto.RackDTO;
import com.api.model.*;
import com.api.repository.RackRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RackServiceImpl implements RackService{

    @Autowired
    private RackRepository rackRepository;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceProductService invoiceProductService;

    @Override
    public Rack dtoToEntity(RackDTO rackDTO) {
        Rack rack = new Rack();
        BeanUtils.copyProperties(rackDTO,rack);
        if (rackDTO.getWarehouseId() != null){
            Warehouse warehouse = warehouseService.get(rackDTO.getWarehouseId());
            if (warehouse != null){
                rack.setWarehouse(warehouse);
            }
        }
        if (rackDTO.getInvoices() != null){
            List<InvoiceProduct> invoices = new ArrayList<>();
            for (InvoiceProductDTO invoiceProductDTO : rackDTO.getInvoices()){
                invoices.add(invoiceProductService.dtoToEntity(invoiceProductDTO));
            }
            rack.setInvoices(invoices);
        }
        return rack;
    }

    @Override
    public List<RackDTO> findAll() {
        List<RackDTO> rackDTOS = new ArrayList<>();
        for (Rack rack : rackRepository.findAll()){
            rackDTOS.add(new RackDTO(rack));
        }
        return rackDTOS;
    }

    @Override
    public Rack get(Long id) {
        Optional<Rack> rack = rackRepository.findById(id);
        if (rack.isEmpty()){
            throw new RuntimeException("Did not find rack id: " + id);
        }
        return rack.get();
    }

    // TODO: 6/15/2021 Όταν κάνω create και update μπορώ να προσθέσω και κανούργιες ενέργειες αποδείξεων

    @Override
    public Rack save(RackDTO rackDTO) {
        Rack rack = new Rack();
        BeanUtils.copyProperties(rackDTO, rack);
        if (rackDTO.getWarehouseId() != null){
            Warehouse warehouse = warehouseService.get(rackDTO.getWarehouseId());
            rack.setWarehouse(warehouse);
        }
        if (rackDTO.getInvoices() != null){
            for (InvoiceProductDTO invoiceProductDTO : rackDTO.getInvoices()){
//                Invoice invoice = invoiceService.get(invoiceProductDTO.getInvoiceId());
//                Product product = productService.get(invoiceProductDTO.getProductId());
                InvoiceProduct invoiceProduct = invoiceProductService.dtoToEntity(invoiceProductDTO);
                rack.addInvoiceProduct(invoiceProduct);
//                rack.addInvoiceProduct(new InvoiceProduct(product,invoice,invoiceProductDTO.getQuantity()));
            }
        }
        return rackRepository.save(rack);
    }

//    @Override
//    public Rack update(RackDTO rackDTO) {
//        Rack rack = get(rackDTO.getId());
//        if (rack != null){
//            BeanUtils.copyProperties(rackDTO,rack);
//            if (rackDTO.getWarehouseId() != null){
//                rack.setWarehouse(warehouseService.get(rackDTO.getWarehouseId()));
//            }
//            if (rackDTO.getInvoices() != null){
//                rack.setInvoices(new ArrayList<>());
//                for (InvoiceProductDTO invoiceProductDTO : rackDTO.getInvoices()){
//                    Invoice invoice = invoiceService.get(invoiceProductDTO.getInvoiceId());
//                    Product product = productService.get(invoiceProductDTO.getProductId());
//                    rack.addInvoiceProduct(new InvoiceProduct(product,invoice,invoiceProductDTO.getQuantity()));
//                }
//            }
//
//        }
//        return rackRepository.save(rack);
//    }

    @Override
    public void delete(Long id) {
        if (get(id) != null){
            rackRepository.deleteById(id);
        }
    }
}
