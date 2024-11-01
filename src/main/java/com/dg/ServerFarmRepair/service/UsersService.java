package com.dg.ServerFarmRepair.service;



import com.dg.ServerFarmRepair.entity.UsersEntity;
import com.dg.ServerFarmRepair.model.UsersModel;
import com.dg.ServerFarmRepair.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService  {

    @Autowired
    UsersRepo usersRepo;

    public List<UsersEntity> findByPos(String pos) {
        return usersRepo.findByPos(pos);
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Здесь вы можете получать данные о пользователе из источника данных (например, базы данных) по логину
//        UsersEntity user = usersRepo.findByLogin(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Пользователь не найден");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword());
//    }

    public String getFioByLoginAndPassword(String login, String password) {
        UsersModel user = UsersModel.toModelUsers(usersRepo.findByLoginAndPassword(login, password));
        if (user != null) {
            return user.getFio();
        } else {

            return  null;
        }
    }


    public String getUserByCardId(String idCard) {
        UsersModel user = UsersModel.toModelUsers(usersRepo.findById(Long.parseLong(idCard)).get());
        if (user != null) {
            return user.getFio();
        } else {

            return  null;
        }
    }


    public void createUser(UsersEntity entity) {
        usersRepo.save(entity);
    }
}
