package com.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany( fetch = FetchType.LAZY,
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Invoice> invoices;

    /** constructors */

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /** Μέθοδος για προσθήκη ενέργειας απόδειξης (bi-directional) */

    public void addInvoice(Invoice invoice){
        if (invoices == null){
            invoices = new ArrayList<>();
        }
        invoices.add(invoice);
        invoice.setEmployee(this);
    }
}
