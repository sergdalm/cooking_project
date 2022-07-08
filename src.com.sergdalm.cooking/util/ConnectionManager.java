package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static Connection connection;

    static {
        loadDriver();
    }

    public static Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ВОПРОС:
    // я пыталась подтянуть драйвер БД из файла application.properties вызвав метод Class.forName(DRIVER_KEY)
    // вместо Class.forName("org.postgresql.Driver"), однако в таком случае драйвер не подгружался и падало исключение.
    // Не могу понять почему драйвер не получается подтянуть таким способом.
    // Ты не знаешь почему драйвер не удается подтянуть как мы подтягиваем URL_KEY, USER_KEY PASSWORD_KEY?
    // Имя драйвера у меня указано также как тут в скобочках - Class.forName("org.postgresql.Driver")
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        ConnectionManager.connection = get();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;

    }

    public static PreparedStatement getPreparedStatementWithGeneratedKeys(String sql) {
        ConnectionManager.connection = get();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;

    }

    public static Statement getStatement() {
        ConnectionManager.connection = get();
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Connection must be closed before stopping the application
    public static void closeConnection() {
        try {
            if (!ConnectionManager.connection.isClosed()) {
                ConnectionManager.connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
