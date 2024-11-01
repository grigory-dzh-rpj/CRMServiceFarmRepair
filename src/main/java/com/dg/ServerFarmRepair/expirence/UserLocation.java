package com.dg.ServerFarmRepair.expirence;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLocation {
    private String name; // Имя пользователя
    private String pos;
    private Double latitude; // Широта
    private Double longitude; // Долгота
    private LocalDateTime lastUpdated; // Время последнего обновления


    // Конструктор, геттеры и сеттеры
    public UserLocation(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserLocation(){
        this.lastUpdated = LocalDateTime.now();
    }

    public void updateLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }

}

