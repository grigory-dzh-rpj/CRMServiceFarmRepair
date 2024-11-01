package com.dg.ServerFarmRepair.service;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.model.DefectsModel;
import com.dg.ServerFarmRepair.repository.DefectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefectsService {

    @Autowired
    private DefectsRepo defectsRepository;


    /*возвращает список всех дефектов*/
    public List<DefectsModel> getAllDefects() {
        List<DefectsEntity> defectsEntities = defectsRepository.findAll();
        return defectsEntities.stream()
                .map(DefectsModel::toModelDefects)
                .collect(Collectors.toList());
    }


    public DefectsEntity createDefect(DefectsEntity defectsEntity){
        defectsRepository.save(defectsEntity);

        return defectsEntity;
    }
}
