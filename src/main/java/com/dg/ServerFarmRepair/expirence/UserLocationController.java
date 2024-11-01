package com.dg.ServerFarmRepair.expirence;

import com.dg.ServerFarmRepair.entity.UsersEntity;
import com.dg.ServerFarmRepair.repository.UsersRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/location")
@Tag(name = "Location Controller", description = "Для работы с местоположением юзеров.")

public class UserLocationController {

    private final List<UserLocation> activeUsers = new ArrayList<>(); // Список активных пользователей

    @Autowired
    private UsersRepo usersRepo;
    // Метод для обновления местоположения пользователя
    @PostMapping("/update")
    @Operation(summary = "Обновить местоположение пользователя",
            description = "Удаляет старое местоположение пользователя и добавляет новое.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Местоположение пользователя успешно обновлено"),
            @ApiResponse(responseCode = "500", description = "Ошибка при обновлении местоположения")
    })
    public void updateUserLocation(@RequestBody UserLocation userLocation) {
        // Удаляем пользователя, если он уже есть в списке
        activeUsers.removeIf(user -> user.getName().equals(userLocation.getName()));

        userLocation.updateLastUpdated();

        // Добавляем или обновляем местоположение пользователя
        activeUsers.add(userLocation);
    }

    @GetMapping("/active")
    @Operation(summary = "Получить активных пользователей",
            description = "Возвращает список активных пользователей с именами и должностями  и их местоположением.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список активных пользователей успешно возвращен"),
            @ApiResponse(responseCode = "500", description = "Ошибка при получении списка активных пользователей")
    })
    public List<UserLocation> getActiveUsers() {
        removeInactiveUsers();
        // Список для хранения пользователей с реальными именами
        List<UserLocation> usersWithRealNames = new ArrayList<>();

        // Проходим по каждому активному пользователю
        for (UserLocation userLocation : activeUsers) {
            // Получаем логин пользователя
            String login = userLocation.getName(); // Предположим, что имя у вас хранится в поле 'name'

            // Находим пользователя по логину
            UsersEntity foundUser = usersRepo.findByLogin(login);

            // Если пользователь найден, заменяем логин на реальное имя
            if (foundUser != null) {
                // Создаем новый UserLocation с именем и координатами
                UserLocation updatedUserLocation = new UserLocation();
                updatedUserLocation.setName(foundUser.getFio()); // Устанавливаем реальное имя
                updatedUserLocation.setPos(foundUser.getPos());
                updatedUserLocation.setLatitude(userLocation.getLatitude()); // Устанавливаем широту
                updatedUserLocation.setLongitude(userLocation.getLongitude()); // Устанавливаем долготу

                // Добавляем обновленный объект в список
                usersWithRealNames.add(updatedUserLocation);
            }
        }

        // Возвращаем новый список с именами
        return usersWithRealNames;
    }


    private void removeInactiveUsers() {
        LocalDateTime now = LocalDateTime.now();
        activeUsers.removeIf(user -> user == null || user.getLastUpdated().isBefore(now.minusMinutes(1)));

    }
}
