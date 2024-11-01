package com.dg.ServerFarmRepair.model;

public class MonitorUpdateRequest {
    private String status;
    private String authorRepair;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthorRepair() {
        return authorRepair;
    }

    public void setAuthorRepair(String authorRepair) {
        this.authorRepair = authorRepair;
    }


}

