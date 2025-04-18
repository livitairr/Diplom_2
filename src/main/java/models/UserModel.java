package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL) // Исключить нулевые поля из JSON при сериализации
public class UserModel {

    private final String email;     // Сделано неизменяемым
    private final String password;  // Сделано неизменяемым
    private final String name;      // Сделано неизменяемым

    // Конструктор с параметрами
    public UserModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Геттеры (сеттеры можно убрать, если поля неизменяемые)

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // Переопределение equals и hashCode для корректного сравнения объектов

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(email, userModel.email) &&
                Objects.equals(password, userModel.password) &&
                Objects.equals(name, userModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name);
    }

    // Переопределение toString для более удобного представления объекта

    @Override
    public String toString() {
        return String.format("UserModel{email='%s', password='%s', name='%s'}", email, password, name);
    }
}
