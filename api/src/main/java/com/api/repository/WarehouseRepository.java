package com.api.repository;

import com.api.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long>, WarehouseIndexRepository {

}
