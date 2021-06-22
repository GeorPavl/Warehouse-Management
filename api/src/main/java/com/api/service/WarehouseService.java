package com.api.service;

import com.api.dto.WarehouseDTO;
import com.api.dto.WarehouseIndexDTO;
import com.api.model.Warehouse;

import java.util.List;

public interface WarehouseService {

    Warehouse dtoToEntity(WarehouseDTO warehouseDTO);

    List<WarehouseDTO> findAll();

    Warehouse get(Long id);

    Warehouse save(WarehouseDTO warehouse);

    void delete(Long id);

    List<WarehouseDTO> index(WarehouseIndexDTO warehouseIndexDTO);
}
