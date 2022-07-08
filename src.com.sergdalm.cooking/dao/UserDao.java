package dao;

import entity.User;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    private final static UserDao INSTANCE = new UserDao();
    private static final String GET_ALL = """
            SELECT id, email, username, password, is_admin FROM cooking_project.cooking.users;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.users(email, username, password, is_admin)
             VALUES (?, ?, ?, ?);
            """;
    private static final String GET = """
            SELECT id, email, username, password, is_admin
            FROM cooking_project.cooking.users
             WHERE id = ?;
            """;
    private static final String GET_USER_BY_EMAIL = """
            SELECT id, email, username, password, is_admin
            FROM cooking_project.cooking.users
             WHERE email = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.users
            SET email = ?,
            username = ?,
            password = ?,
            is_admin = ?
            WHERE id = ?;
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.users WHERE id = ?;
            """;

    private UserDao() {
    }

    @Override
    public User save(User user) {
        try (var preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBoolean(4, user.getAdmin());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt("id"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User get(String email) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildUser(ResultSet resultSet) {
        try {
            return new User(resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("is_admin")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User user) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBoolean(4, user.getAdmin());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
