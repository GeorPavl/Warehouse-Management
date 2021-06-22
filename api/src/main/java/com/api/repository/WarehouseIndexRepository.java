package com.api.repository;

import com.api.dto.WarehouseDTO;
import com.api.dto.WarehouseIndexDTO;

import java.util.List;

public interface WarehouseIndexRepository {

    List<WarehouseDTO> warehouseQuery(WarehouseIndexDTO warehouseIndexDTO);
}
