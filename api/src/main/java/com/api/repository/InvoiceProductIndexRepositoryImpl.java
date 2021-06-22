package com.api.repository;

import com.api.dto.InvoiceProductIndexDTO;
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
public class InvoiceProductIndexRepositoryImpl implements InvoiceProductIndexRepository{

    // TODO: 6/16/2021 Να διαβάσω για EntityManager

    @Autowired
    private EntityManager entityManager;

    private QInvoiceProduct qInvoiceProduct = QInvoiceProduct.invoiceProduct;
    private QProduct qProduct = QProduct.product;
    private QRack qRack = QRack.rack;
    private QInvoice qInvoice = QInvoice.invoice;
    private QEmployee qEmployee = QEmployee.employee;

    /**
     * Έφτιαξα ένα dto με πληροφορίες από όλους τους πίνακες που αφορούν την ενέργεια απόδειξης
     * Αυτό το dto χρησιμοποιώ και για την αναζήτηση αλλά και για την επιστροφή των αποτελεσμάτων */

    @Override
    public List<InvoiceProductIndexDTO> invoiceProductQuery(InvoiceProductIndexDTO search) {
        JPAQuery<InvoiceProductIndexDTO> query = generalQuery(projection()).
                where(predicate(search));
        return query.fetch();
    }

    private <U> JPAQuery<U> generalQuery(Expression<U> select){
        JPAQuery<U> query = new JPAQuery<>(entityManager);
            return query
                    .select(select)
                    .from(qInvoiceProduct)
                    .join(qInvoiceProduct.rack, qRack)
                    .join(qInvoiceProduct.product, qProduct)
                    .join(qInvoiceProduct.invoice, qInvoice)
                    .join(qInvoice.employee, qEmployee);
    }

    // TODO: 6/16/2021 Αν και κάνει search σύμφωνα με τον υπάλληλο, στα στοιχεία υπαλλήλου επιστρέφει null

    private FactoryExpression<InvoiceProductIndexDTO> projection(){
        return Projections.bean(InvoiceProductIndexDTO.class,
                qInvoiceProduct.id,
                qInvoiceProduct.actionDate,
                qInvoiceProduct.quantity,
                qRack.id.as("rackId"),
                qRack.code.as("rackCode"),
                qProduct.id.as("productId"),
                qProduct.title.as("productTitle"),
                qProduct.barcode.as("productBarcode"),
                qProduct.price.as("productPrice"),
                qInvoice.id.as("invoiceId"),
                qInvoice.invoiceType.as("invoiceType"),
                qEmployee.id.as("EmployeeId"),
                qEmployee.lastName.as("EmployeeName"));
    }

    private Predicate predicate(InvoiceProductIndexDTO search){
        BooleanBuilder builder = new BooleanBuilder();

        if (search.getId() != null){
            builder.and(qInvoiceProduct.id.eq(search.getId()));
        }
        if (search.getAfterDate() != null){
            builder.and(qInvoiceProduct.actionDate.after(search.getAfterDate()));
        }
        if (search.getBeforeDate() != null){
            builder.and(qInvoiceProduct.actionDate.before(search.getBeforeDate()));
        }
        if (search.getQuantity() != null){
            builder.and(qInvoiceProduct.quantity.goe(search.getQuantity()));
        }
        if (search.getRackId() != null){
            builder.and(qRack.id.eq(search.getRackId()));
        }
        if (search.getRackCode() != null){
            builder.and(qRack.code.containsIgnoreCase(search.getRackCode()));
        }
        if (search.getInvoiceId() != null){
            builder.and(qInvoice.id.eq(search.getInvoiceId()));
        }
        if (search.getInvoiceType() != null){
            builder.and(qInvoice.invoiceType.eq(search.getInvoiceType()));
        }
        if (search.getProductId() != null){
            builder.and(qProduct.id.eq(search.getProductId()));
        }
        if (search.getProductTitle() != null){
            builder.and(qProduct.title.containsIgnoreCase(search.getProductTitle()));
        }
        if (search.getProductBarcode() != null){
            builder.and(qProduct.barcode.containsIgnoreCase(search.getProductBarcode()));
        }
        if (search.getEmployeeId() != null){
            builder.and(qEmployee.id.eq(search.getEmployeeId()));
        }
        if (search.getEmployeeLastName() != null){
            builder.and(qEmployee.lastName.containsIgnoreCase(search.getEmployeeLastName()));
        }
        return builder;
    }
}
