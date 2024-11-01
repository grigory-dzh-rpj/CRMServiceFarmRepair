package com.dg.ServerFarmRepair.model;

import lombok.Data;


public class HistoryRepairWorksHttpModel {

    private Long idMonitor;
    private String model;
    private String authorRepair;
    private String startRepair;
    private String finishRepair;
    private String date;
    private String message;

    public String getDate() {
        return date;
    }

    public HistoryRepairWorksHttpModel(Long idMonitor, String model, String authorRepair, String startRepair, String finishRepair, String date) {
        this.idMonitor = idMonitor;
        this.model = model;
        this.authorRepair = authorRepair;
        this.startRepair = startRepair;
        this.finishRepair = finishRepair;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAuthorRepair() {
        return authorRepair;
    }

    public void setAuthorRepair(String authorRepair) {
        this.authorRepair = authorRepair;
    }

    public String getStartRepair() {
        return startRepair;
    }

    public void setStartRepair(String startRepair) {
        this.startRepair = startRepair;
    }

    public String getFinishRepair() {
        return finishRepair;
    }

    public void setFinishRepair(String finishRepair) {
        this.finishRepair = finishRepair;
    }
}
