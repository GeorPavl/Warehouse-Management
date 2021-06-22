package com.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "date")
    @CreationTimestamp
    private Date date;

    @Column(name = "invoice_type")
    private boolean invoiceType;

    @OneToMany(fetch = FetchType.LAZY ,
            mappedBy = "invoice",
            cascade = CascadeType.ALL)
    private List<InvoiceProduct> invoiceProducts;

    /** constructors */

    public Invoice(String description, Employee employee, boolean invoiceType) {
        this.description = description;
        this.employee = employee;
        this.invoiceType = invoiceType;
    }

    /** Μέθοδος για προσθήκη ενέργειας απόδειξης (bi-directional) */

    public void addInvoiceProduct(InvoiceProduct invoiceProduct){
        if (invoiceProducts == null){
            invoiceProducts = new ArrayList<>();
        }
        invoiceProducts.add(invoiceProduct);
        invoiceProduct.setInvoice(this);
    }
}
