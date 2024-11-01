package com.dg.ServerFarmRepair.model;


import lombok.Data;

@Data
public class HistoryRepairRequest {

    private String serialNumber;


    private String date; // Используется для одного запроса

    private String startDate; // Используется для диапазона дат
    private String endDate; // Используется для диапазона дат

    private Long idMonitor; // Используется для запросов, связанных с монитором
    private String authorRepair;
}
