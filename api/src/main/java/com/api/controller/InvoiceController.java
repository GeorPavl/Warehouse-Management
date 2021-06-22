package com.api.controller;

import com.api.dto.InvoiceDTO;
import com.api.dto.InvoiceIndexDTO;
import com.api.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nv")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/list")
    public List<InvoiceDTO> getInvoicesList(){
        return invoiceService.findAll();
    }

    @GetMapping("/get")
    public InvoiceDTO getInvoice(@RequestParam Long id){
        return new InvoiceDTO(invoiceService.get(id));
    }

    @PostMapping("/save")
    public InvoiceDTO saveInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return new InvoiceDTO(invoiceService.save(invoiceDTO));
    }

    @DeleteMapping("/delete")
    public void deleteInvoice(@RequestParam Long id){
        invoiceService.delete(id);
    }

    @PostMapping("/index")
    public List<InvoiceDTO> indexInvoice(@RequestBody InvoiceIndexDTO invoiceIndexDTO){
        return invoiceService.index(invoiceIndexDTO);
    }
}
