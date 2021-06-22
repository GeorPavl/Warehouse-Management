package com.api.repository;

import com.api.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>, InvoiceIndexRepository {
}
