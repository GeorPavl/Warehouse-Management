package com.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoice_product")
@Data
@NoArgsConstructor
public class InvoiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_id", referencedColumnName = "id")
    private Rack rack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "action_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    @Column(name = "quantity")
    private Integer quantity;

    /** constructors */

    public InvoiceProduct(Product product, Rack rack, Invoice invoice, Integer quantity) {
        this.product = product;
        this.rack = rack;
        this.invoice = invoice;
        this.quantity = quantity;
    }

    public InvoiceProduct(Product product, Invoice invoice, Integer quantity) {
        this.product = product;
        this.invoice = invoice;
        this.quantity = quantity;
    }

    public InvoiceProduct(Rack rack, Invoice invoice, Integer quantity) {
        this.rack = rack;
        this.invoice = invoice;
        this.quantity = quantity;
    }

    public InvoiceProduct(Product product, Rack rack, Integer quantity) {
        this.product = product;
        this.rack = rack;
        this.quantity = quantity;
    }
}
