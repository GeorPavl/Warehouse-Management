package com.api.service;

import com.api.dto.InvoiceDTO;
import com.api.dto.InvoiceIndexDTO;
import com.api.model.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice dtoToEntity(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> findAll();

    Invoice get(Long id);

    Invoice save(InvoiceDTO invoiceDTO);

    //Invoice update(InvoiceDTO invoiceDTO);

    void delete(Long id);

    List<InvoiceDTO> index(InvoiceIndexDTO search);
}
