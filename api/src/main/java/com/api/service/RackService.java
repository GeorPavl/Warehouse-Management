package com.api.service;

import com.api.dto.RackDTO;
import com.api.model.Rack;

import java.util.List;

public interface RackService {

    Rack dtoToEntity(RackDTO rackDTO);

    List<RackDTO> findAll();

    Rack get(Long id);

    Rack save(RackDTO rack);

    //Rack update(RackDTO rack);

    void delete(Long id);
}
