package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.RecipeSpice;
import com.sergdalm.cooking.util.ConnectionManager;
import com.sergdalm.cooking.util.ValueSetterUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RecipeSpiceDao implements Dao<RecipeSpice>, DoubleKeyDao<RecipeSpice> {
    private final static RecipeSpiceDao INSTANCE = new RecipeSpiceDao();
    private static final String GET_ALL = """
            SELECT recipe_id, spice_id, notes
            FROM cooking_project.cooking.recipe_spice;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.recipe_spice(recipe_id, spice_id, notes)
             VALUES (?, ?, ?)
            """;
    private static final String GET = """
            SELECT recipe_id, spice_id, notes
            FROM cooking_project.cooking.recipe_spice
             WHERE recipe_id = ? AND spice_id = ?;
            """;

    private static final String GET_BY_RECIPE_ID = """
            SELECT recipe_id, spice_id, notes FROM cooking_project.cooking.recipe_spice
            WHERE recipe_id = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.recipe_spice
            SET notes = ?
            WHERE recipe_id = ? AND spice_id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.recipe_spice
            WHERE recipe_id = ? AND spice_id = ?;
            """;

    private RecipeSpiceDao() {
    }

    @Override
    public RecipeSpice save(RecipeSpice recipeSpice) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setInt(1, recipeSpice.getRecipeId());
            preparedStatement.setInt(2, recipeSpice.getSpiceId());
            preparedStatement.setString(3, recipeSpice.getNotes());
            preparedStatement.executeUpdate();
            return recipeSpice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeSpice get(int recipeId, int spiceId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, spiceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return RecipeSpice.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeSpice> get(int recipeId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_RECIPE_ID)) {
            preparedStatement.setInt(1, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeSpice> recipeSpices = new ArrayList<>();
            while (resultSet.next()) {
                recipeSpices.add(RecipeSpice.build(resultSet));
            }
            return recipeSpices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeSpice> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeSpice> recipeSpices = new ArrayList<>();
            while (resultSet.next()) {
                recipeSpices.add(RecipeSpice.build(resultSet));
            }
            return recipeSpices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeSpice update(RecipeSpice recipeSpice) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 1, recipeSpice, RecipeSpice::getNotes, Types.VARCHAR);
            preparedStatement.setInt(2, recipeSpice.getRecipeId());
            preparedStatement.setInt(3, recipeSpice.getSpiceId());
            preparedStatement.executeUpdate();
            return recipeSpice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(int recipeId, int spiceId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, spiceId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static RecipeSpiceDao getInstance() {
        return INSTANCE;
    }
}
