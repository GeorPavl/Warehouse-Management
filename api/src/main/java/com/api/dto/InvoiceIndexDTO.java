package com.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InvoiceIndexDTO {

    private Long id;

    private String description;

    private Date afterDate;

    private Date beforeDate;

    private Boolean invoiceType;

    private String invoiceTypeString;

    private Long employeeId;

    private Long warehouseId;

    private Long rackId;

    private Long productId;
}
