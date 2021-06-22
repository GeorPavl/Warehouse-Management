package com.api.repository;

import com.api.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RackRepository extends JpaRepository<Rack,Long> {

    List<Rack> findByWarehouseId(Long id);
}
