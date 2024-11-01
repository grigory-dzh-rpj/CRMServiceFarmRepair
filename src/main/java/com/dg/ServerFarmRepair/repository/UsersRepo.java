package com.dg.ServerFarmRepair.repository;

import com.dg.ServerFarmRepair.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<UsersEntity, Long> {
    List<UsersEntity> findAll();

    @Query("SELECT u FROM UsersEntity u WHERE u.pos = :pos")
    List<UsersEntity> findByPos(@Param("pos") String pos);


    UsersEntity findByLogin(String login);

   Optional<UsersEntity> findById(Long id);
   UsersEntity findByLoginAndPassword(String login, String password);

}
