package com.api.repository;

import com.api.dto.InvoiceDTO;
import com.api.dto.InvoiceIndexDTO;

import java.util.List;

public interface InvoiceIndexRepository {

    List<InvoiceDTO> invoiceQuery(InvoiceIndexDTO search);

}
