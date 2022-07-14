package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.Ingredient;
import com.sergdalm.cooking.entity.Recipe;
import com.sergdalm.cooking.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao implements Dao<Recipe>, SingleKeyDao<Recipe> {
    private final static RecipeDao INSTANCE = new RecipeDao();
    private static final String GET_ALL = """
            SELECT * FROM cooking_project.cooking.recipe;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.recipe(name)
             VALUES (?)
            """;
    private static final String GET = """
            SELECT id, name
            FROM cooking_project.cooking.recipe
             WHERE id = ?
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.recipe
            SET name = ?
            WHERE id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.recipe  WHERE id = ?
            """;

    private RecipeDao() {
    }

    @Override
    public Recipe save(Recipe recipe) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            recipe.setId(resultSet.getInt(Ingredient.getID()));
            return recipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe get(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Recipe.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Recipe> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Recipe> recipes = new ArrayList<>();
            while (resultSet.next()) {
                recipes.add(Recipe.build(resultSet));
            }
            return recipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe update(Recipe recipe) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setInt(1, recipe.getId());
            preparedStatement.executeUpdate();
            return recipe;
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

    public static RecipeDao getInstance() {
        return INSTANCE;
    }
}
