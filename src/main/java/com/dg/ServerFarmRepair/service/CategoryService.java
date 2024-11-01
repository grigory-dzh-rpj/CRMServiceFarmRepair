package com.dg.ServerFarmRepair.service;

import com.dg.ServerFarmRepair.entity.CategoryEntity;
import com.dg.ServerFarmRepair.entity.ContractorEntity;
import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.model.CategoryModel;
import com.dg.ServerFarmRepair.model.DefectsModel;
import com.dg.ServerFarmRepair.repository.CategoryRepo;
import com.dg.ServerFarmRepair.repository.DefectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


    /*возвращает список всех дефектов*/
    public List<CategoryModel> getAllCategory() {
        List<CategoryEntity> defectsEntities = categoryRepo.findAll();
        return defectsEntities.stream()
                .map(CategoryModel::toModelCategory)
                .collect(Collectors.toList());
    }


    public CategoryEntity createCategory(CategoryEntity category){

        categoryRepo.save(category);

        return category;
    }
}
