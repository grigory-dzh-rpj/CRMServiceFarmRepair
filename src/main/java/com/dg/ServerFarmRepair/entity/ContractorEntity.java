package com.dg.ServerFarmRepair.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="contractor")
public class ContractorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String  tel;

    private String  email;


}
