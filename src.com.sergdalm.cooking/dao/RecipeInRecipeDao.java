package dao;

import entity.RecipeInRecipe;
import util.ConnectionManager;
import util.ValueSetterUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RecipeInRecipeDao implements DoubleKeyDao<RecipeInRecipe> {
    private final static RecipeInRecipeDao INSTANCE = new RecipeInRecipeDao();
    private static final String GET_ALL = """
            SELECT recipe_id, used_recipe_id, gram, notes
            FROM cooking_project.cooking.recipe_recipe;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.recipe_recipe(recipe_id, used_recipe_id, gram, notes)
             VALUES (?, ?, ?, ?)
            """;
    private static final String GET = """
            SELECT recipe_id, used_recipe_id, gram, notes
            FROM cooking_project.cooking.recipe_recipe
             WHERE recipe_id = ? AND used_recipe_id = ?;
            """;
    private static final String GET_USED_RECIPE_BY_RECIPE_ID = """
            SELECT recipe_id, used_recipe_id, gram, notes
            FROM cooking_project.cooking.recipe_recipe
             WHERE recipe_id = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.recipe_recipe
            SET gram = ?,
            notes = ?
            WHERE recipe_id = ? AND used_recipe_id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.recipe_recipe
            WHERE recipe_id = ? AND used_recipe_id = ?;
            """;

    private RecipeInRecipeDao() {
    }


    @Override
    public RecipeInRecipe save(RecipeInRecipe recipeInRecipe) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(SAVE)) {
            preparedStatement.setInt(1, recipeInRecipe.getRecipeId());
            preparedStatement.setInt(2, recipeInRecipe.getUsedRecipeId());
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 3, recipeInRecipe, RecipeInRecipe::getGram, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 4, recipeInRecipe, RecipeInRecipe::getNotes, Types.VARCHAR);
            preparedStatement.executeUpdate();
            return recipeInRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeInRecipe get(int recipeId, int usedRecipeId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, usedRecipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildRecipeInRecipe(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeInRecipe> get(int recipeId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_USED_RECIPE_BY_RECIPE_ID)) {
            preparedStatement.setInt(1, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeInRecipe> recipeInRecipes = new ArrayList<>();
            while (resultSet.next()) {
                recipeInRecipes.add(buildRecipeInRecipe(resultSet));
            }
            return recipeInRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeInRecipe> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeInRecipe> recipeInRecipes = new ArrayList<>();
            while (resultSet.next()) {
                recipeInRecipes.add(buildRecipeInRecipe(resultSet));
            }
            return recipeInRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private RecipeInRecipe buildRecipeInRecipe(ResultSet resultSet) {
        try {
            RecipeInRecipe recipeInRecipe = new RecipeInRecipe();
            recipeInRecipe.setRecipeId(resultSet.getInt("recipe_id"));
            recipeInRecipe.setUsedRecipeId(resultSet.getInt("used_recipe_id"));
            ValueSetterUtil.setEntityValueIfNotNull(recipeInRecipe, RecipeInRecipe::setNotes, resultSet, "notes", String.class);
            return recipeInRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeInRecipe update(RecipeInRecipe recipeInRecipe) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 1, recipeInRecipe, RecipeInRecipe::getGram, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 2, recipeInRecipe, RecipeInRecipe::getNotes, Types.VARCHAR);
            preparedStatement.setInt(3, recipeInRecipe.getRecipeId());
            preparedStatement.setInt(4, recipeInRecipe.getUsedRecipeId());
            preparedStatement.executeUpdate();
            return recipeInRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(int recipeId, int usedRecipeId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(DELETE)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, usedRecipeId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static RecipeInRecipeDao getInstance() {
        return INSTANCE;
    }
}
