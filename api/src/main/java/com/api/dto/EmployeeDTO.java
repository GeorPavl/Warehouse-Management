package com.api.dto;

import com.api.model.Employee;
import com.api.model.Invoice;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<InvoiceDTO> invoices;

    /** constructors */

    public EmployeeDTO(Employee employee){
        BeanUtils.copyProperties(employee,this);
        invoices = new ArrayList<>();
        if (employee.getInvoices() != null){
            for (Invoice invoice : employee.getInvoices()){
                invoices.add(new InvoiceDTO(invoice));
            }
        }
    }
}
