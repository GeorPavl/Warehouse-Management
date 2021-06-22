package com.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY ,
            mappedBy = "product",
            cascade = CascadeType.ALL)
    List<InvoiceProduct> invoices;

    /** constructors */

    public Product(String title, String barcode, String description, BigDecimal price) {
        this.title = title;
        this.barcode = barcode;
        this.description = description;
        this.price = price;
    }

    /** Μέθοδος για προσθήκη ενέργειας απόδειξης (bi-directional) */

    public void addInvoiceProduct(InvoiceProduct invoiceProduct){
        if (invoices == null){
            invoices = new ArrayList<>();
        }
        invoices.add(invoiceProduct);
        invoiceProduct.setProduct(this);
    }
}
