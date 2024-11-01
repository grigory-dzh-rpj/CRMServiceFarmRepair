package com.dg.ServerFarmRepair.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

@Entity
@Table(name = "plat_models")
public class PlatModelsEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
