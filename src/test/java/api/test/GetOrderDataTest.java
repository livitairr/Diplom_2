package api.test;

import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import static data.OrderData.BASE_URL;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@Epic("Получение данных о заказах") // Эпик для Allure-отчётов
public class GetOrderDataTest {

    private UserSteps userSteps;
    private OrderSteps orderSteps;
    private Response response;
    private String token;

    @Before
    public void setUp() {
        // Устанавливаем базовый URL для API
        RestAssured.baseURI = BASE_URL;

        // Создаём объекты шагов для работы с юзерами и заказами
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();

        // Создаём нового пользователя и получаем его accessToken
        response = userSteps.createUser(UserData.getValidUser());
        userSteps.getAccessToken(response);
        token = userSteps.accessToken;
    }

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    @Description("Проверка возможности получения списка заказов для авторизованного пользователя.")
    public void getOrderListForAuthorizedUser() {
        orderSteps.getOrderListAuthorizedUser(token)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .body("orders.size()", notNullValue());
    }

    @Test
    @DisplayName("Попытка получить заказы без авторизации")
    @Description("Проверка, что неавторизованный пользователь не может получить заказы, получает ошибку 401.")
    public void getFullOrderListForNotAuthorizedUser() {
        orderSteps.getFullOrderListNotAuthorizedUser()
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void tearDown() {
        if (response != null) {
            // Удаляем тестового пользователя
            userSteps.deleteUser();
        }
    }
}
