package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAll();
}