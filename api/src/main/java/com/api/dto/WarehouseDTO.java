package com.api.dto;

import com.api.model.Rack;
import com.api.model.Warehouse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WarehouseDTO {

    private Long id;

    private String name;

    private String description;

    private List<RackDTO> racks;

    /** constructors */

    public WarehouseDTO(Warehouse warehouse){
        BeanUtils.copyProperties(warehouse,this);
        racks = new ArrayList<>();
        if (warehouse.getRacks() != null){
            for (Rack rack : warehouse.getRacks()){
                if (rack != null){
                    racks.add(new RackDTO(rack));
                }
            }
        }
    }
}
