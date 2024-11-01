package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.HistoryDefectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryDefectsRepo extends JpaRepository<HistoryDefectsEntity, Long> {




    List<HistoryDefectsEntity> findByHistoryTest_Id(Long idHistoryTest);

    List<HistoryDefectsEntity> findByHistoryTest_IdAndStatus(Long idHistoryTest, String status);

    HistoryDefectsEntity findByDefectNameAndHistoryTest_IdAndStatus(String name, Long id, String status);
    HistoryDefectsEntity findByIdAndHistoryTest_IdAndStatus(Long idDefect, Long idHistory, String status);

}