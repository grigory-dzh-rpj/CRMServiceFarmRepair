package com.dg.ServerFarmRepair.service;



import com.dg.ServerFarmRepair.entity.PhotoBoxPlatsEntity;

import com.dg.ServerFarmRepair.repository.PhotoBoxPlatsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class PhotoBoxPlatsService {

    @Autowired
    private PhotoBoxPlatsRepo photoBoxPlatsRepo;



    @Transactional
    public void loadPlatPhoto(String name, byte[] image) {
        PhotoBoxPlatsEntity photoBoxPlatsEntity = new PhotoBoxPlatsEntity();
        LocalDate d =LocalDate.now();
        photoBoxPlatsEntity.setName(name);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        photoBoxPlatsEntity.setDate(d.toString());
        photoBoxPlatsEntity.setImage(image);
        try {
            photoBoxPlatsRepo.save(photoBoxPlatsEntity);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("11111ji");
        }


    }

}
