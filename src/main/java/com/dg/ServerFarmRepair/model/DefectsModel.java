package com.dg.ServerFarmRepair.model;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefectsModel {

    @JsonProperty("defect")
    private String defect;

    public DefectsModel(String defect){
        this.defect = defect ;
    }

    public static DefectsModel toModelDefects (DefectsEntity defectsEntity){

        return new DefectsModel (defectsEntity.getDefect());

    }
}
