package com.api.service;

import com.api.dto.RackDTO;
import com.api.dto.WarehouseDTO;
import com.api.dto.WarehouseIndexDTO;
import com.api.model.Rack;
import com.api.model.Warehouse;
import com.api.repository.WarehouseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService{

    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private RackService rackService;

    @Override
    public Warehouse dtoToEntity(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO,warehouse);
        if (warehouseDTO.getRacks() != null){
            List<Rack> racks = new ArrayList<>();
            for (RackDTO rackDTO : warehouseDTO.getRacks()){
                racks.add(rackService.dtoToEntity(rackDTO));
            }
            warehouse.setRacks(racks);
        }
        return warehouse;
    }

    @Override
    public List<WarehouseDTO> findAll() {
        List<WarehouseDTO> warehouseDTOS = new ArrayList<>();
        for (Warehouse warehouse : warehouseRepository.findAll()){
            warehouseDTOS.add(new WarehouseDTO(warehouse));
        }
        return warehouseDTOS;
    }

    @Override
    public Warehouse get(Long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        if (warehouse.isEmpty()){
            throw new RuntimeException("Did not find warehouse id: " + id);
        }
        return warehouse.get();
    }

    /**
     * Όταν κάνω save μπορώ να κάνω save και ράφια */

    // TODO: 6/18/2021 Όταν κάνω update, δίνει καινούργιο id στα ράφια
    // TODO: 6/18/2021 Μάλλον διορθώνεται από όπως είναι στα σχόλια σε όπως είναι τώρα 

    @Override
    public Warehouse save(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDTO,warehouse);
        if (warehouseDTO.getRacks() != null){
            for (RackDTO rackDTO : warehouseDTO.getRacks()){
                Rack rack = rackService.dtoToEntity(rackDTO);
                warehouse.addRack(rack);
                //warehouse.addRack(new Rack(rackDTO.getCode(),warehouse));
            }
        }
        return warehouseRepository.save(warehouse);
    }

    @Override
    public void delete(Long id) {
        if (get(id) != null){
            warehouseRepository.deleteById(id);
        }
    }

    @Override
    public List<WarehouseDTO> index(WarehouseIndexDTO warehouseIndexDTO) {
        return warehouseRepository.warehouseQuery(warehouseIndexDTO);
    }
}
