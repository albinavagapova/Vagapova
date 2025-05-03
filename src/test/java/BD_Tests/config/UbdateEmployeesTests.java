package BD_Tests.config;

import configs.ConfigBD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class UbdateEmployeesTests {

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
    @DisplayName("Успешное обновление данных сотрудника с Id 777")
    public void updateEmployeeSuccess() throws SQLException {
        int employeeId = 777;
        String updateQuery = "UPDATE employee SET first_name = 'НовыйРоман', email = 'newemail@example.com' WHERE id = " + employeeId;

        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(updateQuery);
            assertEquals(1, rowsAffected, "Имя обновлено");

            // Проверка, что данные обновлены корректно
            String selectQuery = "SELECT first_name, email FROM employee WHERE id = " + employeeId;
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                if (resultSet.next()) {
                    assertEquals("НовыйРоман", resultSet.getString("first_name"), "Имя обновлено");
                    assertEquals("newemail@example.com", resultSet.getString("email"), "Email сотрудника должен быть обновлен");
                } else {
                    fail("Сотрудник с данным Id не найден");
                }
            }
        }
    }

    @Test
    @DisplayName("Обновление данных сотрудника с некорректными данными")
    public void updateEmployeeWithInvalidData() throws SQLException {
        int employeeId = 228;
        String updateQuery = "UPDATE employee SET first_name = NULL WHERE id = " + employeeId;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateQuery);
            System.out.println("данные обновлены");
        } catch (SQLException e) {
            assertTrue(e.getMessage().toLowerCase().contains("null value"), "Ошибка ");
        }
    }
}
