package com.dg.ServerFarmRepair.service;


import com.dg.ServerFarmRepair.entity.CategoryEntity;
import com.dg.ServerFarmRepair.entity.ContractorEntity;
import com.dg.ServerFarmRepair.model.CategoryModel;
import com.dg.ServerFarmRepair.model.reportsForHistoryTest.ContractorModel;
import com.dg.ServerFarmRepair.repository.ContractorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractorService {

    @Autowired
    private ContractorRepo contractorRepo;


    public ContractorEntity getContractorByName(String name){
        ContractorEntity contractor = contractorRepo.findByName(name);
        if(contractor != null){
         return  contractor;
        }else{
         return null;
        }
    }


    public List<ContractorModel> getAllContractorsName() {
        List<ContractorEntity> contractorEntities =  contractorRepo.findAll();
        return contractorEntities.stream()
                .map(ContractorModel::toModelContractor)
                .collect(Collectors.toList());
    }


    public ContractorEntity createContractor(ContractorEntity contractor){

        contractorRepo.save(contractor);

        return contractor;
    }

}
