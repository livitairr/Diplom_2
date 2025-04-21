package api.test;

import data.OrderData;
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

@Epic("Создание заказов")
public class CreateOrderTest {

    private UserSteps userSteps;
    private OrderSteps orderSteps;
    private Response response;
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

        userSteps = new UserSteps();
        orderSteps = new OrderSteps();

        response = userSteps.createUser(UserData.getValidUser());
        userSteps.getAccessToken(response);
        token = userSteps.accessToken;
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    @Description("Проверка успешного создания заказа с авторизацией и валидными ингредиентами.")
    public void createOrderWithAuth() {
        orderSteps.createOrderWithAuthorization(token, OrderData.getOrderBodies().get(5))
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue())
                .body("order._id", notNullValue())
                .body("order.owner", notNullValue())
                .body("order.status", equalTo("done"))
                .body("order.createdAt", notNullValue())
                .body("order.updatedAt", notNullValue())
                .body("order.number", notNullValue())
                .body("order.price", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка возможности создания заказа без авторизации.")
    public void createOrderWithoutAuth() {
        orderSteps.createOrderWithoutAuthorization(OrderData.getOrderBodies().get(4))
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка ошибки при создании заказа без указания ингредиентов.")
    public void createOrderWithoutIngredients() {
        orderSteps.createOrderWithAuthorization(token, OrderData.getEmptyIngredients())
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиента")
    @Description("Проверка поведения сервера при использовании невалидного хеша ингредиента.")
    public void createOrderWithInvalidIngredientHash() {
        orderSteps.createOrderWithAuthorization(token, OrderData.getInvalidHashIngredient())
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        if (response != null) {
            userSteps.getAccessToken(response);
            userSteps.deleteUser();
        }
    }
}
