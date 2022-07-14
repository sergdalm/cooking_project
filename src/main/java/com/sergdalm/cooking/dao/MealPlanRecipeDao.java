package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.MealPlanRecipe;
import com.sergdalm.cooking.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealPlanRecipeDao implements Dao<MealPlanRecipe>, SingleKeyDao<MealPlanRecipe> {
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
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, mealPlanRecipe.getMealPlanId());
            preparedStatement.setInt(2, mealPlanRecipe.getRecipeId());
            preparedStatement.setInt(3, mealPlanRecipe.getDayNumber());
            preparedStatement.setString(4, mealPlanRecipe.getMealType().toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            mealPlanRecipe.setId(resultSet.getInt(MealPlanRecipe.getID()));
            return mealPlanRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MealPlanRecipe get(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return MealPlanRecipe.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MealPlanRecipe> getByPlanId(int mealPlanId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_PEAL_ID)) {
            preparedStatement.setInt(1, mealPlanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MealPlanRecipe> mealPlanRecipes = new ArrayList<>();
            while (resultSet.next()) {
                mealPlanRecipes.add(MealPlanRecipe.build(resultSet));
            }
            return mealPlanRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MealPlanRecipe> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MealPlanRecipe> mealPlanRecipes = new ArrayList<>();
            while (resultSet.next()) {
                mealPlanRecipes.add(MealPlanRecipe.build(resultSet));
            }
            return mealPlanRecipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MealPlanRecipe update(MealPlanRecipe mealPlanRecipe) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
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
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE)) {
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
