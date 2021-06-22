package com.api.service;

import com.api.dto.EmployeeDTO;
import com.api.model.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Στο Employee θα αφήσω και το create/update εκτός από το save, για να υπάρχει κάπου η υλοποίηση */

    Employee dtoToEntity(EmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();

    Employee get(Long id);

    Employee create(EmployeeDTO employeeDTO);

    Employee update(EmployeeDTO employeeDTO);

    void delete(Long id);

    Employee save(EmployeeDTO employeeDTO);
}
