package com.dg.ServerFarmRepair.model;

public class HistoryDefectsHttpModel {

    private Long idMonitor;
    private String defectName;
    private String description;
    private String status;

    public HistoryDefectsHttpModel(Long idMonitor, String defectName, String description, String status) {
        this.idMonitor = idMonitor;
        this.defectName = defectName;
        this.description = description;
        this.status = status;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
