package api.test;

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
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Создание пользователей")
public class CreateUserTest {

    private UserSteps userSteps;
    private Response response;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешной регистрации пользователя с валидными данными.")
    public void createUniqueUser() {
        response = userSteps.createUser(UserData.getValidUser());

        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание дубликата пользователя")
    @Description("Проверка, что повторная регистрация одного и того же пользователя вызывает ошибку 403.")
    public void createDuplicateUserReturnsError() {
        UserModel fixedUser = UserData.getValidUser();

        response = userSteps.createUser(fixedUser);
        Response duplicateResponse = userSteps.createUser(fixedUser);

        duplicateResponse.then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @After
    public void tearDown() {
        if (response != null) {
            userSteps.getAccessToken(response);
            userSteps.deleteUser();
        }
    }
}
