package com.api.service;

import com.api.dto.EmployeeDTO;
import com.api.dto.InvoiceDTO;
import com.api.model.Employee;
import com.api.model.Invoice;
import com.api.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private InvoiceService invoiceService;

    @Override
    public Employee dtoToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        if (employeeDTO.getInvoices() != null){
            List<Invoice> invoices = new ArrayList<>();
            for (InvoiceDTO invoiceDTO : employeeDTO.getInvoices()){
                invoices.add(invoiceService.dtoToEntity(invoiceDTO));
            }
            employee.setInvoices(invoices);
        }
        return employee;
    }

    @Override
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()){
            employeeDTOS.add(new EmployeeDTO(employee));
        }
        return employeeDTOS;
    }

    @Override
    public Employee get(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee == null){
            throw new RuntimeException("Did not find employee id: " + id);
        }
        return employee.get();
    }

    @Override
    public Employee create(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        if (employeeDTO.getInvoices() != null){
            List<Invoice> invoices = new ArrayList<>();
            for (InvoiceDTO invoiceDTO : employeeDTO.getInvoices()){
                employee.addInvoice(new Invoice(invoiceDTO.getDescription(), employee, invoiceDTO.getInvoiceType()));
            }
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(EmployeeDTO employeeDTO) {
        Employee employee = get(employeeDTO.getId());
        if (employee != null){
            BeanUtils.copyProperties(employeeDTO,employee);
            if (employeeDTO.getInvoices() != null){
                List<Invoice> invoices = new ArrayList<>();
                for (InvoiceDTO invoiceDTO : employeeDTO.getInvoices()){
                    employee.addInvoice(new Invoice(invoiceDTO.getDescription(), employee, invoiceDTO.getInvoiceType()));
                }
            }
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        if (get(id) != null){
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        if (employeeDTO.getInvoices() != null){
            for (InvoiceDTO invoiceDTO : employeeDTO.getInvoices()){
                Invoice invoice = invoiceService.dtoToEntity(invoiceDTO);
                employee.addInvoice(invoice);
            }
        }
        return employeeRepository.save(employee);
    }
}
