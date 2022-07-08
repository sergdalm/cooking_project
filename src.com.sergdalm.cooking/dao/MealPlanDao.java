package dao;

import entity.MealPlan;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealPlanDao implements Dao<MealPlan> {
    private final static MealPlanDao INSTANCE = new MealPlanDao();
    private static final String GET_ALL = """
            SELECT id, user_id, name FROM cooking_project.cooking.meal_plan;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.meal_plan(name, user_id)
             VALUES (?, ?);
            """;
    private static final String GET = """
            SELECT id, name, user_id
            FROM cooking_project.cooking.meal_plan
             WHERE id = ?;
            """;
    private static final String GET_BY_USER_ID = """
            SELECT id, name, user_id
            FROM cooking_project.cooking.meal_plan
            WHERE user_id = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.meal_plan
            SET name = ?,
            user_id = ?
            WHERE id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.meal_plan  WHERE id = ?
            """;

    private MealPlanDao() {
    }


    @Override
    public MealPlan save(MealPlan mealPlan) {
        try (var preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE)) {
            preparedStatement.setString(1, mealPlan.getName());
            preparedStatement.setInt(2, mealPlan.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            mealPlan.setId(resultSet.getInt("id"));
            return mealPlan;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MealPlan get(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildMealPlan(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MealPlan> getByUser(int userId) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MealPlan> mealPlans = new ArrayList<>();
            while (resultSet.next()) {
                mealPlans.add(buildMealPlan(resultSet));
            }
            return mealPlans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MealPlan> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MealPlan> mealPlans = new ArrayList<>();
            while (resultSet.next()) {
                mealPlans.add(buildMealPlan(resultSet));
            }
            return mealPlans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MealPlan buildMealPlan(ResultSet resultSet) {
        try {
            return new MealPlan(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MealPlan update(MealPlan mealPlan) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
            preparedStatement.setString(1, mealPlan.getName());
            preparedStatement.setInt(2, mealPlan.getUserId());
            preparedStatement.setInt(3, mealPlan.getId());
            preparedStatement.executeUpdate();
            return mealPlan;
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

    public static MealPlanDao getInstance() {
        return INSTANCE;
    }
}
