package com.dg.ServerFarmRepair.controller;


import com.dg.ServerFarmRepair.entity.HistoryRepairWorksEntity;
import com.dg.ServerFarmRepair.model.HistoryRepairRequest;
import com.dg.ServerFarmRepair.model.HistoryRepairWorksHttpModel;
import com.dg.ServerFarmRepair.repository.HistoryRepairWorksRepo;
import com.dg.ServerFarmRepair.service.HistoryRepairWorksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/repair_works")
@Tag(name = "Repair Works Controller", description = "Управление для истории ремонтных работ.")
public class HistoryRepairWorksController {


    @Autowired
    private HistoryRepairWorksService historyRepairWorksService;

    @Autowired
    private HistoryRepairWorksRepo historyRepairWorksRepo;




    @PostMapping("/findBySNAndDateRange")
    @Operation(summary = "Найти историю ремонтов по серийному номеру и диапазону дат",
            description = "Возвращает историю ремонтов по указанному серийному номеру и диапазону дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История ремонтов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История ремонтов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории ремонтов")
    })
    public ResponseEntity<List<HistoryRepairWorksEntity>>  findBySerialNumberAndDateRange(@RequestBody HistoryRepairRequest request){

        List<HistoryRepairWorksEntity> historyTestEntities = historyRepairWorksRepo.findBySerialNumberAndDateBetween(
                request.getSerialNumber(), request.getStartDate(), request.getEndDate()
        );
        if (historyTestEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( historyTestEntities);
    }

    @PostMapping("/findBySNAndDateRangeAuthorRepair")
    @Operation(summary = "Найти историю ремонтов по серийному номеру, автору ремонта и диапазону дат",
            description = "Возвращает историю ремонтов по серийному номеру, автору ремонта и диапазону дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История ремонтов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История ремонтов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории ремонтов")
    })
    public ResponseEntity<List<HistoryRepairWorksEntity>>  findBySerialNumberAndDateRangeAndAuthorRepair(@RequestBody HistoryRepairRequest request){

        List<HistoryRepairWorksEntity> historyTestEntities = historyRepairWorksRepo.findBySerialNumberAndAuthorRepairAndDateBetween(
            request.getSerialNumber(), request.getAuthorRepair(), request.getStartDate(), request.getEndDate()
        );
        if (historyTestEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( historyTestEntities);
    }



    @PostMapping("/findByDateRange")
    @Operation(summary = "Найти историю ремонтов по диапазону дат",
            description = "Возвращает историю ремонтов в указанном диапазоне дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История ремонтов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История ремонтов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории ремонтов")
    })
    public ResponseEntity<List<HistoryRepairWorksEntity>> findHistoryTestByDateRange(@RequestBody HistoryRepairRequest request){

        List<HistoryRepairWorksEntity> historyTestEntities = historyRepairWorksRepo.findByDateBetween(
               request.getStartDate(), request.getEndDate()
        );

        if (historyTestEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( historyTestEntities);
    }

    /*Получить историю тестирования по автору и диапазону дат*/



    // Эндпоинт для получения тестов по диапазону дат
    @PostMapping("/findByDateRangeAndAuthorRepair")
    @Operation(summary = "Получить историю ремонтов по автору и диапазону дат",
            description = "Возвращает историю ремонтов по автору и диапазону дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История ремонтов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История ремонтов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории ремонтов")
    })
    public ResponseEntity<List<HistoryRepairWorksEntity>> getTestsByDateRangeAndAuthorTest(@RequestBody HistoryRepairRequest request) {
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();
        String authorRepair = request.getAuthorRepair();
        List<HistoryRepairWorksEntity> tests = historyRepairWorksRepo.findByDateBetweenAndAuthorRepair(startDate, endDate, authorRepair);

        return ResponseEntity.ok(tests);
    }

    @PostMapping("/durationNow")
    @Operation(summary = "Получить продолжительность ремонта за сегодня по автору",
            description = "Возвращает продолжительность ремонта по автору в настоящее время.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продолжительность ремонта успешно получена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении продолжительности")
    })
    public ResponseEntity<String> durationTestNow(@RequestBody HistoryRepairWorksHttpModel data) {

        String durationNow = historyRepairWorksService.durationRepairByAuthorNow(data.getAuthorRepair());
        return ResponseEntity.ok(durationNow);

    }


    @PostMapping("/durationMonth")
    @Operation(summary = "Получить продолжительность ремонта за месяц по автору",
            description = "Возвращает продолжительность ремонта за месяц по автору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продолжительность ремонта успешно получена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении продолжительности")
    })
    public ResponseEntity<String> durationMonth(@RequestBody HistoryRepairWorksHttpModel data) {

        String durationNow = historyRepairWorksService.durationRepairByAuthorMonth(data.getAuthorRepair());
        return ResponseEntity.ok(durationNow);

    }

    @PostMapping("/durationById")
    @Operation(summary = "Получить продолжительность ремонта по ID и дате",
            description = "Возвращает продолжительность ремонта по ID монитора и дате.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продолжительность ремонта успешно получена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении продолжительности")
    })
    public ResponseEntity<String> durationById(@RequestBody HistoryRepairWorksHttpModel data) {

        String durationNow = historyRepairWorksService.durationRepairWorksByIdMonitorAndDate(data.getDate(),data.getIdMonitor());
        return ResponseEntity.ok(durationNow);

    }


    @PostMapping("/findByDate")
    @Operation(summary = "Найти историю ремонтов по дате",
            description = "Возвращает историю ремонтов по указанной дате.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История ремонтов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История ремонтов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории ремонтов")
    })
    public ResponseEntity<List<HistoryRepairWorksEntity>> findByDate(@RequestBody String date){

        List<HistoryRepairWorksEntity> list = historyRepairWorksService.findByDate(date);

        return ResponseEntity.ok(list);
    }







}
