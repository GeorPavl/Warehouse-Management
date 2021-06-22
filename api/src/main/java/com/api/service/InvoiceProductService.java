package com.api.service;

import com.api.dto.InvoiceProductDTO;
import com.api.dto.InvoiceProductIndexDTO;
import com.api.dto.ProductStockDTO;
import com.api.model.InvoiceProduct;

import java.text.ParseException;
import java.util.List;

public interface InvoiceProductService {

    InvoiceProduct dtoToEntity(InvoiceProductDTO invoiceProductDTO);

    List<InvoiceProductDTO> findAll();

    InvoiceProduct get(Long id);

    InvoiceProduct save(InvoiceProductDTO invoiceProductDTO);

    //InvoiceProduct update(InvoiceProductDTO invoiceProductDTO);

    void delete(Long id);

    List<InvoiceProductIndexDTO> index(InvoiceProductIndexDTO invoiceProductIndexDTO);

    List<InvoiceProduct> findByProductIdAndActionDate(ProductStockDTO productStockDTO) throws ParseException;
}
