package com.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvoiceProductIndexDTO {

    private Long id;

    private Date actionDate;

    private Date afterDate;

    private Date beforeDate;

    private Integer quantity;

    private Long rackId;

    private String rackCode;

    private Long invoiceId;

    private Boolean invoiceType;

    private Long productId;

    private String productTitle;

    private String productBarcode;

    private Long employeeId;

    private String employeeLastName;
}
