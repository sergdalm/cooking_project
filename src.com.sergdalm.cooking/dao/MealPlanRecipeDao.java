package dao;

import entity.MealPlanRecipe;
import entity.MealType;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealPlanRecipeDao implements Dao<MealPlanRecipe> {
    private final static MealPlanRecipeDao INSTANCE = new MealPlanRecipeDao();
    private static final String GET_ALL = """
            SELECT id, meal_plan_id, recipe_id, day_number, meal_type FROM cooking_project.cooking.meal_plan_recipe;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.meal_plan_recipe(meal_plan_id, recipe_id, day_number, meal_type)
             VALUES (?, ?, ?, ?);
            """;
    private static final String GET = """
            SELECT id, meal_plan_id, recipe_id, day_number, meal_type
            FROM cooking_project.cooking.meal_plan_recipe
            WHERE id = ?;
            """;
    private static final String GET_BY_PEAL_ID = """
            SELECT id, meal_plan_id, recipe_id, day_number, meal_type FROM cooking_project.cooking.meal_plan_recipe
            WHERE meal_plan_id = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.meal_plan_recipe
            SET meal_plan_id = ?,
            recipe_id = ?,
            day_number = ?,
            meal_type = ?
            WHERE id = ?;
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.meal_plan_recipe  WHERE id = ?
            """;

    private MealPlanRecipeDao() {
    }

    @Override
    public MealPlanRecipe save(MealPlanRecipe mealPlanRecipe) {
        try (var preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE)) {
            preparedStatement.setInt(1, mealPlanRecipe.getMealPlanId());
            preparedStatement.setInt(2, mealPlanRecipe.getRecipeId());
            preparedStatement.setInt(3, mealPlanRecipe.getDayNumber());
            preparedStatement.setString(4, mealPlanRecipe.getMealType().toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            mealPlanRecipe.setId(resultSet.getInt("id"));
            return mealPlanRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MealPlanRecipe get(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildMealPlanRecipe(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MealPlanRecipe> getByPlanId(int mealPlanId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_BY_PEAL_ID)) {
            preparedStatement.setInt(1, mealPlanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MealPlanRecipe> mealPlanRecipes = new ArrayList<>();
            while (resultSet.next()) {
                mealPlanRecipes.add(buildMealPlanRecipe(resultSet));
            }
            return mealPlanRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MealPlanRecipe> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MealPlanRecipe> mealPlanRecipes = new ArrayList<>();
            while (resultSet.next()) {
                mealPlanRecipes.add(buildMealPlanRecipe(resultSet));
            }
            return mealPlanRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MealPlanRecipe buildMealPlanRecipe(ResultSet resultSet) {
        try {
            return new MealPlanRecipe(
                    resultSet.getInt("id"),
                    resultSet.getInt("meal_plan_id"),
                    resultSet.getInt("recipe_id"),
                    resultSet.getInt("day_number"),
                    MealType.valueOf(resultSet.getString("meal_type")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MealPlanRecipe update(MealPlanRecipe mealPlanRecipe) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
            preparedStatement.setInt(1, mealPlanRecipe.getMealPlanId());
            preparedStatement.setInt(2, mealPlanRecipe.getRecipeId());
            preparedStatement.setInt(3, mealPlanRecipe.getDayNumber());
            preparedStatement.setString(4, mealPlanRecipe.getMealType().toString());
            preparedStatement.setInt(5, mealPlanRecipe.getId());
            preparedStatement.executeUpdate();
            return mealPlanRecipe;
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

    public static MealPlanRecipeDao getInstance() {
        return INSTANCE;
    }
}
