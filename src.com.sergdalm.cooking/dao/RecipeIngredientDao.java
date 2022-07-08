package dao;

import entity.RecipeIngredient;
import util.ConnectionManager;
import util.ValueSetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RecipeIngredientDao implements DoubleKeyDao<RecipeIngredient> {
    private final static RecipeIngredientDao INSTANCE = new RecipeIngredientDao();
    private static final String GET_ALL = """
            SELECT recipe_id, ingredient_id, gram, notes
            FROM cooking_project.cooking.recipe_ingredient;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.recipe_ingredient(recipe_id, ingredient_id, gram, notes)
            VALUES (?, ?, ?, ?)
            """;
    private static final String GET = """
            SELECT recipe_id, ingredient_id, gram, notes FROM cooking_project.cooking.recipe_ingredient
             WHERE recipe_id = ? AND ingredient_id = ?;
            """;
    private static final String GET_BY_RECIPE_ID = """
            SELECT recipe_id, ingredient_id, gram, notes FROM cooking_project.cooking.recipe_ingredient
            WHERE recipe_id = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.recipe_ingredient
            SET gram = ?,
            notes = ?
            WHERE recipe_id = ? AND ingredient_id = ?;
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.recipe_ingredient
            WHERE recipe_id = ? AND ingredient_id = ?;
            """;

    private RecipeIngredientDao() {
    }

    @Override
    public RecipeIngredient save(RecipeIngredient recipeIngredient) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(SAVE)) {
            preparedStatement.setInt(1, recipeIngredient.getRecipeId());
            preparedStatement.setInt(2, recipeIngredient.getIngredientId());
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 3, recipeIngredient, RecipeIngredient::getGram, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 4, recipeIngredient, RecipeIngredient::getNotes, Types.VARCHAR);
            preparedStatement.executeUpdate();
            return recipeIngredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeIngredient get(int recipeId, int ingredientId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, ingredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildRecipeIngredient(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeIngredient> get(int recipeId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_BY_RECIPE_ID)) {
            preparedStatement.setInt(1, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeIngredient> recipeIngredients = new ArrayList<>();
            while (resultSet.next()) {
                recipeIngredients.add(buildRecipeIngredient(resultSet));
            }
            return recipeIngredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeIngredient> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeIngredient> recipeIngredients = new ArrayList<>();
            while (resultSet.next()) {
                recipeIngredients.add(buildRecipeIngredient(resultSet));
            }
            return recipeIngredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private RecipeIngredient buildRecipeIngredient(ResultSet resultSet) {
        try {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipeId(resultSet.getInt("recipe_id"));
            recipeIngredient.setIngredientId(resultSet.getInt("ingredient_id"));
            ValueSetterUtil.setEntityValueIfNotNull(recipeIngredient, RecipeIngredient::setGram, resultSet, "gram", Integer.class);
            ValueSetterUtil.setEntityValueIfNotNull(recipeIngredient, RecipeIngredient::setNotes, resultSet, "notes", String.class);
            return recipeIngredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeIngredient update(RecipeIngredient recipeIngredient) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
            setValueIfNotNull(preparedStatement, 1, recipeIngredient, RecipeIngredient::getGram);
            setValueIfNotNull(preparedStatement, 2, recipeIngredient, RecipeIngredient::getNotes);
            preparedStatement.setInt(3, recipeIngredient.getRecipeId());
            preparedStatement.setInt(4, recipeIngredient.getIngredientId());
            preparedStatement.executeUpdate();
            return recipeIngredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void setValueIfNotNull(PreparedStatement preparedStatement, int number, RecipeIngredient recipeDetail,
                                       Function<RecipeIngredient, T> function) throws SQLException {
        if (function.apply(recipeDetail) == null) {
            preparedStatement.setNull(number, Types.INTEGER);
        } else {
            preparedStatement.setObject(number, function.apply(recipeDetail));
        }
    }

    @Override
    public boolean delete(int recipeId, int ingredientId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(DELETE)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, ingredientId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static RecipeIngredientDao getInstance() {
        return INSTANCE;
    }

}
