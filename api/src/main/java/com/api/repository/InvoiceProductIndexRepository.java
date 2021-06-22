package com.api.repository;

import com.api.dto.InvoiceProductIndexDTO;

import java.util.List;

public interface InvoiceProductIndexRepository {

    List<InvoiceProductIndexDTO> invoiceProductQuery(InvoiceProductIndexDTO search);
}
