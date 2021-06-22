package com.api.repository;

import com.api.model.InvoiceProduct;
import com.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long>, InvoiceProductIndexRepository {

    @Query("FROM InvoiceProduct WHERE product = ?1 AND actionDate <= ?2")
    List<InvoiceProduct> findByProductIdAndActionDate(Product product, Date actionDate);
}
