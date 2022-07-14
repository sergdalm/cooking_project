package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.Spice;
import com.sergdalm.cooking.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpiceDao implements Dao<Spice>, SingleKeyDao<Spice> {
    private final static SpiceDao INSTANCE = new SpiceDao();
    private static final String GET_ALL = """
            SELECT * FROM cooking_project.cooking.spice;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.spice(name)
             VALUES (?);
            """;
    private static final String GET = """
            SELECT id, name
            FROM cooking_project.cooking.spice
             WHERE id = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.spice
            SET name = ?
            WHERE id = ?;
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.spice  WHERE id = ?;
            """;

    private SpiceDao() {
    }

    @Override
    public Spice save(Spice spice) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, spice.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            spice.setId(resultSet.getInt(Spice.getID()));
            return spice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Spice get(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Spice.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Spice> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Spice> spices = new ArrayList<>();
            while (resultSet.next()) {
                spices.add(Spice.build(resultSet));
            }
            return spices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Spice update(Spice spice) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, spice.getName());
            preparedStatement.setInt(2, spice.getId());
            preparedStatement.executeUpdate();
            return spice;
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

    public static SpiceDao getInstance() {
        return INSTANCE;
    }
}
