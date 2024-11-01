package com.dg.ServerFarmRepair.controller;

import com.dg.ServerFarmRepair.entity.CategoryEntity;
import com.dg.ServerFarmRepair.entity.ContractorEntity;
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
@RequestMapping("/api/category")
@Tag(name = "CategoryController", description = "API для работы с категориями плат," +
        "после ремонта оборудование должно помещаться в определенную категорию (рабочие , нцрп и тд..")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /*Отправь /api/category/all  для получения всех категорий
    /*Достаем вс*/
    @GetMapping("/all")
    @Operation(summary = "Получить все категории",
            description = "Возвращает список всех категорий, доступных в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список категорий успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении данных")
    })
    public ResponseEntity<List<CategoryModel>> getAllcategory() {
        List<CategoryModel> categoryModels = categoryService.getAllCategory();
        try {
            return ResponseEntity.ok(categoryModels);
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }

    }

    //Создать категорию
    @PostMapping("/create")
    @Operation(summary = "Создать новую категорию",
            description = "Создает новую категорию на основе предоставленных данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категория успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка в предоставленных данных"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при создании категории")
    })
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        CategoryEntity savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
