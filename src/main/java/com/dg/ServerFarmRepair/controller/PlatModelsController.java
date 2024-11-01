package com.dg.ServerFarmRepair.controller;


import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.entity.PlatModelsEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.model.DefectsModel;
import com.dg.ServerFarmRepair.model.PlatModelsModel;
import com.dg.ServerFarmRepair.service.DefectsService;
import com.dg.ServerFarmRepair.service.PlatModelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plat_models")
@Tag(name = "PlatsModels Controller", description = "Для работы с моделями плат.")
public class PlatModelsController {
    @Autowired
    PlatModelsService platModelsService;

    /**
     * Получить все модели плат.
     * Endpoint: GET /api/plat_models/all
     * @return Список моделей плат
     */
    @Operation(summary = "Получить все модели плат",
            description = "Возвращает список всех моделей плат.")
    @GetMapping("/all")
    public ResponseEntity<List<PlatModelsModel>> getAllModel() {
        List<PlatModelsModel> defectsModels = platModelsService.getAllmodels();
        try {
            return ResponseEntity.ok(defectsModels);
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }

    }

    /**
     * Создать новую модель плат.
     * Endpoint: POST /api/plat_models/create
     * @param entity Модель плат для создания
     * @return Созданная модель плат с статусом CREATED
     */
    @PostMapping("/create")
    @Operation(summary = "Создать новую модель плат",
            description = "Создает новую модель плат и возвращает её.")
    public ResponseEntity<PlatModelsEntity> create(@RequestBody PlatModelsEntity entity){
        platModelsService.createPlatModel(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }
}
