package data;

import com.github.javafaker.Faker;
import models.UserModel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static data.OrderData.BASE_URL;

public class UserData {

    // Константы
    private static final Faker faker = new Faker(); // Генератор случайных данных Faker
    public static final String ORDER_URL = BASE_URL + "/api/orders";

    // Методы

    /**
     * Создает валидного пользователя с уникальными данными.
     * @return новый объект UserModel с уникальными данными.
     */
    public static UserModel getValidUser() {
        return new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(7, 20),
                faker.name().firstName()
        );
    }

    /**
     * Возвращает список невалидных пользователей для параметризованных тестов.
     * @return список невалидных пользователей.
     */
    public static List<UserModel> getInvalidUserRequests() {
        return Stream.of(
                new UserModel("", faker.internet().password(7, 20), faker.name().firstName()),  // Пустой email
                new UserModel(faker.internet().emailAddress(), "", faker.name().firstName()),  // Пустой пароль
                new UserModel(faker.internet().emailAddress(), faker.internet().password(7, 20), "")  // Пустое имя
        ).collect(Collectors.toList());
    }

    /**
     * Возвращает список пользователей с невалидными данными для тестирования авторизации.
     * @return список пользователей с невалидными данными.
     */
    public static List<UserModel> getInvalidLoginRequests() {
        return Stream.of(
                new UserModel("invalidemail@test.com", faker.internet().password(7, 20), null),  // Неверный email
                new UserModel(faker.internet().emailAddress(), "incorrectpassword", null),  // Неверный пароль
                new UserModel("invalidemail@test.com", "incorrectpassword", null)  // Неверные данные
        ).collect(Collectors.toList());
    }

    /**
     * Возвращает список вариантов обновления данных пользователя.
     * @return список моделей пользователя для обновления данных.
     */
    public static List<UserModel> getUserDataUpdateBodies() {
        return Stream.of(
                new UserModel(faker.internet().emailAddress(), null, null),  // Обновление email
                new UserModel(null, faker.internet().password(7, 20), null),  // Обновление пароля
                new UserModel(null, null, faker.name().lastName())  // Обновление имени
        ).collect(Collectors.toList());
    }
}
