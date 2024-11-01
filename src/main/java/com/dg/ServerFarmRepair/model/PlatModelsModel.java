package com.dg.ServerFarmRepair.model;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.entity.PlatModelsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlatModelsModel {

    @JsonProperty("name")
    private String name;

    public PlatModelsModel(String name){
        this.name = name ;
    }

    public static PlatModelsModel toModelPlatModel (PlatModelsEntity modelEntity){

        return new PlatModelsModel (modelEntity.getName());

    }
}
