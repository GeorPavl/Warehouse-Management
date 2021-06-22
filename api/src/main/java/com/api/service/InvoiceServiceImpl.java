package com.api.service;

import com.api.dto.InvoiceDTO;
import com.api.dto.InvoiceIndexDTO;
import com.api.dto.InvoiceProductDTO;
import com.api.model.*;
import com.api.repository.InvoiceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RackService rackService;
    @Autowired
    private InvoiceProductService invoiceProductService;

    @Override
    public Invoice dtoToEntity(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceDTO,invoice);
        if (invoiceDTO.getEmployeeId() != null){
            Employee employee = employeeService.get(invoiceDTO.getEmployeeId());
            if (employee != null){
                invoice.setEmployee(employee);
            }
        }
        if (invoiceDTO.getInvoiceProducts() != null){
            List<InvoiceProduct> invoiceProducts = new ArrayList<>();
            for (InvoiceProductDTO invoiceProductDTO : invoiceDTO.getInvoiceProducts()){
                invoice.addInvoiceProduct(invoiceProductService.dtoToEntity(invoiceProductDTO));
            }
            invoice.setInvoiceProducts(invoiceProducts);
        }
        return invoice;
    }

    @Override
    public List<InvoiceDTO> findAll() {
        List<InvoiceDTO> invoiceDTOS = new ArrayList<>();
        for (Invoice invoice : invoiceRepository.findAll()){
            invoiceDTOS.add(new InvoiceDTO(invoice));
        }
        return invoiceDTOS;
    }

    @Override
    public Invoice get(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()){
            throw new RuntimeException("Did not find invoice id: " + id);
        }
        return invoice.get();
    }

    @Override
    public Invoice save(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceDTO,invoice);
        if (invoiceDTO.getEmployeeId() != null){
            Employee employee = employeeService.get(invoiceDTO.getEmployeeId());
            invoice.setEmployee(employee);
        }
        if (invoiceDTO.getInvoiceProducts() != null){
            for (InvoiceProductDTO invoiceProductDTO : invoiceDTO.getInvoiceProducts()){
//                Rack rack = rackService.get(invoiceProductDTO.getRackId());
//                Product product = productService.get(invoiceProductDTO.getProductId());
                InvoiceProduct invoiceProduct = invoiceProductService.dtoToEntity(invoiceProductDTO);
                invoice.addInvoiceProduct(invoiceProduct);
//                invoice.addInvoiceProduct(new InvoiceProduct(product,rack,invoiceProductDTO.getQuantity()));
            }
        }
        return invoiceRepository.save(invoice);
    }

//    @Override
//    public Invoice update(InvoiceDTO invoiceDTO) {
//        Invoice invoice = get(invoiceDTO.getId());
//        if (invoice != null){
//            BeanUtils.copyProperties(invoiceDTO,invoice);
//            if (invoiceDTO.getEmployeeId() != null){
//                invoice.setEmployee(employeeService.get(invoiceDTO.getEmployeeId()));
//            }
//            if (invoiceDTO.getInvoiceProducts() != null){
//                invoice.setInvoiceProducts(new ArrayList<>());
//                for (InvoiceProductDTO invoiceProductDTO : invoiceDTO.getInvoiceProducts()){
//                    Rack rack = rackService.get(invoiceProductDTO.getRackId());
//                    Product product = productService.get(invoiceProductDTO.getProductId());
//                    invoice.addInvoiceProduct(new InvoiceProduct(product,rack,invoiceProductDTO.getQuantity()));
//                }
//            }
//        }
//        return invoiceRepository.save(invoice);
//    }

    @Override
    public void delete(Long id) {
        if (get(id) != null){
            invoiceRepository.deleteById(id);
        }
    }

    @Override
    public List<InvoiceDTO> index(InvoiceIndexDTO search) {
        List<InvoiceDTO> list = invoiceRepository.invoiceQuery(search);
        for (InvoiceDTO invoiceDTO : list) {
            if (invoiceDTO.getInvoiceType()) {
                invoiceDTO.setInvoiceTypeString("ΔΕΛΤΙΟ ΕΙΣΟΔΟΥ");
            }else {
                invoiceDTO.setInvoiceTypeString("ΔΕΛΤΙΟ ΕΞΟΔΟΥ");
            }
        }
        return list;
    }
}
