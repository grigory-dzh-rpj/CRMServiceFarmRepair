package com.dg.ServerFarmRepair.controller;


import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.model.CategoryModel;
import com.dg.ServerFarmRepair.model.DefectsModel;
import com.dg.ServerFarmRepair.service.CategoryService;
import com.dg.ServerFarmRepair.service.DefectsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/defects")
@Tag(name = "Defects Controller", description = "Управляем дефектами оборудования.")
public class DefectsController {

    @Autowired
    DefectsService defectsService;

    /*Отправь /api/defects/all  для получения всех неисправностей {defect : LDO}*/
    /*Достаем вс*/
    @GetMapping("/all")
    @Operation(summary = "Получить все дефекты для выбора к ремонту",
            description = "Возвращает список всех дефектов которые можно выбрать к ремонтируемому оборудованию.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список дефектов успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении списка")
    })
    public ResponseEntity<List<DefectsModel>> getAllDefects() {
        List<DefectsModel> defectsModels = defectsService.getAllDefects();
        try {
            return ResponseEntity.ok(defectsModels);
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }
    }


    @PostMapping("/create")
    @Operation(summary = "Создать новый дефект для выбора к ремонту",
            description = "Создает новый дефект на основе предоставленных данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Дефект успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в предоставленных данных"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при создании дефекта")
    })
    public ResponseEntity<DefectsEntity> create(@RequestBody DefectsEntity entity){
        defectsService.createDefect(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }
}
