package configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigBD {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigBD.class.getClassLoader().getResourceAsStream("db_config.properties")) {
            if (input == null) {
                throw new IOException("Файл db_config.properties не найден");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
