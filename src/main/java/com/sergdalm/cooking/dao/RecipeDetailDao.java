package com.sergdalm.cooking.dao;

import com.sergdalm.cooking.entity.RecipeDetail;
import com.sergdalm.cooking.util.ConnectionManager;
import com.sergdalm.cooking.util.ValueSetterUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailDao implements Dao<RecipeDetail>, SingleKeyDao<RecipeDetail> {
    private final static RecipeDetailDao INSTANCE = new RecipeDetailDao();
    private static final String GET_ALL = """
            SELECT recipe_id, user_id, description,
            active_cooking_time_minute, total_cooking_time_minute, number_of_portion
            FROM cooking_project.cooking.recipe_detail;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.recipe_detail(recipe_id, user_id, description,
            active_cooking_time_minute, total_cooking_time_minute, number_of_portion)
             VALUES (?, ?, ?, ?, ?, ?);
            """;

    private static final String GET = """
            SELECT recipe_id, user_id, description,
            active_cooking_time_minute, total_cooking_time_minute, number_of_portion
            FROM cooking_project.cooking.recipe_detail
            WHERE recipe_id = ?
            """;

    private static final String UPDATE = """
            UPDATE cooking_project.cooking.recipe_detail
            SET description = ?,
            active_cooking_time_minute = ?,
            total_cooking_time_minute = ?,
            number_of_portion = ?
            WHERE recipe_id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.recipe_detail  WHERE recipe_id = ?
            """;

    private RecipeDetailDao() {
    }

    @Override
    public RecipeDetail save(RecipeDetail recipeDetail) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setInt(1, recipeDetail.getRecipeId());
            preparedStatement.setInt(2, recipeDetail.getUserId());
            preparedStatement.setString(3, recipeDetail.getDescription());
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 4, recipeDetail,
                    RecipeDetail::getActiveCookingTimeMinute, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 5, recipeDetail,
                    RecipeDetail::getTotalCookingTimeMinute, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 6, recipeDetail,
                    RecipeDetail::getNumberOfPortions, Types.INTEGER);
            preparedStatement.executeUpdate();
            return recipeDetail;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeDetail get(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return RecipeDetail.build(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RecipeDetail> getAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipeDetail> recipeDetails = new ArrayList<>();
            while (resultSet.next()) {
                recipeDetails.add(RecipeDetail.build(resultSet));
            }
            return recipeDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RecipeDetail update(RecipeDetail recipeDetail) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setInt(2, recipeDetail.getUserId());
            preparedStatement.setString(1, recipeDetail.getDescription());
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 2, recipeDetail,
                    RecipeDetail::getActiveCookingTimeMinute, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 3, recipeDetail,
                    RecipeDetail::getTotalCookingTimeMinute, Types.INTEGER);
            ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 4, recipeDetail,
                    RecipeDetail::getNumberOfPortions, Types.INTEGER);
            preparedStatement.setInt(5, recipeDetail.getRecipeId());
            preparedStatement.executeUpdate();
            return recipeDetail;
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

    public static RecipeDetailDao getInstance() {
        return INSTANCE;
    }
}
