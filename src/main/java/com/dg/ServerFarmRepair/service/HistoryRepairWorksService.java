package com.dg.ServerFarmRepair.service;


import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;
import com.dg.ServerFarmRepair.entity.HistoryRepairWorksEntity;
import com.dg.ServerFarmRepair.entity.MonitorEntity;
import com.dg.ServerFarmRepair.repository.HistoryDefectsRepo;
import com.dg.ServerFarmRepair.repository.HistoryRepairWorksRepo;
import com.dg.ServerFarmRepair.repository.MonitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class HistoryRepairWorksService {

    @Autowired
    private HistoryRepairWorksRepo historyRepairWorksRepo;

    @Autowired
    private MonitorRepo monitorRepo;

    @Autowired
    private HistoryDefectsRepo historyDefectsRepo;

//
    public List<HistoryRepairWorksEntity>  findBySerialNumber(String serialNumber){
        return historyRepairWorksRepo.findBySerialNumber(serialNumber);
    }


    public List<HistoryRepairWorksEntity> findByMonitorAndDateBetween(Long idMonitor, String startDate, String endDate){
        MonitorEntity monitor = monitorRepo.findById(idMonitor)
                .orElseThrow(() -> new IllegalArgumentException("Monitor not found"));

        return historyRepairWorksRepo.findByMonitorAndDateBetween(monitor, startDate, endDate);
    }



    public void startRepair(Long idMonitor, String authorRepair) {
        try {
            // Проверка входных параметров на null
            if (idMonitor == null  || authorRepair == null) {
                throw new IllegalArgumentException("Один из обязательных параметров равен null. Параметры: idMonitor, model, authorRepair.");
            }
            MonitorEntity monitor = monitorRepo.findById(idMonitor)
                    .orElseThrow(() -> new MonitorNotFoundException(idMonitor));

            // Создание сущности для истории ремонтных работ
//            HistoryRepairWorksEntity repairWorks = new HistoryRepairWorksEntity();
//            repairWorks.setMonitor(monitor);
//            repairWorks.setModel(monitor.getModel());
//            repairWorks.setSerialNumber(monitor.getSerial_number());

            HistoryRepairWorksEntity repairWorks = historyRepairWorksRepo.findByMonitor_IdAndSerialNumberAndStartTimeRepairIsNull(
                    monitor.getId(), monitor.getSerial_number()
            );


            // Форматирование времени и даты
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime now = LocalTime.now();
            repairWorks.setAuthorRepair(monitor.getAuthorRepair());
            repairWorks.setStartTimeRepair(now.format(formatter));
            repairWorks.setDate(getCurrentDateAsString());

            // Сохранение записи о начале ремонта в базу данных
            historyRepairWorksRepo.save(repairWorks);

        } catch (DateTimeParseException e) {
            throw new RuntimeException("Ошибка при форматировании времени для монитора с ID " + idMonitor, e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при сохранении данных в базе для ремонта монитора с ID " + idMonitor, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка в переданных параметрах для ремонта монитора: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка при старте ремонта для монитора с ID " + idMonitor, e);
        }
    }


    public void finishRepair(Long idMonitor, String message) {
        try {
            // Проверка входного параметра
            if (idMonitor == null) {
                throw new IllegalArgumentException("ID монитора не может быть null.");
            }

            // Поиск записи о ремонте, где время завершения еще не установлено
            List<HistoryRepairWorksEntity> repairWorksList = historyRepairWorksRepo.findByFinishTimeRepairIsNullAndMonitor_Id(idMonitor);
            if (repairWorksList.isEmpty()) {
                throw new RuntimeException("Ремонт для монитора с ID " + idMonitor + " не найден или уже завершен.");
            }

            HistoryRepairWorksEntity repairWorks = repairWorksList.get(0);

            // Форматирование текущего времени как время завершения ремонта
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime now = LocalTime.now();
            String finishTime = now.format(formatter);

            // Получение времени начала ремонта
            String startTime = repairWorks.getStartTimeRepair();
            LocalTime startTimeL = LocalTime.parse(startTime, formatter);
            LocalTime finishTimeL = LocalTime.parse(finishTime, formatter);

            // Вычисление продолжительности ремонта
            Duration duration = Duration.between(startTimeL, finishTimeL);
            long durationSeconds = duration.toSeconds();
            long minutes = durationSeconds / 60;
            long seconds = durationSeconds % 60;

            String durationString = String.format("%02d:%02d", minutes, seconds);

            // Обновление данных в сущности
            repairWorks.setFinishTimeRepair(finishTime);
            repairWorks.setDuration(durationString);
            if(message != null){
                repairWorks.setMessage(message);
            }


            // Обновление статуса дефектов, связанных с ремонтом
            List<HistoryDefectsEntity> defects = historyDefectsRepo.findByHistoryTest_Id(repairWorks.getId());
            for (HistoryDefectsEntity defect : defects) {
                defect.setStatus("делались");
            }
            historyDefectsRepo.saveAll(defects);

            // Сохранение обновленной записи в базу данных
            historyRepairWorksRepo.save(repairWorks);

        } catch (DateTimeParseException e) {
            throw new RuntimeException("Ошибка форматирования времени для монитора с ID " + idMonitor, e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при сохранении данных в базе для ремонта монитора с ID " + idMonitor, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка в переданных параметрах для завершения ремонта: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка при завершении ремонта для монитора с ID " + idMonitor, e);
        }
    }


    public static String getCurrentDateAsString() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }


    public String durationRepairByAuthorNow(String authorRepair){
        int i = historyRepairWorksRepo.minutesDurationRepairWorksByAuthorRepairNow(authorRepair);
        String duration = String.format("%02d:%02d", i / 60, i % 60);
        return duration;
    }

    public String durationRepairByAuthorMonth(String authorTest){
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(startDate.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s = startDate.format(formatter);
        String e = endDate.format(formatter);

        int i = historyRepairWorksRepo.minutesDurationRepairWorksByAuthorRepairMonth(authorTest, s, e);
        String duration = String.format("%02d:%02d", i / 60, i % 60);
        return duration;
    }

    public String durationRepairWorksByIdMonitorAndDate(String date, Long idMonitor){

        int i =  historyRepairWorksRepo.minutesDurationRepairWorksByDate(date,idMonitor);
        String duration = String.format("%02d:%02d", i / 60, i % 60);
        return duration;

    }


    public List<HistoryRepairWorksEntity> findByDate(String date){

        return historyRepairWorksRepo.findByDate(date);
    }

}
