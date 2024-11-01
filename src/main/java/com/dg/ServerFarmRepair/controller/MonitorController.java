package com.dg.ServerFarmRepair.controller;


import com.dg.ServerFarmRepair.entity.HistoryTestEntity;
import com.dg.ServerFarmRepair.entity.MonitorEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.model.*;


import com.dg.ServerFarmRepair.model.reportsForHistoryTest.HistoryTestRequest;
import com.dg.ServerFarmRepair.model.targetDefect.TargetDefectRequest;
import com.dg.ServerFarmRepair.service.*;
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
@RequestMapping("/api/monitor")
@Tag(name = "Monitor Controller", description = "Основной энедпоинт для организации процессинга ремонтного отдела.")

public class MonitorController {
    @Autowired
    private MonitorService monitorService;

    @Autowired
    private HistoryTestService historyTestService;
    @Autowired
    private HistoryDefectsService historyDefectsService;


    /*****  Start test transactions endpoint*/
    @PostMapping("/startTestAndUpdate")
    @Operation(summary = "Начать тест и обновить монитор",
            description = "Запускает тест для указанного монитора и обновляет его статус.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
            @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
    })
    public ResponseEntity<String> startTestAndUpdateMonitor(@RequestBody HistoryTestHttpModel data) {
        try {
            monitorService.startTestAndUpdateMonitor(
                    data.getIdMonitor(),
                    data.getAuthorTest()
            );
            return ResponseEntity.ok("Операции успешно выполнены");
        } catch (Exception e) {
            System.out.println(e.getMessage());
           e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций" );
        }
    }

    /*****  Finish test transactions endpoint*/


    @PostMapping("/finishTestAndUpdate")
    @Operation(summary = "Завершить тест и обновить монитор",
            description = "Завершает тест для указанного монитора и обновляет его статус.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
            @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
    })
    public ResponseEntity<String> finishTestAndUpdateMonitor(@RequestBody FinishTestSaveAndUpdateModel data) {
        try {
            monitorService.finishTestAndUpdateMonitor(
                    data.getIdMonitor(),
                    data.getAuthorRepair(),
                    data.getSelectedDefectsList()
            );
            return ResponseEntity.ok("Операции успешно выполнены");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций");
        }
    }


    @PostMapping("/finishTestAndUpdateForCategory")
    @Operation(summary = "Завершить тест и обновить монитор для категории",
            description = "Завершает тест для указанного монитора и обновляет его статус для заданной категории.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
            @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
    })
    public ResponseEntity<String> finishTestAndUpdateMonitorForCategory(@RequestBody FinishTestSaveAndUpdateModelForCategory data) {
        try {
            monitorService.finishTestAndUpdateMonitorForCategory(data.getIdMonitor(),data.getCategory(), data.getSelectedDefectsList());
            return ResponseEntity.ok("Операции успешно выполнены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций");
        }
    }

    /*****  Start repair transactions endpoint*/
    @PostMapping("/startRepairAndUpdate")
    @Operation(summary = "Начать ремонт и обновить монитор",
            description = "Запускает процесс ремонта для указанного монитора и обновляет его статус.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
            @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
    })
    public ResponseEntity<String> startRepairAndUpdateMonitor (@RequestBody HistoryRepairWorksHttpModel data) {
        try {
           monitorService.startRepairAndUpdateMonitor(data.getIdMonitor(), data.getAuthorRepair());
            return ResponseEntity.ok("Операции успешно выполнены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций");
        }
    }

            /*Начало по конкретному дефекту*/
            @PostMapping("/startTargetDefect")
            @Operation(summary = "Начать работу над конкретным дефектом",
                    description = "Записывает время начала работы над конкретным дефектом.")
            @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
                    @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
            })
            public ResponseEntity<String> startTargetDefect (@RequestBody TargetDefectRequest data) {
                try {
                    historyDefectsService.writeStartTime(data.getIdDefect(), data.getIdHistory());
                    return ResponseEntity.ok("Операции успешно выполнены");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций");
                }
            }
            /*Конец по конкретному дефекту*/
            @PostMapping("/finishTargetDefect")
            @Operation(summary = "Завершить работу над конкретным дефектом",
                    description = "Записывает время завершения работы над конкретным дефектом.")
            @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
                    @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
            })
            public ResponseEntity<String> finishTargetDefect (@RequestBody TargetDefectRequest data) {
                try {
                    historyDefectsService.writeFinishTime(data.getIdDefect(), data.getIdHistory());
                    return ResponseEntity.ok("Операции успешно выполнены");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций");
                }
            }


    /*****  Finish repair transactions endpoint*/
    @PostMapping("/finishRepairAndUpdate")
    @Operation(summary = "Завершить ремонт и обновить монитор",
            description = "Завершает процесс ремонта для указанного монитора и обновляет его статус.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операции успешно выполнены"),
            @ApiResponse(responseCode = "500", description = "Ошибка при выполнении операций")
    })
    public ResponseEntity<String> finishRepairAndUpdateMonitor(@RequestBody HistoryRepairWorksHttpModel data) {
        try {
            monitorService.finishRepairAndUpdateMonitor(data.getIdMonitor(), data.getAuthorRepair(), data.getMessage());
            return ResponseEntity.ok("Операции успешно выполнены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при выполнении операций");
        }
    }




    /*Добавление плат для первичного тестирования*/
    @PostMapping("/add")
    @Operation(summary = "Добавить новый монитор/ взять плату к ремону",
            description = "Добавляет новый монитор для первичного тестирования.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Платы добавлены в тестирование"),
            @ApiResponse(responseCode = "500", description = "Ошибка при добавлении монитора")
    })
    public ResponseEntity<String> addNewMonitor(@RequestBody MonitorRequestAddPlatToTheTest request) {

        try {
            monitorService.addNewMonitor(request.getSerialNumber(), request.getModel(), request.getAuthorTest(), request.getContractorName());
            return ResponseEntity.ok("Платы добавлены в тестирование");
        } catch (Exception e) {
            return ExceptionHttp.MainExeption();
        }

    }

    @PostMapping("/addListPlats")
    @Operation(summary = "НЕ ИСПОЛЬЗУЕТСЯ",
            description = "----.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "---"),
            @ApiResponse(responseCode = "500", description = "---")
    })
    public ResponseEntity<String> addAllNewMonitor(@RequestBody List<MonitorEntity> request){
        try{
            monitorService.addAllNewMonitor(request);
            return ResponseEntity.ok("Платы добавлены в тестирование");
        }catch (Exception e){return  ExceptionHttp.MainExeption();}
    }




    /*обновление монитора и отправка таким образом плат на ремонт конкретному сотруднику */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMonitor(@PathVariable Long id, @RequestBody MonitorUpdateRequest request) {
        try {
            monitorService.updateMonitorGoToFirstRepair(id, request.getStatus(), request.getAuthorRepair());
            return ResponseEntity.ok("Отправлено в" + request.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }






  @GetMapping("/countByStatusAndDateNow")
    public ResponseEntity<Integer> getCountByStatusAndDate(
            @RequestParam(value = "status") String status){

    Integer count = monitorService.findCountPlatsByStatusAndDate(status);

    return ResponseEntity.ok(count);

  }


  @GetMapping("/changeDate")
  @Operation(summary = "Изменить дату ремонта на следущий день",
          description = "Переносит дату ремонта для указанного монитора на следующий день .")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Дата ремонта успешно изменена"),
          @ApiResponse(responseCode = "404", description = "Монитор не найден")
  })
  public ResponseEntity<String> changeDate(@RequestParam(value = "idMonitor") Long idMonitor){

      try{
          monitorService.changeDate(idMonitor);
          return ResponseEntity.ok("Дата ремонта перенесена");
      }catch (Exception e){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

  }



    @PostMapping("/changeDateWithParam")
    @Operation(summary = "Изменить дату ремонта с параметрами",
            description = "Изменяет дату ремонта для указанного монитора с заданной датой.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дата ремонта успешно изменена"),
            @ApiResponse(responseCode = "404", description = "Монитор не найден")
    })
    public ResponseEntity<String> changeDateWithParam(@RequestBody RequestChangeDate requestChangeDate){

        try{
            monitorService.changeDateWithSelectDate(requestChangeDate.getIdMonitor(), requestChangeDate.getDate());
            return ResponseEntity.ok("Дата ремонта перенесена");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }



    @GetMapping("/monitor_test")
    @Operation(summary = "Получить список мониторов по дате",
            description = "Возвращает список мониторов, тестируемых на указанную дату.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список мониторов успешно получен"),
            @ApiResponse(responseCode = "404", description = "Мониторы не найдены")
    })
    public ResponseEntity<List<MonitorEntity>> getMonitor(@RequestParam(value = "date") String date){
        try{
            return ResponseEntity.ok(monitorService.findByDate(date));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }









}

