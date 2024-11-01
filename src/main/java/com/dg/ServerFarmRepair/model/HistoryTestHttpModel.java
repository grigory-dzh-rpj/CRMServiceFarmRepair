package com.dg.ServerFarmRepair.model;

public class HistoryTestHttpModel {

    private Long idMonitor;
    private String model;
    private String authorTest;


    public HistoryTestHttpModel(Long idMonitor, String model, String authorTest, String durationTest) {
        this.idMonitor = idMonitor;
        this.model = model;
        this.authorTest = authorTest;

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

    public String getAuthorTest() {
        return authorTest;
    }

    public void setAuthorTest(String authorTest) {
        this.authorTest = authorTest;
    }

}
