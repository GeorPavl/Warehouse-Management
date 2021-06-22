package com.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockDTO {

    private Long productId;

    private String date;

    private Integer stock;
}
