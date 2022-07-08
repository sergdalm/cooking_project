package dao;

import entity.UserRecipe;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRecipeDao implements DoubleKeyDao<UserRecipe> {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatement(SAVE)) {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildUserRecipe(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRecipe> get(int userId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRecipe> userRecipes = new ArrayList<>();
            while (resultSet.next()) {
                userRecipes.add(buildUserRecipe(resultSet));
            }
            return userRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRecipe> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRecipe> userRecipes = new ArrayList<>();
            while (resultSet.next()) {
                userRecipes.add(buildUserRecipe(resultSet));
            }
            return userRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserRecipe buildUserRecipe(ResultSet resultSet) {
        try {
            return new UserRecipe(
                    resultSet.getInt("user_id"),
                    resultSet.getInt("recipe_id")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRecipe update(UserRecipe userRecipe) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatement(DELETE)) {
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
