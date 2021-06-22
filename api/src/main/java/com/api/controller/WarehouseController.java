package com.api.controller;

import com.api.dto.WarehouseDTO;
import com.api.dto.WarehouseIndexDTO;
import com.api.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wr")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public List<WarehouseDTO> getWarehousesList(){
        return warehouseService.findAll();
    }

    @GetMapping("/get")
    public WarehouseDTO getWarehouse(@RequestParam Long id){
        return new WarehouseDTO(warehouseService.get(id));
    }

    @PostMapping("/save")
    public WarehouseDTO saveWarehouse(@RequestBody WarehouseDTO warehouseDTO){
        return new WarehouseDTO(warehouseService.save(warehouseDTO));
    }

    @DeleteMapping("/delete")
    public void deleteWarehouse(@RequestParam Long id){
        warehouseService.delete(id);
    }

    @PostMapping("/index")
    public List<WarehouseDTO> indexWarehouse(@RequestBody WarehouseIndexDTO warehouseIndexDTO){
        return warehouseService.index(warehouseIndexDTO);
    }
}
