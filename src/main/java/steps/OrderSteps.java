package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.OrderModel;
import static data.UserData.ORDER_URL;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    // Метод для создания базового запроса с заголовками
    private static io.restassured.specification.RequestSpecification createBaseRequest() {
        return given()
                .header("Content-Type", "application/json"); // Установка заголовка JSON
    }

    // Метод для создания запроса с авторизацией
    private static io.restassured.specification.RequestSpecification createAuthorizedRequest(String accessToken) {
        return createBaseRequest()
                .header("Authorization", accessToken); // Добавляем токен авторизации
    }

    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuthorization(String accessToken, OrderModel orderModel) {
        return createAuthorizedRequest(accessToken)
                .body(orderModel) // Тело запроса
                .when()
                .post(ORDER_URL); // Отправка POST-запроса
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuthorization(OrderModel orderModel) {
        return createBaseRequest()
                .body(orderModel) // Тело запроса
                .when()
                .post(ORDER_URL); // Отправка POST-запроса
    }

    @Step("Получение списка заказов авторизованного пользователя")
    public Response getOrderListAuthorizedUser(String token) {
        return createAuthorizedRequest(token)
                .when()
                .get(ORDER_URL); // Отправка GET-запроса
    }

    @Step("Получение списка заказов неавторизованного пользователя")
    public Response getFullOrderListNotAuthorizedUser() {
        return createBaseRequest()
                .when()
                .get(ORDER_URL); // Отправка GET-запроса без токена
    }
}
