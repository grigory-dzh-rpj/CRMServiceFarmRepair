package com.dg.ServerFarmRepair.model;

import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;

import java.util.List;

public class FinishTestSaveAndUpdateModel {

    private Long idMonitor;
    private Long id;
    private String authorRepair;
    private List<HistoryDefectsEntity> selectedDefectsList;

    public FinishTestSaveAndUpdateModel(Long idMonitor, String authorRepair, List<HistoryDefectsEntity> selectedDefectsList) {
        this.idMonitor = idMonitor;
        this.authorRepair = authorRepair;
        this.selectedDefectsList = selectedDefectsList;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public String getAuthorRepair() {
        return authorRepair;
    }

    public void setAuthorRepair(String authorRepair) {
        this.authorRepair = authorRepair;
    }

    public List<HistoryDefectsEntity> getSelectedDefectsList() {
        return selectedDefectsList;
    }

    public void setSelectedDefectsList(List<HistoryDefectsEntity> selectedDefectsList) {
        this.selectedDefectsList = selectedDefectsList;
    }
}
