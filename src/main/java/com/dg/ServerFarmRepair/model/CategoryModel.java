package com.dg.ServerFarmRepair.model;

import com.dg.ServerFarmRepair.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryModel {

    @JsonProperty("category")
    private String category;

    public CategoryModel(String category){
        this.category = category ;
    }

    public static CategoryModel toModelCategory (CategoryEntity categoryEntity){
        return new CategoryModel (categoryEntity.getNameCategory());
    }
}
