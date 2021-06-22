package com.api.dto;

import com.api.model.InvoiceProduct;
import com.api.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String title;

    private String barcode;

    private String description;

    private BigDecimal price;

    private List<InvoiceProductDTO> invoices;

    /** constructors */

    public ProductDTO(Product product){
        BeanUtils.copyProperties(product,this);
        invoices = new ArrayList<>();
        if (product.getInvoices() != null){
            for (InvoiceProduct invoiceProduct : product.getInvoices()){
                invoices.add(new InvoiceProductDTO(invoiceProduct));
            }
        }
    }
}
