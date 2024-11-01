package com.dg.ServerFarmRepair.model;

import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;

import java.util.List;

public class FinishTestSaveAndUpdateModelForCategory {

    private Long idMonitor;
    private String category;
    private List<HistoryDefectsEntity> selectedDefectsList;

    public FinishTestSaveAndUpdateModelForCategory(Long idMonitor, String category, List<HistoryDefectsEntity> selectedDefectsList) {
        this.idMonitor = idMonitor;
        this.category = category;
        this.selectedDefectsList = selectedDefectsList;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public String getCategory() {
        return category;
    }

    public List<HistoryDefectsEntity> getSelectedDefectsList() {
        return selectedDefectsList;
    }
}
