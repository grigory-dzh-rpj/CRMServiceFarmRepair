package com.dg.ServerFarmRepair.controller;


import com.dg.ServerFarmRepair.entity.ContractorEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.model.reportsForHistoryTest.ContractorModel;
import com.dg.ServerFarmRepair.service.ContractorService;
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
@RequestMapping("/api/contractor")
@Tag(name = "Contractor Controller", description = "API для работы с контрагентами(тем кому мы чиним оборудование)," +
        "после ремонта оборудование должно помещаться в определенную категорию (рабочие , нцрп и тд..")
public class ContractorController {


    @Autowired
    private ContractorService contractorService;


    @GetMapping("/allName")
    @Operation(summary = "Получить все имена контрагентов",
            description = "Возвращает список всех имен контрагентов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список имен подрядчиков успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении списка")
    })
    public ResponseEntity<List<ContractorModel>> getAllContractorsName() {
        List<ContractorModel> categoryModels = contractorService.getAllContractorsName();
        try {
            return ResponseEntity.ok(categoryModels);
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }

    }



    @PostMapping("/create")
    @Operation(summary = "Создать нового контрагента",
            description = "Создает нового контрагента на основе предоставленных данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Подрядчик успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка в предоставленных данных"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при создании подрядчика")
    })
    public ResponseEntity<ContractorEntity> createContractor(@RequestBody ContractorEntity contractor) {
        ContractorEntity savedContractor = contractorService.createContractor(contractor);
        return new ResponseEntity<>(savedContractor, HttpStatus.CREATED);
    }

}
