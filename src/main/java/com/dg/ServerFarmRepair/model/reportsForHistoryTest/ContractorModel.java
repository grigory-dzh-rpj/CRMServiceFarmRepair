package com.dg.ServerFarmRepair.model.reportsForHistoryTest;

import com.dg.ServerFarmRepair.entity.CategoryEntity;
import com.dg.ServerFarmRepair.entity.ContractorEntity;
import com.dg.ServerFarmRepair.model.CategoryModel;
import lombok.Data;


@Data
public class ContractorModel {

    private String name;

    public ContractorModel(String name){
        this.name = name ;
    }


    public static ContractorModel toModelContractor (ContractorEntity contractor){
        return new ContractorModel (contractor.getName());
    }
}
