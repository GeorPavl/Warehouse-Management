package com.api.controller;

import com.api.dto.EmployeeDTO;
import com.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/em")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public List<EmployeeDTO> getEmployeesList(){
        return employeeService.findAll();
    }

    @GetMapping("/get")
    public EmployeeDTO getEmployee(@RequestParam Long id){
        return new EmployeeDTO(employeeService.get(id));
    }

    @PostMapping("/create")
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO){
        return new EmployeeDTO(employeeService.create(employeeDTO));
    }

    @PutMapping("/update")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        return new EmployeeDTO(employeeService.update(employeeDTO));
    }

    @DeleteMapping("/delete")
    public void deleteEmployee(@RequestParam Long id){
        employeeService.delete(id);
    }

    @PostMapping("/save")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        return new EmployeeDTO(employeeService.save(employeeDTO));
    }

}
