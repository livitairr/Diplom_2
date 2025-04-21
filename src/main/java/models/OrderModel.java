package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL) // Исключить нулевые поля из JSON при сериализации
public class OrderModel {

    private final List<String> ingredients; // Сделать поле неизменяемым

    // Конструктор без аргументов (необходим для Jackson)
    public OrderModel() {
        this.ingredients = Collections.emptyList(); // по умолчанию пустой список
    }

    // Конструктор с параметрами
    public OrderModel(List<String> ingredients) {
        this.ingredients = ingredients != null ? ingredients : Collections.emptyList(); // Проверка на null
    }

    // Геттеры

    /**
     * Получить список ингредиентов.
     * @return список ингредиентов.
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    // Переопределение equals и hashCode для корректного сравнения объектов

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients);
    }

    // Переопределение toString для более читабельного представления объекта

    @Override
    public String toString() {
        return "OrderModel{" +
                "ingredients=" + ingredients +
                '}';
    }
}
