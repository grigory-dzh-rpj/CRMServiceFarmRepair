package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectsRepo extends JpaRepository<DefectsEntity, Long> {

    List<DefectsEntity> findAll();


}
