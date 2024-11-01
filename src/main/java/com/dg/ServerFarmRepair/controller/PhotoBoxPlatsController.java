package com.dg.ServerFarmRepair.controller;




import com.dg.ServerFarmRepair.exception.ExceptionHttp;

import com.dg.ServerFarmRepair.model.PhotoBoxPlatsModel;
import com.dg.ServerFarmRepair.service.PhotoBoxPlatsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/photo")
@Tag(name = "PhotoController", description = "В данный момент не использую, скоро будет иное хранение фото")
public class PhotoBoxPlatsController {

    @Autowired
    private PhotoBoxPlatsService photoBoxPlatsService;

    @PostMapping("/add")
    public ResponseEntity<String> addPhoto(@RequestBody PhotoBoxPlatsModel data) {

        try{
            byte[] decodedImage = Base64.getDecoder().decode(data.getImage());
            photoBoxPlatsService.loadPlatPhoto(data.getName(), decodedImage);
            return ResponseEntity.ok("Фото загружено");
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }

    }
}
