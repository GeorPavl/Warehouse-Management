package com.api.dto;

import com.api.model.InvoiceProduct;
import com.api.model.Rack;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RackDTO {

    private Long id;

    private String code;

    private Long warehouseId;

    private List<InvoiceProductDTO> invoices;

    /** constructors */

    public RackDTO(Rack rack){
        BeanUtils.copyProperties(rack,this);
        this.warehouseId = rack.getWarehouse().getId();
        invoices = new ArrayList<>();
        if (rack.getInvoices() != null){
            for (InvoiceProduct invoiceProduct : rack.getInvoices()){
                invoices.add(new InvoiceProductDTO(invoiceProduct));
            }
        }
    }
}
