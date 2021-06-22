package com.api.controller;

import com.api.dto.InvoiceProductDTO;
import com.api.dto.InvoiceProductIndexDTO;
import com.api.service.InvoiceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/np")
public class InvoiceProductController {

    @Autowired
    private InvoiceProductService invoiceProductService;

    @GetMapping("/list")
    public List<InvoiceProductDTO> getInvoiceProductsList(){
        return invoiceProductService.findAll();
    }

    @GetMapping("/get")
    public InvoiceProductDTO getInvoiceProduct(@RequestParam Long id){
        return new InvoiceProductDTO(invoiceProductService.get(id));
    }

    @PostMapping("/save")
    public InvoiceProductDTO saveInvoiceProduct(@RequestBody InvoiceProductDTO invoiceProductDTO){
        return new InvoiceProductDTO(invoiceProductService.save(invoiceProductDTO));
    }

    @DeleteMapping("/delete")
    public void deleteInvoiceProduct(@RequestParam Long id){
        invoiceProductService.delete(id);
    }

    @PostMapping("/index")
    public List<InvoiceProductIndexDTO> indexInvoiceProduct(@RequestBody InvoiceProductIndexDTO invoiceProductIndexDTO){
        return invoiceProductService.index(invoiceProductIndexDTO);
    }
}
