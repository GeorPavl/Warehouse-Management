package com.api.repository;

import com.api.dto.RackDTO;
import com.api.dto.WarehouseDTO;
import com.api.dto.WarehouseIndexDTO;
import com.api.model.QRack;
import com.api.model.QWarehouse;
import com.api.model.Rack;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WarehouseIndexRepositoryImpl implements WarehouseIndexRepository{

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RackRepository rackRepository;

    private QWarehouse qWarehouse = QWarehouse.warehouse;
    private QRack qRack = QRack.rack;

    @Override
    public List<WarehouseDTO> warehouseQuery(WarehouseIndexDTO search) {
        JPAQuery<WarehouseDTO> query = generalQuery(projection())
                                            .where(predicate(search));

        // TODO: 6/18/2021 Για να επιστρέφει ΚΑΙ τα ράφια της αποθήκης, έκανα την παρακάτω υλοποίηση 
        
        List<WarehouseDTO> list = query.fetch();
        for (WarehouseDTO warehouseDTO : list){
            warehouseDTO.setRacks(new ArrayList<>());
            List<Rack> racks = rackRepository.findByWarehouseId(warehouseDTO.getId());
            for (Rack rack : racks){
                warehouseDTO.getRacks().add(new RackDTO(rack));
            }
        }
        return list;
    }

    // TODO: 6/17/2021 Να προσθέσω στην αναζήτηση και description, κωδικό ραφιού

    private <U>JPAQuery<U> generalQuery(Expression<U> select){
        JPAQuery<U> query = new JPAQuery<>(entityManager);
        return query.select(select)
                .from(qWarehouse)
                .join(qWarehouse.racks, qRack)
                .distinct();
    }

    // TODO: 6/16/2021 Να το κάνω να επιστρέφει και τα racks

    private FactoryExpression<WarehouseDTO> projection(){
        return Projections.bean(WarehouseDTO.class,
                qWarehouse.id,
                qWarehouse.name,
                qWarehouse.description);
    }

    private Predicate predicate(WarehouseIndexDTO search){
        BooleanBuilder builder = new BooleanBuilder();

        if (search.getId() != null){
            builder.and(qWarehouse.id.eq(search.getId()));
        }
        if (search.getName() != null){
            builder.and(qWarehouse.name.containsIgnoreCase(search.getName()));
        }
        if (search.getDescription() != null){
            builder.and(qWarehouse.description.containsIgnoreCase(search.getDescription()));
        }
        if (search.getRackId() != null){
            builder.and(qRack.id.eq(search.getRackId()));
        }

        return builder;
    }
}
