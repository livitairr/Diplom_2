package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserModel;
import static data.OrderData.*;
import static io.restassured.RestAssured.given;

public class UserSteps {

    // Поля для хранения токенов доступа
    public String accessToken;
    public String refreshToken;

    // Создание запроса с базовыми заголовками
    private static io.restassured.specification.RequestSpecification createBaseRequest() {
        return given()
                .header("Content-Type", "application/json"); // Установка заголовка JSON
    }

    // Создание запроса с заголовком авторизации
    private static io.restassured.specification.RequestSpecification createAuthorizedRequest(String accessToken) {
        return createBaseRequest()
                .header("Authorization", accessToken); // Добавление заголовка Authorization
    }

    // Методы

    // Создание нового юзера
    @Step("Создание уникального пользователя с указанным телом запроса")
    public Response createUser(UserModel userModel) {
        return createBaseRequest()
                .body(userModel) // Тело запроса
                .when()
                .post(REGISTER_URL); // Отправка POST-запроса на регистрацию
    }

    // Авторизация юзера
    @Step("Авторизация пользователя с указанным телом запроса")
    public Response loginUser(UserModel userModel) {
        return createBaseRequest()
                .body(userModel) // Тело запроса
                .when()
                .post(LOGIN_URL); // Отправка POST-запроса на вход
    }

    // Удаление юзера по accessToken
    @Step("Удаление пользователя по accessToken")
    public Response deleteUser() {
        return createAuthorizedRequest(accessToken)
                .when()
                .delete(AUTH_URL); // Отправка DELETE-запроса
    }

    // Извлечение токенов доступа из ответа
    @Step("Извлечение accessToken и refreshToken из ответа")
    public void getAccessToken(Response response) {
        this.accessToken = response.jsonPath().getString("accessToken");
        this.refreshToken = response.jsonPath().getString("refreshToken");
    }

    // Обновление данных юзера с авторизацией
    @Step("Обновление данных пользователя с авторизацией")
    public Response editUserDataWithAuthorization(String accessToken, UserModel userModel) {
        return createAuthorizedRequest(accessToken)
                .body(userModel) // Тело запроса
                .when()
                .patch(AUTH_URL); // Отправка PATCH-запроса
    }

    // Обновление данных юзера без авторизации
    @Step("Обновление данных пользователя без авторизации")
    public Response editUserDataWithoutAuthorization(UserModel userModel) {
        return createBaseRequest()
                .body(userModel) // Тело запроса
                .when()
                .patch(AUTH_URL); // Отправка PATCH-запроса
    }
}
