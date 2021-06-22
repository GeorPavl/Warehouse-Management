package com.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouse")
@Data
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @OneToMany( fetch = FetchType.LAZY,
            mappedBy = "warehouse",
            cascade = CascadeType.ALL)
    private List<Rack> racks;

    /** constructors */

    public Warehouse(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** Μέθοδος για προσθήκη ραφιού (bi-directional) */

    public void addRack(Rack rack){
        if (racks == null){
            racks = new ArrayList<>();
        }
        racks.add(rack);
        rack.setWarehouse(this);
    }
}
