package com.dg.ServerFarmRepair.service;


import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;
import com.dg.ServerFarmRepair.entity.HistoryRepairWorksEntity;
import com.dg.ServerFarmRepair.entity.HistoryTestEntity;
import com.dg.ServerFarmRepair.entity.MonitorEntity;
import com.dg.ServerFarmRepair.repository.HistoryRepairWorksRepo;
import com.dg.ServerFarmRepair.repository.HistoryTestRepo;
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
public class HistoryTestService {

    @Autowired
    private HistoryTestRepo historyTestRepo;

    @Autowired
    private HistoryRepairWorksRepo historyRepairWorksRepo;

    @Autowired
    private MonitorRepo monitorRepo;

    /*Возвращаем лист истории теста по id*/

    public List<HistoryTestEntity> findBySerialNumber(String serialNumber){
        return historyTestRepo.findBySerialNumber(serialNumber);
    }

    public List<HistoryTestEntity> findBySerialNumberAndDateRange(String serialNumber, String start, String end){
        return historyTestRepo.findBySerialNumberAndDateBetween(serialNumber, start, end);
    }

    public List<HistoryTestEntity> findBySerialNumberAndDateRangeAndAuthorTest(String serialNumber, String start,
                                                                               String end, String authorTest){
        return historyTestRepo.findBySerialNumberAndDateBetweenAndAuthorTest(serialNumber, start, end, authorTest);
    }


    public List<HistoryTestEntity> findByAndAuthorTestAndDateBetween(
             String author_test, String startDate, String endDate) {
        return historyTestRepo.findByAuthorTestAndDateBetween(author_test, startDate, endDate);
    }
    public List<HistoryTestEntity> findByAuthorTest(String author_test){
        return historyTestRepo.findByAuthorTest(author_test);
    }

    public List<HistoryTestEntity> findByDate(String date) {
        return historyTestRepo.findByDate(date);
    }


    public List<HistoryTestEntity> findByDateRange(String start, String end) {
        return historyTestRepo.findByDateBetween(start, end);
    }



    /*start*/
    public void startTest(Long id, String authorTest) {
        try {
            // Проверка входных параметров на null
            if (id == null ||  authorTest == null) {
                throw new IllegalArgumentException("Один из обязательных параметров равен null. Параметры: id, model, authorTest.");
            }

            MonitorEntity monitor = monitorRepo.findById(id)
                    .orElseThrow(() -> new MonitorNotFoundException(id));

            // Создание сущности теста
            HistoryTestEntity historyTestEntity = new HistoryTestEntity();
            historyTestEntity.setMonitor(monitor);
            historyTestEntity.setModel(monitor.getModel());
            historyTestEntity.setSerialNumber(monitor.getSerial_number());

            // Форматирование времени и даты
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime now = LocalTime.now();
            historyTestEntity.setAuthorTest(authorTest);
            historyTestEntity.setStartTimeTest(now.format(formatter));
            historyTestEntity.setDate(getCurrentDateAsString());

            // Сохранение теста в базу данных
            historyTestRepo.save(historyTestEntity);

        } catch (DateTimeParseException e) {
            throw new RuntimeException("Ошибка при форматировании времени для монитора с ID " + id, e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при сохранении данных в базе для теста монитора с ID " + id, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка в переданных параметрах для теста монитора: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка при старте теста для монитора с ID " + id, e);
        }
    }

    /*finish*/
    public void finishTest(Long id, List<HistoryDefectsEntity> listSelectedDefects) {
        try {
            // Получаем тест по ID монитора, где время завершения еще не указано
            List<HistoryTestEntity> tests = historyTestRepo.findByMonitor_IdAndFinishTimeTestIsNull(id);
            if (tests.isEmpty()) {
                throw new RuntimeException("Тест для монитора с Serial number " +  id + " не найден.");
            }

            HistoryTestEntity historyTestEntity = tests.get(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime now = LocalTime.now(); // конечное время

            String startTime = historyTestEntity.getStartTimeTest();
            String finishTime = now.format(formatter);

            // Форматируем и вычисляем продолжительность теста
            LocalTime startTimeL = LocalTime.parse(startTime, formatter);
            LocalTime endTimeL = LocalTime.parse(finishTime, formatter);
            Duration duration = Duration.between(startTimeL, endTimeL);

            long durationSeconds = duration.toSeconds();
            long minutes = durationSeconds / 60;
            long seconds = durationSeconds % 60;

            String durationString = String.format("%02d:%02d", minutes, seconds);

            historyTestEntity.setFinishTimeTest(finishTime);
            historyTestEntity.setDuration(durationString);

            HistoryRepairWorksEntity repairWorks = new HistoryRepairWorksEntity();
            repairWorks.setMonitor(historyTestEntity.getMonitor());
            repairWorks.setModel(historyTestEntity.getModel());
            repairWorks.setSerialNumber(historyTestEntity.getSerialNumber());
            historyRepairWorksRepo.save(repairWorks);

            if (listSelectedDefects != null && !listSelectedDefects.isEmpty()) {

                for (HistoryDefectsEntity defect : listSelectedDefects) {
                    defect.setHistoryTest(historyTestEntity);

                    defect.setHistoryRepair(repairWorks);
                    historyTestEntity.getHistoryDefects().add(defect); // Добавляем дефект в коллекцию
                }

            }


            // Сохраняем тест в базу данных
            historyTestRepo.save(historyTestEntity);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Ошибка форматирования времени теста для монитора с SN " + id, e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при работе с базой данных для теста с SN " +  id, e);
        } catch (Exception e) {
            throw new RuntimeException("Неизвестная ошибка при завершении теста для монитора с SN "+id, e);
        }
    }


    public static String getCurrentDateAsString() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }



    public String durationTestByAuthorNow(String authorTest){
        int i = historyTestRepo.minutesDurationTestByAuthorTestNow(authorTest);
        String duration = String.format("%02d:%02d", i / 60, i % 60);
        return duration;
    }

    public String durationTestByAuthorMonth(String authorTest){
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(startDate.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s = startDate.format(formatter);
        String e = endDate.format(formatter);

        int i = historyTestRepo.minutesDurationTestByAuthorTestMonth(authorTest, s, e);
        String duration = String.format("%02d:%02d", i / 60, i % 60);
        return duration;
    }


}
