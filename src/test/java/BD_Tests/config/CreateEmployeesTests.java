package BD_Tests.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import configs.ConfigBD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateEmployeesTests {

    private static Connection connection;
    private static final String CONNECTION_STRING = ConfigBD.getProperty("CONNECTION_STRING");
    private static final String LOGIN = ConfigBD.getProperty("LOGIN");
    private static final String PASSWORD = ConfigBD.getProperty("PASSWORD");

    @BeforeAll
    public static void setup() throws SQLException {
       connection = DriverManager.getConnection(CONNECTION_STRING, LOGIN, PASSWORD);
    }

    @AfterAll
    public static void close() throws SQLException {
        // Закрытие соединения после выполнения всех тестов
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Успешное создание сотрудника")
    public void createEmployeeSuccess() throws SQLException {
        // Генерация случайного айди сотрудника
        int employeeId = (int) (Math.random() * 10000 + 1);
        String insertQuery = "INSERT INTO employee (id, first_name, last_name, middle_name, phone, birthdate, avatar_url, company_id) " +
                "VALUES (" + employeeId + ", 'Степан', 'Кошкин', 'Сергеевич', '89878896969', '2000-05-06', '217.ru', 687)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertQuery);
            // Проверить, что такой сотрудник добавился
            String selectQuery = "SELECT id FROM employee WHERE id = " + employeeId;
            ResultSet resultSet = statement.executeQuery(selectQuery);
            assertTrue(resultSet.next(), "Сотрудник с ID " + employeeId + " существует");
        }
    }


    // Создание сотрудника с некорректными данными
    @Test
    @DisplayName("Создание сотрудника с пустыми данными - должны получить bisiness exception")
    public void createEmployeeWithInvalidData() throws SQLException {
        String insertQuery = "INSERT INTO employee (id, first_name, last_name, middle_name, phone, birthdate, avatar_url, company_id) " +
                "VALUES (999, NULL, NULL)";
        ;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            System.out.println("Не все обязательные поля заполнены");
            assertFalse(e.getMessage().toLowerCase().contains("null"));
        }
    }







}



