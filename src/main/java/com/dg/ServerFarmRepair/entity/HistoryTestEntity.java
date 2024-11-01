package com.dg.ServerFarmRepair.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name ="history_test")
public class HistoryTestEntity {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;


   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "id_monitor", nullable = false)
   private MonitorEntity monitor;

   private String serialNumber;
   private String date;
   private String model;
   private String authorTest;
   private String startTimeTest;
   private String finishTimeTest;
   private String duration;


   @OneToMany(mappedBy = "historyTest", cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference("testReference")
   private List<HistoryDefectsEntity> historyDefects = new ArrayList<>();



}
