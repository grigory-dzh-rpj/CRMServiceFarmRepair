package com.dg.ServerFarmRepair.entity;

import jakarta.persistence.*;

import lombok.Data;


import java.util.List;

@Entity
@Data
@Table(name = "monitor")
public class MonitorEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_number")
    private String serial_number;
    private String date;
    private String authorTest;
    private String model;
    private String status;
    private String authorRepair;

    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private ContractorEntity contractor;


    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoryTestEntity> testHistory;

    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoryRepairWorksEntity> repairHistory;



}
