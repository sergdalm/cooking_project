package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.UserRecipe;
import com.sergdalm.cooking.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRecipeDao implements Dao<UserRecipe>, DoubleKeyDao<UserRecipe>{
    private final static UserRecipeDao INSTANCE = new UserRecipeDao();
    private static final String GET_ALL = """
            SELECT user_id, recipe_id
            FROM cooking_project.cooking.user_favorite_recipes;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.user_favorite_recipes(user_id, recipe_id)
             VALUES (?, ?)
            """;
    private static final String GET = """
            SELECT user_id, recipe_id
            FROM cooking_project.cooking.user_favorite_recipes
             WHERE user_id = ?
            """;

    private static final String UPDATE = """
            UPDATE cooking_project.cooking.user_favorite_recipes
            SET recipe_id = ?
            WHERE user_id = ? AND recipe_id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.user_favorite_recipes
            WHERE user_id = ? AND recipe_id = ?;
            """;

    private UserRecipeDao() {
    }

    @Override
    public UserRecipe save(UserRecipe userRecipe) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setInt(1, userRecipe.getUserId());
            preparedStatement.setInt(2, userRecipe.getRecipeId());
            preparedStatement.executeUpdate();
            return userRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRecipe get(int userId, int recipeId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return UserRecipe.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRecipe> get(int userId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRecipe> userRecipes = new ArrayList<>();
            while (resultSet.next()) {
                userRecipes.add(UserRecipe.build(resultSet));
            }
            return userRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRecipe> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRecipe> userRecipes = new ArrayList<>();
            while (resultSet.next()) {
                userRecipes.add(UserRecipe.build(resultSet));
            }
            return userRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRecipe update(UserRecipe userRecipe) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setInt(1, userRecipe.getUserId());
            preparedStatement.setInt(2, userRecipe.getRecipeId());
            preparedStatement.executeUpdate();
            return userRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int userId, int recipeId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserRecipeDao getInstance() {
        return INSTANCE;
    }
}
