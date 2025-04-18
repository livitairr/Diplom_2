package ApiTests;

import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static data.OrderData.BASE_URL;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Авторизация пользователя") // Эпик для Allure-отчётов
public class LoginUserTest {

    // Объект для работы с юзерами
    private UserSteps userSteps;

    // Ответы от сервера при создании пользователя и попытке входа
    private Response response;
    private Response uniqueUserCreating;

    // Юзер с фиксированными данными
    private UserModel fixedUser;

    @Before
    public void setUp() {
        // Устанавливаем базовый URL для API
        RestAssured.baseURI = BASE_URL;

        // Создаём объект шагов для работы с юзерами
        userSteps = new UserSteps();

        // Генерируем уникальные данные для пользователя
        fixedUser = UserData.getValidUser();

        // Создаём нового пользователя
        uniqueUserCreating = userSteps.createUser(fixedUser);
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    @Description("Этот тест проверяет возможность входа с корректными учетными данными существующего пользователя.")
    public void userLogin() {
        // Отправляем запрос на вход с использованием данных ранее созданного пользователя
        response = userSteps.loginUser(new UserModel(fixedUser.getEmail(), fixedUser.getPassword(), null));

        // Проверяем успешный вход
        response.then()
                .statusCode(SC_OK) // Ожидаемый код 200
                .body("success", equalTo(true)); // Проверяем успешный статус ответа
    }

    @After
    public void tearDown() {
        if (uniqueUserCreating != null) {
            // Получаем accessToken из ответа
            userSteps.getAccessToken(uniqueUserCreating);

            // Удаляем тестового пользователя после завершения теста
            userSteps.deleteUser();
        }
    }
}
