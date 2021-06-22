package com.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rack")
@Data
@NoArgsConstructor
public class Rack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "rack",
            cascade = CascadeType.ALL)
    private List<InvoiceProduct> invoices;

    /** constructors */

    public Rack(String code, Warehouse warehouse) {
        this.code = code;
        this.warehouse = warehouse;
    }

    /** Μέθοδος για προσθήκη ενέργειας απόδειξης (bi-directional) */

    public void addInvoiceProduct(InvoiceProduct invoiceProduct){
        if (invoices == null){
            invoices = new ArrayList<>();
        }
        invoices.add(invoiceProduct);
        invoiceProduct.setRack(this);
    }
}
