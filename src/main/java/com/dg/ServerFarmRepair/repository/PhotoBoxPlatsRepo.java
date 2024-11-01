package com.dg.ServerFarmRepair.repository;


import com.dg.ServerFarmRepair.entity.PhotoBoxPlatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoBoxPlatsRepo extends JpaRepository<PhotoBoxPlatsEntity, Long> {

    List<PhotoBoxPlatsEntity> findAll();

}
