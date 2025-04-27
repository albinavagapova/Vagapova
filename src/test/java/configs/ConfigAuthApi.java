package configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigAuthApi {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigAuthApi.class.getClassLoader().getResourceAsStream("auth_config.properties")) {
            if (input == null) {
                throw new IOException("Файл конфигурации auth_config.properties не найден");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки конфигурации", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
