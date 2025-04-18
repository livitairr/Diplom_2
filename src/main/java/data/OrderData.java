package data;

import models.OrderModel;
import java.util.List;

public class OrderData {

    // Константы для базового URL и эндпоинтов
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String REGISTER_URL = BASE_URL + "/api/auth/register";
    public static final String LOGIN_URL = BASE_URL + "/api/auth/login";
    public static final String AUTH_URL = BASE_URL + "/api/auth/user";

    // Константы для идентификаторов ингредиентов
    private static final String INGREDIENT_1 = "61c0c5a71d1f82001bdaaa6d";
    private static final String INGREDIENT_2 = "61c0c5a71d1f82001bdaaa6f";
    private static final String INGREDIENT_3 = "61c0c5a71d1f82001bdaaa70";
    private static final String INGREDIENT_4 = "61c0c5a71d1f82001bdaaa71";
    private static final String INGREDIENT_5 = "61c0c5a71d1f82001bdaaa72";
    private static final String INGREDIENT_6 = "61c0c5a71d1f82001bdaaa73";
    private static final String INGREDIENT_7 = "61c0c5a71d1f82001bdaaa74";
    private static final String INGREDIENT_8 = "61c0c5a71d1f82001bdaaa75";
    private static final String INGREDIENT_9 = "61c0c5a71d1f82001bdaaa76";
    private static final String INGREDIENT_10 = "61c0c5a71d1f82001bdaaa77";
    private static final String INGREDIENT_11 = "61c0c5a71d1f82001bdaaa78";
    private static final String INGREDIENT_12 = "61c0c5a71d1f82001bdaaa79";
    private static final String INGREDIENT_13 = "61c0c5a71d1f82001bdaaa7a";
    private static final String INGREDIENT_14 = "61c0c5a71d1f82001bdaaa6e";

    // Метод для получения списков заказов с различными комбинациями ингредиентов
    public static List<OrderModel> getOrderBodies() {
        return List.of(
                new OrderModel(List.of(INGREDIENT_1)),
                new OrderModel(List.of(INGREDIENT_2, INGREDIENT_3)),
                new OrderModel(List.of(INGREDIENT_4, INGREDIENT_5, INGREDIENT_6)),
                new OrderModel(List.of(INGREDIENT_7, INGREDIENT_8, INGREDIENT_9, INGREDIENT_10, INGREDIENT_11)),
                new OrderModel(List.of(INGREDIENT_12)),
                new OrderModel(List.of(INGREDIENT_13, INGREDIENT_14, INGREDIENT_6))
        );
    }

    // Метод для получения заказа с пустым списком ингредиентов
    public static OrderModel getEmptyIngredients() {
        return new OrderModel(List.of());
    }

    // Метод для получения заказа с некорректным хешем ингредиента
    public static OrderModel getInvalidHashIngredient() {
        return new OrderModel(List.of("invalid_ingredient_hash_12345"));
    }
}
