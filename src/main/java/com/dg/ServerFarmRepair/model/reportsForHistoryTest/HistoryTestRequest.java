package com.dg.ServerFarmRepair.model.reportsForHistoryTest;

public class HistoryTestRequest {


    private String serialNumber;


    private String date; // Используется для одного запроса

    private String startDate; // Используется для диапазона дат
    private String endDate; // Используется для диапазона дат

    private Long idMonitor; // Используется для запросов, связанных с монитором
    private String authorTest; // Используется для авторов тестов

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    // Геттеры и сеттеры
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public String getAuthorTest() {
        return authorTest;
    }

    public void setAuthorTest(String authorTest) {
        this.authorTest = authorTest;
    }
}
