package com.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductIndexDTO {

    private Long id;

    private String title;

    private String barcode;

    private String description;

    private BigDecimal price;

    private Long invoiceId;

    private Long rackId;

    private Long warehouseId;
}
