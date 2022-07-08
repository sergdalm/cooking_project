package dao;

import entity.Spice;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpiceDao implements Dao<Spice> {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE)) {
            preparedStatement.setString(1, spice.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            spice.setId(resultSet.getInt("id"));
            return spice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Spice get(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildSpice(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Spice> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Spice> spices = new ArrayList<>();
            while (resultSet.next()) {
                spices.add(buildSpice(resultSet));
            }
            return spices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Spice buildSpice(ResultSet resultSet) {
        try {
            return new Spice(resultSet.getInt("id"),
                    resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Spice update(Spice spice) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatement(DELETE)) {
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
