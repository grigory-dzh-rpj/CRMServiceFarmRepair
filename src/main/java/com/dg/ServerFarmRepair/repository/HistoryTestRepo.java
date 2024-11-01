package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.HistoryRepairWorksEntity;
import com.dg.ServerFarmRepair.entity.HistoryTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryTestRepo extends JpaRepository<HistoryTestEntity, Long> {

    List<HistoryTestEntity> findAll();
    List<HistoryTestEntity> findBySerialNumber(String serialNumber);
    List<HistoryTestEntity> findBySerialNumberAndDateBetween(String serialNumber, String startDate, String endDate);
    List<HistoryTestEntity> findBySerialNumberAndDateBetweenAndAuthorTest(String serialNumber, String startDate,
                                                             String endDate, String authorTest);


    List<HistoryTestEntity> findByMonitor_Id(Long idMonitor);
    List<HistoryTestEntity> findByMonitor_IdAndFinishTimeTestIsNull(Long idMonitor);
    List<HistoryTestEntity> findByAuthorTest(String author_test);
    List<HistoryTestEntity> findByDate(String date);
    List<HistoryTestEntity> findByAuthorTestAndDateBetween(String authorTest, String startDate, String endDate);
    List<HistoryTestEntity> findByDateBetween(String startDate, String endDate);




    @Query(value = "SELECT SUM(TIME_FORMAT(duration, '%i:%s')) FROM history_test WHERE date = CURDATE() AND author_test = :author_test", nativeQuery = true)
    int minutesDurationTestByAuthorTestNow(@Param("author_test") String author_test);

    @Query(value = "SELECT SUM(TIME_FORMAT(duration, '%i:%s')) FROM history_test WHERE author_test = :author_test AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
    int minutesDurationTestByAuthorTestMonth(@Param("author_test") String author_test, @Param("startDate") String startDate, @Param("endDate") String endDate);
}