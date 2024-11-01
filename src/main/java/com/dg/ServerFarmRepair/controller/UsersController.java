package com.dg.ServerFarmRepair.controller;

import com.dg.ServerFarmRepair.entity.PlatModelsEntity;
import com.dg.ServerFarmRepair.entity.UsersEntity;
import com.dg.ServerFarmRepair.exception.ExceptionHttp;
import com.dg.ServerFarmRepair.model.PlatModelsModel;
import com.dg.ServerFarmRepair.service.PlatModelsService;
import com.dg.ServerFarmRepair.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@Tag(name = "UsersController", description = "Контроллер для управления юзерами/думаю потом вынести в отдельный сервис.")
public class UsersController {

    @Autowired
    private UsersService usersService;


    /*Лист по должности*/
    @GetMapping("/pos")
    @Operation(summary = "Получить пользователей по должности",
            description = "Возвращает список пользователей по указанной должности.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка при получении списка пользователей")
    })
    public ResponseEntity<List<UsersEntity>> getUsersByPos(
            @RequestParam (value = "pos") String pos) {
        List<UsersEntity> usersPoslist = usersService.findByPos(pos);
        try {
            return ResponseEntity.ok(usersPoslist);
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }
    }

    @GetMapping("/auth")
    @Operation(summary = "Аутентификация пользователя",
            description = "Возвращает полное имя пользователя по логину и паролю.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Имя пользователя успешно получено"),
            @ApiResponse(responseCode = "500", description = "Ошибка при аутентификации пользователя")
    })
    public ResponseEntity<String> getUserByLoginAndPassword(
            @RequestParam(value = "login") String login, @RequestParam(value ="password") String password){

       String username = usersService.getFioByLoginAndPassword(login, password);
       try{
           return ResponseEntity.ok(username);
       }catch (Exception e){
           return ExceptionHttp.MainExeption();
       }

    }

    @GetMapping("/authByCard")
    @Operation(summary = "Аутентификация пользователя по ID карты",
            description = "Возвращает полное имя пользователя по ID карты.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Имя пользователя успешно получено"),
            @ApiResponse(responseCode = "500", description = "Ошибка при аутентификации пользователя по ID карты")
    })
    public ResponseEntity<String> getUserByLoginAndPasswordByIdCard(
            @RequestParam(value = "idCard") String idCard){

        String username = usersService.getUserByCardId(idCard);
        try {
            return ResponseEntity.ok(username);
        }catch (Exception e){
            return ExceptionHttp.MainExeption();
        }

    }



    @PostMapping("/create")
    @Operation(summary = "Создать нового пользователя",
            description = "Создает нового пользователя и возвращает его данные.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка при создании пользователя")
    })
    public ResponseEntity<UsersEntity> create(@RequestBody UsersEntity entity){
        usersService.createUser(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }



}
