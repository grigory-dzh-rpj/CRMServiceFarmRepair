package com.dg.ServerFarmRepair.model;

public class RequestChangeDate {

    private Long idMonitor;
    private String date;

    public RequestChangeDate(Long idMonitor, String date) {
        this.idMonitor = idMonitor;
        this.date = date;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
