package com.dg.ServerFarmRepair.model;

import com.dg.ServerFarmRepair.entity.DefectsEntity;
import com.dg.ServerFarmRepair.entity.UsersEntity;

public class UsersModel {

    private String fio;
    private String login;
    private String password;

    public UsersModel(String fio, String login, String password) {

        this.fio = fio;
        this.login = login;
        this.password = password;
    }

    public static UsersModel toModelUsers (UsersEntity usersEntity){

        return new UsersModel (usersEntity.getFio(), usersEntity.getLogin(), usersEntity.getPassword());

    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
