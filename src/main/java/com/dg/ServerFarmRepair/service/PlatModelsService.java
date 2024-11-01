package com.dg.ServerFarmRepair.service;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.entity.PlatModelsEntity;
import com.dg.ServerFarmRepair.model.DefectsModel;
import com.dg.ServerFarmRepair.model.PlatModelsModel;
import com.dg.ServerFarmRepair.repository.DefectsRepo;
import com.dg.ServerFarmRepair.repository.PlatModelsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatModelsService {

    @Autowired
    private PlatModelsRepo platModelsRepo;


    /*возвращает список всех моделей*/
    public List<PlatModelsModel> getAllmodels() {
        List<PlatModelsEntity> defectsEntities = platModelsRepo.findAll();
        return defectsEntities.stream()
                .map(PlatModelsModel::toModelPlatModel)
                .collect(Collectors.toList());
    }

    public void createPlatModel(PlatModelsEntity entity) {
        platModelsRepo.save(entity);
    }
}
