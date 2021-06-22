package com.api.dto;

import com.api.model.Invoice;
import com.api.model.InvoiceProduct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class InvoiceDTO {

    private Long id;

    private String description;

    private Date date;

    private Boolean invoiceType;

    private String invoiceTypeString;

    private Long employeeId;

    private String employeeName;

    private List<InvoiceProductDTO> invoiceProducts;

    /** constructors */

    public InvoiceDTO(Invoice invoice){
        if (invoice.isInvoiceType()) {
            this.invoiceTypeString = "ΔΕΛΤΙΟ ΕΙΣΟΔΟΥ";
        }else {
            this.invoiceTypeString = "ΔΕΛΤΙΟ ΕΞΟΔΟΥ";
        }
        BeanUtils.copyProperties(invoice,this);
        this.employeeId = invoice.getEmployee().getId();
        this.employeeName = invoice.getEmployee().getLastName();
        invoiceProducts = new ArrayList<>();
        if (invoice.getInvoiceProducts() != null){
            for (InvoiceProduct invoiceProduct : invoice.getInvoiceProducts()){
                invoiceProducts.add(new InvoiceProductDTO(invoiceProduct));
            }
        }
    }
}
