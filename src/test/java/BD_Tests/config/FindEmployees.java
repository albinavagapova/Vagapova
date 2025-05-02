package BD_Tests.config;

import configs.ConfigBD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class FindEmployees {
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
    @DisplayName("Поиск сотрудника по существующему ID")
    public void findEmployeeByIdSuccess() throws SQLException {
        int employeeId = 773;
        String query = "SELECT id, first_name, last_name FROM employee WHERE id = " + employeeId;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            assertTrue(resultSet.next());
            assertEquals(employeeId, resultSet.getInt("id"));
            System.out.println("Сотрудник найден: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
        }
    }

    @Test
    @DisplayName("Поиск сотрудника по несуществующему ID")
    public void findEmployeeByNonexistentId() throws SQLException {
        int noneEmployeeId = 0;  // Несуществующий ID сотрудника
        String query = "SELECT id FROM employee WHERE id = " + noneEmployeeId;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Провить, что записи с этим ID нет
            assertFalse(resultSet.next(), "Сотрудник с ID " + noneEmployeeId + " не должен существовать");
            System.out.println("Сотрудника с ID " + noneEmployeeId + " не существует");
        }
    }

}
