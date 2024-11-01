package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.HistoryRepairWorksEntity;
import com.dg.ServerFarmRepair.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepairWorksRepo extends JpaRepository<HistoryRepairWorksEntity, Long> {

    List<HistoryRepairWorksEntity> findAll();
    List<HistoryRepairWorksEntity> findBySerialNumber(String serialNumber);

    List<HistoryRepairWorksEntity> findBySerialNumberAndAuthorRepairAndDateBetween(String serialNumber, String authorRepair,
                                                                                   String startDate, String endDate);
    List<HistoryRepairWorksEntity> findBySerialNumberAndDateBetween(String serialNumber,
                                                                    String startDate, String endDate);


    List<HistoryRepairWorksEntity> findByDateBetween(String startDate, String endDate);
    List<HistoryRepairWorksEntity> findByDateBetweenAndAuthorRepair(String startDate, String endDate, String authorRepair);

    List<HistoryRepairWorksEntity> findByFinishTimeRepairIsNullAndMonitor_Id(Long idMonitor);
    List<HistoryRepairWorksEntity> findByMonitorAndDateBetween(MonitorEntity monitor, String startDate, String endDate);


    HistoryRepairWorksEntity findByMonitor_IdAndSerialNumberAndStartTimeRepairIsNull(Long idMonitor, String serialNumber);
    List<HistoryRepairWorksEntity> findByDate(String date);

    @Query(value = "SELECT SUM(TIME_FORMAT(duration, '%i:%s')) FROM history_repair_works WHERE date = CURDATE() AND author_repair = :author_repair", nativeQuery = true)
    int minutesDurationRepairWorksByAuthorRepairNow(@Param("author_repair") String author_repair);

    @Query(value = "SELECT SUM(TIME_FORMAT(duration, '%i:%s')) FROM history_repair_works WHERE author_repair = :author_repair AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
    int minutesDurationRepairWorksByAuthorRepairMonth(@Param("author_repair") String author_repair, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT SUM(TIME_FORMAT(duration, '%i:%s')) FROM history_repair_works WHERE date = :date AND id_monitor = :id", nativeQuery = true)
    int minutesDurationRepairWorksByDate(@Param("date") String date, @Param("id") Long idMonitor);

}
