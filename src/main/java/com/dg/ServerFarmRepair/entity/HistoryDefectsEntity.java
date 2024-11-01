package com.dg.ServerFarmRepair.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "history_defects")
public class HistoryDefectsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String defectName;
    private String description;
    private String status;

    @ManyToOne
    @JsonBackReference("testReference")
    @JoinColumn(name = "id_history_test", nullable = false)
    private HistoryTestEntity historyTest;


    @ManyToOne
    @JsonBackReference("repairReference")
    @JoinColumn(name = "id_history_repair", nullable = false)
    private HistoryRepairWorksEntity historyRepair;




    private String startTimeRepairDefect;
    private String finishTimeRepairDefect;
    private String duration;




}
