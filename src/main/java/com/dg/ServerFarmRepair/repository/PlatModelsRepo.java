package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.entity.PlatModelsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatModelsRepo extends JpaRepository<PlatModelsEntity, Long> {

    List<PlatModelsEntity> findAll();

}
