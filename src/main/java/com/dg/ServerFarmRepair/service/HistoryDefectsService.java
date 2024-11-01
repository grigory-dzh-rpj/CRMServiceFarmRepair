package com.dg.ServerFarmRepair.service;


import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;
import com.dg.ServerFarmRepair.entity.HistoryTestEntity;
import com.dg.ServerFarmRepair.entity.MonitorEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.repository.HistoryDefectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class HistoryDefectsService {

    @Autowired
    private HistoryDefectsRepo historyDefectsRepo;


    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void writeStartTime(Long idDefect, Long idHistory ){




        HistoryDefectsEntity defectsEntity = historyDefectsRepo.findByIdAndHistoryTest_IdAndStatus(
               idDefect, idHistory, "не делались"
        );



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        defectsEntity.setStartTimeRepairDefect(now.format(formatter));

        historyDefectsRepo.save(defectsEntity);

    }



    @Transactional(
            rollbackFor = Exception.class, // Откат на любом исключении
            isolation = Isolation.SERIALIZABLE // Уровень изоляции SERIALIZABLE
    )
    public void writeFinishTime(Long idDefect, Long idHistory ){

        HistoryDefectsEntity defectsEntity = historyDefectsRepo.findByIdAndHistoryTest_IdAndStatus(
               idDefect , idHistory, "не делались"
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        String finishTime = now.format(formatter);

        // Получение времени начала ремонта
        String startTime = defectsEntity.getStartTimeRepairDefect();
        LocalTime startTimeL = LocalTime.parse(startTime, formatter);
        LocalTime finishTimeL = LocalTime.parse(finishTime, formatter);

        // Вычисление продолжительности ремонта
        Duration duration = Duration.between(startTimeL, finishTimeL);
        long durationSeconds = duration.toSeconds();
        long minutes = durationSeconds / 60;
        long seconds = durationSeconds % 60;

        String durationString = String.format("%02d:%02d", minutes, seconds);

        // Обновление данных в сущности
        defectsEntity.setFinishTimeRepairDefect(finishTime);
        defectsEntity.setDuration(durationString);
        defectsEntity.setStatus("делались");

        historyDefectsRepo.save(defectsEntity);

    }


}
