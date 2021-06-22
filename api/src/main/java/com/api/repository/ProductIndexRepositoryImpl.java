package com.api.repository;

import com.api.dto.ProductIndexDTO;
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
public class ProductIndexRepositoryImpl implements ProductIndexRepository{

    @Autowired
    private EntityManager entityManager;

    private QInvoiceProduct qInvoiceProduct = QInvoiceProduct.invoiceProduct;
    private QProduct qProduct = QProduct.product;
    private QRack qRack = QRack.rack;
    private QInvoice qInvoice = QInvoice.invoice;
    private QWarehouse qWarehouse = QWarehouse.warehouse;

    @Override
    public List<ProductIndexDTO> productQuery(ProductIndexDTO search) {
        // TODO: 6/18/2021 Να προσθέσω να επιστρέφει και τις λίστες των ραφιών, αποδείξεων κτλ.
        JPAQuery<ProductIndexDTO> query = generalQuery(projection()).
                where(predicate(search));
        return query.fetch();
    }

    private <U>JPAQuery<U> generalQuery(Expression<U> select){
        JPAQuery<U> query = new JPAQuery<>(entityManager);
        return query.
                select(select).
                    from(qProduct).
                    join(qProduct.invoices, qInvoiceProduct).
                    join(qInvoiceProduct.invoice, qInvoice).
                    join(qInvoiceProduct.rack, qRack).
                    join(qRack.warehouse, qWarehouse).
                    distinct();
    }

    private FactoryExpression<ProductIndexDTO> projection(){
        return Projections.bean(ProductIndexDTO.class,
                qProduct.id,
                qProduct.title,
                qProduct.barcode,
                qProduct.price,
                qProduct.description);
    }

    private Predicate predicate(ProductIndexDTO search){
        BooleanBuilder builder = new BooleanBuilder();

        if (search.getId() != null){
            builder.and(qProduct.id.eq(search.getId()));
        }
        if (search.getTitle() != null){
            builder.and(qProduct.title.containsIgnoreCase(search.getTitle()));
        }
        if (search.getBarcode() != null){
            builder.and(qProduct.barcode.containsIgnoreCase(search.getBarcode()));
        }
        if (search.getDescription() != null){
            builder.and(qProduct.description.containsIgnoreCase(search.getDescription()));
        }
        if (search.getPrice() != null){
            builder.and(qProduct.price.eq(search.getPrice()));
        }
        if (search.getRackId() != null){
            builder.and(qRack.id.eq(search.getRackId()));
        }
        if (search.getWarehouseId() != null){
            builder.and(qWarehouse.id.eq(search.getWarehouseId()));
        }
        if (search.getInvoiceId() != null){
            builder.and(qInvoice.id.eq(search.getInvoiceId()));
        }
        return builder;
    }
}
