package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitorRepo extends JpaRepository<MonitorEntity, Long> {

    /**/
    List<MonitorEntity> findByDate(String date);

    List<MonitorEntity> findByAuthorRepairAndStatusAndDate(String author_repair, String status, String date);

    List<MonitorEntity> findByStatusAndDate(String status, String date);


    @Query(value = "SELECT COUNT(*) FROM monitor WHERE date = CURDATE() AND status = :status", nativeQuery = true)
    int countByDateAndStatus(@Param("status") String status);








}
