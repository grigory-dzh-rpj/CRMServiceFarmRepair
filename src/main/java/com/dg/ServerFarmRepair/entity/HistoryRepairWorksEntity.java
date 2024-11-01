package com.dg.ServerFarmRepair.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "history_repair_works")
public class HistoryRepairWorksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_monitor")
    private MonitorEntity monitor;

    private String date;
    private String model;
    private String authorRepair;
    private String startTimeRepair;
    private String finishTimeRepair;
    private String serialNumber;
    private String duration;
    private String message;


    @OneToMany(mappedBy = "historyRepair", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("repairReference")
    private List<HistoryDefectsEntity> historyDefects = new ArrayList<>();


}
