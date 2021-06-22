package com.api.repository;

import com.api.dto.InvoiceDTO;
import com.api.dto.InvoiceIndexDTO;
import com.api.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class InvoiceIndexRepositoryImpl implements InvoiceIndexRepository{

    @Autowired
    private EntityManager entityManager;

    private QInvoiceProduct qInvoiceProduct = QInvoiceProduct.invoiceProduct;
    private QProduct qProduct = QProduct.product;
    private QRack qRack = QRack.rack;
    private QInvoice qInvoice = QInvoice.invoice;
    private QEmployee qEmployee = QEmployee.employee;
    private QWarehouse qWarehouse = QWarehouse.warehouse;

    /**
     * Έφτιαξα ένα dto με πληροφορίες από όλους τους πίνακες που αφορούν την ενέργεια απόδειξης
     * Αυτό το dto χρησιμοποιώ και για την αναζήτηση αλλά και για την επιστροφή των αποτελεσμάτων */

    @Override
    public List<InvoiceDTO> invoiceQuery(InvoiceIndexDTO search) {
        JPAQuery<InvoiceDTO> query = generalQuery(projection()).
                where(predicate(search));
        return query.fetch();
    }

    private <U> JPAQuery<U> generalQuery(Expression<U> select){
        JPAQuery<U> query = new JPAQuery<>(entityManager);
        return query
                .select(select)
                .from(qInvoice)
                .join(qInvoice.employee, qEmployee)
                .join(qInvoice.invoiceProducts, qInvoiceProduct)
                .join(qInvoiceProduct.product, qProduct)
                .join(qInvoiceProduct.rack, qRack)
                .join(qRack.warehouse, qWarehouse)
                .distinct();
    }

    // TODO: 6/16/2021 Αν και κάνει search σύμφωνα με τον υπάλληλο, στα στοιχεία υπαλλήλου επιστρέφει null

    private FactoryExpression<InvoiceDTO> projection(){
        return Projections.bean(InvoiceDTO.class,
                qInvoice.id,
                qInvoice.description,
                qInvoice.date,
                qInvoice.invoiceType,
                qEmployee.id.as("employeeId"),
                qEmployee.lastName.as("employeeName"));
    }

    private Predicate predicate(InvoiceIndexDTO search){
        BooleanBuilder builder = new BooleanBuilder();
        if (search.getId() != null){
            builder.and(qInvoice.id.eq(search.getId()));
        }
        if (search.getDescription() != null){
            builder.and(qInvoice.description.eq(search.getDescription()));
        }
        if (search.getAfterDate() != null){
            builder.and(qInvoice.date.after(search.getAfterDate()));
        }
        if (search.getBeforeDate() != null){
            builder.and(qInvoice.date.before(search.getBeforeDate()));
        }
        if (search.getInvoiceType() != null){
            builder.and(qInvoice.invoiceType.eq(search.getInvoiceType()));
        }
        if (search.getEmployeeId() != null){
            builder.and(qInvoice.employee.id.eq(search.getEmployeeId()));
        }
        if (search.getRackId() != null){
            builder.and(qRack.id.eq(search.getRackId()));
        }
        if (search.getWarehouseId() != null){
            builder.and(qWarehouse.id.eq(search.getWarehouseId()));
        }
        if (search.getProductId() != null){
            builder.and(qProduct.id.eq(search.getProductId()));
        }
        return builder;
    }
}
