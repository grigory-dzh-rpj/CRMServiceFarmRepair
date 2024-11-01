package com.dg.ServerFarmRepair.service;

import com.dg.ServerFarmRepair.entity.ContractorEntity;
import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;
import com.dg.ServerFarmRepair.entity.HistoryRepairWorksEntity;

import com.dg.ServerFarmRepair.entity.MonitorEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.repository.MonitorRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Service
public class MonitorService {


    @Autowired
    private MonitorRepo monitorRepository;


    @Autowired
    private HistoryTestService historyTestService;

    @Autowired
    private HistoryDefectsService historyDefectsService;

    @Autowired
    private HistoryRepairWorksService historyRepairWorksService;

    @Autowired
    private ContractorService contractorService;


    public boolean existsByDateAndStatus(String status) {
        int count = monitorRepository.countByDateAndStatus(status);
        return count > 0;
    }


    /**********************Transaction START TEST**********************************************************************/
    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void startTestAndUpdateMonitor(Long idMonitor, String authorTest) throws Exception {
        // 1. Запуск теста
        historyTestService.startTest(idMonitor,  authorTest);


        // 2. Обновление монитора
        updateMonitorGoToFirstRepair(idMonitor, "тестирование", "");
    }

    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void finishTestAndUpdateMonitor(Long idMonitor, String authorRepair,  List<HistoryDefectsEntity> listSelectedDefects) throws Exception {
        // 1. Запись времени завершения теста
        historyTestService.finishTest(idMonitor, listSelectedDefects);

        // 2. Обновление монитора
        updateMonitorGoToFirstRepair(idMonitor, "ожидание ремонта", authorRepair);
    }


    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void finishTestAndUpdateMonitorForCategory(Long idMonitor, String category,  List<HistoryDefectsEntity> listSelectedDefects) throws Exception {
        // 1. Запись времени завершения теста
        historyTestService.finishTest(idMonitor, listSelectedDefects);

        // 2. Обновление монитора
        updateMonitorGoToFirstRepair(idMonitor, category, "");
    }

    /**********************Transaction START REPAIR**/

    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void startRepairAndUpdateMonitor(Long idMonitor, String authorRepair) throws Exception {
        //1. Создаю историю ремонтной работы и записываю начальное время
        historyRepairWorksService.startRepair(idMonitor, authorRepair);
        //2.
        updateMonitor(idMonitor, "ремонт");
    }


    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void finishRepairAndUpdateMonitor(Long idMonitor, String authorRepair, String message) throws Exception {
        //1. Записываю время окончание ремонтной работы
        historyRepairWorksService.finishRepair(idMonitor,message);
        //2. Меняю статус дефектов на делались
//        historyDefectsService.changeStatus(idMonitor, "не делались");

//        updateMonitorGoToFirstRepair(idMonitor, "ожидание", authorRepair);
        updateMonitor(idMonitor, "ожидание");
    }

    /********************************************************************************************/





    /*Для взятия плат на ремонт*/
    @Transactional
    public MonitorEntity addNewMonitor(String serialNumber, String model, String authorTest, String contractorName) {
        MonitorEntity newMonitor = new MonitorEntity();
        newMonitor.setSerial_number(serialNumber);
        newMonitor.setModel(model);
        ContractorEntity contractor = contractorService.getContractorByName(contractorName);
        newMonitor.setContractor(contractor);
        newMonitor.setDate(String.valueOf(LocalDate.now()));
        newMonitor.setAuthorTest(authorTest);
        newMonitor.setStatus("ожидание");
        return monitorRepository.save(newMonitor);
    }

    @Transactional
    public List<MonitorEntity> addAllNewMonitor(List<MonitorEntity> monitorEntities){
       return monitorRepository.saveAll(monitorEntities);
    }

    /*для отправки на первоначальный ремонт*/

    public void updateMonitorGoToFirstRepair(Long id, String status, String authorRepair) {
        try {
            MonitorEntity monitor = monitorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Монитор с ID " + id + " не найден."));

            monitor.setStatus(status);
            monitor.setAuthorRepair(authorRepair);

            // Сохраняем монитор
            monitorRepository.save(monitor);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при обновлении статуса монитора с ID " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка при обновлении монитора с ID " + id, e);
        }
    }


    public void updateMonitor(Long id, String status) {
        try {
            MonitorEntity monitor = monitorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Монитор с ID " + id + " не найден."));

            monitor.setStatus(status);
            // Сохраняем монитор
            monitorRepository.save(monitor);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при обновлении статуса монитора с ID " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка при обновлении монитора с ID " + id, e);
        }
    }


    /*для отправки плат на монитор ремонтика*/
    @Transactional
    public List<MonitorEntity> findByAuthorRepairAndStatusAndDate(String authorName, String status, String date) {
        return monitorRepository.findByAuthorRepairAndStatusAndDate(authorName, status, date);
    }


    /*для отображения монитора*/
    public List<MonitorEntity> findByDate(String date){
        return  monitorRepository.findByDate(date);
    }


    /*now*/
    @Transactional
    public Integer findCountPlatsByStatusAndDate(String status){
        LocalDate localDate = LocalDate.now();

       return monitorRepository.findByStatusAndDate(status, localDate.toString()).size();
    }


    @Transactional
    public void changeDate(Long id) throws Exception {
        MonitorEntity monitor = monitorRepository.findById(id)
                .orElseThrow(() -> ExceptionHttp.ResourceNotFoundException());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.now().plusDays(1);
        monitor.setDate(localDate.format(formatter));
        monitorRepository.save(monitor);
    }


    @Transactional
    public void changeDateWithSelectDate(Long id, String date) throws Exception {
        MonitorEntity monitor = monitorRepository.findById(id)
                .orElseThrow(() -> ExceptionHttp.ResourceNotFoundException());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date);
        monitor.setDate(localDate.format(formatter));
        monitorRepository.save(monitor);
    }


}
