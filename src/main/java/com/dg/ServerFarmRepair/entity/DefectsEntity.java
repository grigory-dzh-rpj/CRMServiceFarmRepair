package com.dg.ServerFarmRepair.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "defects")
public class DefectsEntity {

 @Id
 @GeneratedValue (strategy = GenerationType.IDENTITY)
 private Long id;
 private String defect;

}
