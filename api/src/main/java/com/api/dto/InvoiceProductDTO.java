package com.api.dto;

import com.api.model.InvoiceProduct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class InvoiceProductDTO {

    // TODO: 6/17/2021 Το dto να περιέχει και το invoiceType του invoice

    private Long id;

    private Date actionDate;

    private Integer quantity;

    private Long rackId;

    private Long productId;

    private Long invoiceId;

    /** constructors */

    public InvoiceProductDTO(InvoiceProduct invoiceProduct){
        BeanUtils.copyProperties(invoiceProduct,this);
        this.rackId = invoiceProduct.getRack().getId();
        this.productId = invoiceProduct.getProduct().getId();
        this.invoiceId = invoiceProduct.getInvoice().getId();
    }
}
