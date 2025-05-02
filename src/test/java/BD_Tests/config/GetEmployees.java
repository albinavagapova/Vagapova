package BD_Tests.config;

import configs.ConfigBD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetEmployees {

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

    // Получение всех ID сотрудников по company_id
    @Test
    @DisplayName("Проверить, что опреденные Id сотрудников есть в списке компании")
    public void checkEmployeeIdsPresenceByCompanyId() throws SQLException {
        int companyId = 687;
        String query = "SELECT id FROM employee WHERE company_id = " + companyId;
        // Ожидаемые ID сотрудников
        List<Integer> expectedEmployeeIds = List.of(777, 787, 779);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            // Фактические ID сотрудников
            List<Integer> actualEmployeeIds = new ArrayList<>();
            System.out.println("Список ID сотрудников:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                actualEmployeeIds.add(id); // Добавляем ID в список
                System.out.println(id);
            }
            // Проверяем, что ожидаемык id есть в списке
            for (int expectedId : expectedEmployeeIds) {
                assertTrue(actualEmployeeIds.contains(expectedId), "ID сотрудника " + expectedId + " должен присутствовать");
            }
        }
    }

    @Test
    @DisplayName("Получить всех Id сотрудников по несуществующему company_id")
    public void getAllEmployeeIdsByNonexistentCompanyId() throws SQLException {
        int noneCompanyId = 99999;
        String query = "SELECT id FROM employee WHERE company_id = " + noneCompanyId;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Список для фактических Id сотрудников
            List<Integer> actualEmployeeIds = new ArrayList<>();
            System.out.println("Список Id сотрудников:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                actualEmployeeIds.add(id); // Добавляем ID в список
                System.out.println(id); // Логируем найденные ID
            }
            // Проверяем, что список пустой
            assertTrue(actualEmployeeIds.isEmpty(), "Список Id сотрудников должен быть пустым для несуществующей компании");
        }
    }

}
