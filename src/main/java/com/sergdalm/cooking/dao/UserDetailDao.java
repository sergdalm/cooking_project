package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.UserDetail;
import com.sergdalm.cooking.util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailDao implements Dao<UserDetail>, SingleKeyDao<UserDetail> {
    private final static UserDetailDao INSTANCE = new UserDetailDao();
    private static final String GET_ALL = """
            SELECT user_id, first_name, last_name, birthday FROM cooking_project.cooking.user_details;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.user_details(user_id, first_name, last_name, birthday)
             VALUES (?, ?, ?, ?)
            """;
    private static final String GET = """
            SELECT user_id, first_name, last_name, birthday
            FROM cooking_project.cooking.user_details
             WHERE user_id = ?
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.user_details
            SET first_name = ?,
            last_name = ?,
            birthday = ?
            WHERE user_id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.user_details WHERE user_id = ?
            """;

    private UserDetailDao() {
    }


    @Override
    public UserDetail save(UserDetail user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetail get(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return UserDetail.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserDetail> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserDetail> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(UserDetail.build(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetail update(UserDetail user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDetailDao getInstance() {
        return INSTANCE;
    }
}
