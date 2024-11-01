package com.dg.ServerFarmRepair.service;

public class MonitorNotFoundException extends Exception {

    private Long idMonitor;
    public MonitorNotFoundException(Long idMonitor) {
        super("Монитор с ID " + idMonitor + " не найден."); // Сообщение об ошибке
        this.idMonitor = idMonitor; // Сохраняем ID монитора
    }

    // Геттер для ID монитора
    public Long getIdMonitor() {
        return idMonitor;
    }

}