package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepo extends JpaRepository<ContractorEntity, Long> {

    ContractorEntity findByName(String name);
}
