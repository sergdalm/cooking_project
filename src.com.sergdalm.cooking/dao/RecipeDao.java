package dao;

import entity.Recipe;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao implements Dao<Recipe> {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            recipe.setId(resultSet.getInt("id"));
            return recipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe get(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildRecipe(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Recipe> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Recipe> recipes = new ArrayList<>();
            while (resultSet.next()) {
                recipes.add(buildRecipe(resultSet));
            }
            return recipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Recipe buildRecipe(ResultSet resultSet) {
        try {
            return new Recipe(resultSet.getInt("id"),
                    resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe update(Recipe recipe) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
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
        try (var preparedStatement = ConnectionManager.getPreparedStatement(DELETE)) {
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
