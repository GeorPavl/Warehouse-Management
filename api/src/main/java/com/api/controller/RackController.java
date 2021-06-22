package com.api.controller;

import com.api.dto.RackDTO;
import com.api.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rc")
public class RackController {

    @Autowired
    private RackService rackService;

    @GetMapping("/list")
    public List<RackDTO> getRacksList(){
        return rackService.findAll();
    }

    @GetMapping("/get")
    public RackDTO getRack(@RequestParam Long id){
        return new RackDTO(rackService.get(id));
    }

    @PostMapping("/save")
    public RackDTO saveRack(@RequestBody RackDTO rackDTO){
        return new RackDTO(rackService.save(rackDTO));
    }

    @DeleteMapping("/delete")
    public void deleteRack(@RequestParam Long id){
        rackService.delete(id);
    }
}
