package com.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseIndexDTO {

    private Long id;

    private String name;

    private String description;

    private Long rackId;
}
