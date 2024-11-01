package com.dg.ServerFarmRepair.controller;


import com.dg.ServerFarmRepair.entity.HistoryTestEntity;
import com.dg.ServerFarmRepair.model.HistoryTestHttpModel;
import com.dg.ServerFarmRepair.model.reportsForHistoryTest.HistoryTestRequest;
import com.dg.ServerFarmRepair.service.HistoryTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history_test")
@Tag(name = "HistoryTest Controller", description = "Управление историей тестирования.")
public class HistoryTestController {

    @Autowired
    HistoryTestService historyTestService;

    /*Возвращаем историю тестов по idMonitor */

    @GetMapping("/findBySN")
    @Operation(summary = "Найти историю тестов по серийному номеру",
            description = "Возвращает историю тестов по указанному серийному номеру.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>> returnHistoryTestFindBySN(
            @RequestParam(value = "serialNumber") String serialNumber) {

        List<HistoryTestEntity> historyTest = historyTestService.findBySerialNumber(serialNumber);
        if (historyTest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(historyTest);
    }



    @PostMapping("/findBySNAndDateRange")
    @Operation(summary = "Найти историю тестов по серийному номеру и диапазону дат",
            description = "Возвращает историю тестов по указанному серийному номеру и диапазону дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>>  findBySerialNumberAndDateRange(@RequestBody HistoryTestRequest request){

        List<HistoryTestEntity> historyTestEntities = historyTestService.findBySerialNumberAndDateRange(
                request.getSerialNumber(), request.getStartDate(), request.getEndDate()
        );
        if (historyTestEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( historyTestEntities);
    }

    @PostMapping("/findBySNAndDateRangeAuthorTest")
    @Operation(summary = "Найти историю тестов по серийному номеру, диапазону дат и автору теста",
            description = "Возвращает историю тестов по указанному серийному номеру, диапазону дат и автору теста.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>>  findBySerialNumberAndDateRangeAndAuthorTest(@RequestBody HistoryTestRequest request){

        List<HistoryTestEntity> historyTestEntities = historyTestService.findBySerialNumberAndDateRangeAndAuthorTest(
                request.getSerialNumber(), request.getStartDate(), request.getEndDate(), request.getAuthorTest()
        );
        if (historyTestEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( historyTestEntities);
    }



    @PostMapping("/findByDateRange")
    @Operation(summary = "Найти историю тестов по диапазону дат",
            description = "Возвращает историю тестов в указанном диапазоне дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>>  findHistoryTestByDateRange(@RequestBody HistoryTestRequest request){

        List<HistoryTestEntity> historyTestEntities = historyTestService.findByDateRange(request.getStartDate(),
                request.getEndDate());
        if (historyTestEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok( historyTestEntities);
    }

    /*Получить историю тестирования по автору и диапазону дат*/



    // Эндпоинт для получения тестов по диапазону дат
    @PostMapping("/findByDateRangeAndAuthorTest")
    @Operation(summary = "Получить историю тестов по автору и диапазону дат",
            description = "Возвращает историю тестов по указанному автору и диапазону дат.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>> getTestsByDateRange(@RequestBody HistoryTestRequest request) {
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();
        String authorTest = request.getAuthorTest();
        List<HistoryTestEntity> tests = historyTestService.findByAndAuthorTestAndDateBetween(authorTest, startDate, endDate);

        return ResponseEntity.ok(tests);
    }

    @PostMapping("/findByDate")
    @Operation(summary = "Найти историю тестов по дате",
            description = "Возвращает историю тестов по указанной дате.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>> getTestsByDate(@RequestBody HistoryTestRequest request) {
        String date = request.getDate();
        List<HistoryTestEntity> tests = historyTestService.findByDate(date);

        return ResponseEntity.ok(tests);
    }

    @PostMapping("/findByAuthorTest")
    @Operation(summary = "Найти историю тестов по автору теста",
            description = "Возвращает историю тестов по указанному автору теста.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История тестов успешно найдена"),
            @ApiResponse(responseCode = "404", description = "История тестов не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении истории тестов")
    })
    public ResponseEntity<List<HistoryTestEntity>> getTestsByAuthorTest(@RequestBody HistoryTestRequest request) {
        String authorTest = request.getAuthorTest();
        List<HistoryTestEntity> tests = historyTestService.findByAuthorTest(authorTest);

        return ResponseEntity.ok(tests);
    }



    @PostMapping("/durationNow")
    @Operation(summary = "Получить продолжительность теста за сегодня по автору",
            description = "Возвращает продолжительность теста по указанному автору за сегодня.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продолжительность теста успешно получена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении продолжительности")
    })
    public ResponseEntity<String> durationTestNow(@RequestBody HistoryTestHttpModel data) {

        String durationNow = historyTestService.durationTestByAuthorNow(data.getAuthorTest());
        return ResponseEntity.ok(durationNow);

    }


    @PostMapping("/durationMonth")
    @Operation(summary = "Получить продолжительность теста за месяц по автору",
            description = "Возвращает продолжительность теста за месяц по указанному автору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продолжительность теста успешно получена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при получении продолжительности")
    })
    public ResponseEntity<String> durationMonth(@RequestBody HistoryTestHttpModel data) {

        String durationNow = historyTestService.durationTestByAuthorMonth(data.getAuthorTest());
        return ResponseEntity.ok(durationNow);

    }


}
