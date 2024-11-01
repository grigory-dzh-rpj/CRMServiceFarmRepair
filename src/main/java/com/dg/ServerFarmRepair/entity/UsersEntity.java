package com.dg.ServerFarmRepair.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@Table(name = "users")
public class UsersEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String fio;
        private String pos;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Long chatId;
        private String tel;
        private String role;
        private String otdel;
        private String tracking;
        private String personTracking;
        private String password;
        private String login;
}

