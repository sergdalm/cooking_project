package dao;

import entity.Ingredient;
import util.ConnectionManager;
import util.ValueSetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements Dao<Ingredient> {
    private final static IngredientDao INSTANCE = new IngredientDao();
    private static final String GET_ALL = """
            SELECT id, name, weight_of_one_piece_gram, kilocalories, protein_gr, fat_gr, carbohydrate_gr
            FROM cooking_project.cooking.ingredient;
            """;
    private static final String SAVE = """
            INSERT INTO cooking_project.cooking.ingredient(name, weight_of_one_piece_gram,
             kilocalories, protein_gr, fat_gr, carbohydrate_gr)
            VALUES (?, ?, ?, ?, ? , ?)
            """;
    private static final String GET = """
            SELECT id, name, weight_of_one_piece_gram,
             kilocalories, protein_gr, fat_gr, carbohydrate_gr
             FROM cooking_project.cooking.ingredient
             WHERE id = ?
            """;
    private static final String GET_INGREDIENT_BY_NAME = """
            SELECT id, name, weight_of_one_piece_gram,
             kilocalories, protein_gr, fat_gr, carbohydrate_gr
             FROM cooking_project.cooking.ingredient
             WHERE name = ?;
            """;
    private static final String UPDATE = """
            UPDATE cooking_project.cooking.ingredient
            SET name = ?,
            weight_of_one_piece_gram = ?,
            kilocalories = ?,
            protein_gr = ?,
            fat_gr = ?,
            carbohydrate_gr = ?
            WHERE id = ?
            """;
    private static final String DELETE = """
            DELETE FROM cooking_project.cooking.ingredient
            WHERE id = ?
            """;

    private IngredientDao() {
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        try (var preparedStatement = ConnectionManager.getPreparedStatementWithGeneratedKeys(SAVE)) {
            setValues(preparedStatement, ingredient);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            ingredient.setId(resultSet.getInt("id"));
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient get(int id) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildIngredient(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ingredient get(String name) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_INGREDIENT_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return buildIngredient(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> getAll() {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ingredient> ingredients = new ArrayList<>();
            while (resultSet.next()) {
                ingredients.add(buildIngredient(resultSet));
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ingredient buildIngredient(ResultSet resultSet) {
        try {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(resultSet.getInt("id"));
            ingredient.setName(resultSet.getString("name"));
            ValueSetterUtil.setEntityValueIfNotNull(ingredient, Ingredient::setWeightOfOnePieceGram, resultSet, "weight_of_one_piece_gram", Integer.class);
            ingredient.setKilocalories(resultSet.getInt("kilocalories"));
            ingredient.setProtein(resultSet.getDouble("protein_gr"));
            ingredient.setFat(resultSet.getDouble("fat_gr"));
            ingredient.setCarbohydrate(resultSet.getDouble("carbohydrate_gr"));
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient update(Ingredient ingredient) {
        try (var preparedStatement = ConnectionManager.getPreparedStatement(UPDATE)) {
            setValues(preparedStatement, ingredient);
            preparedStatement.setInt(7, ingredient.getId());
            preparedStatement.executeUpdate();
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setValues(PreparedStatement preparedStatement, Ingredient ingredient) throws SQLException {
        preparedStatement.setString(1, ingredient.getName());
        ValueSetterUtil.setPreparedStatementValueIfNotNull(preparedStatement, 2, ingredient, Ingredient::getWeightOfOnePieceGram, Types.INTEGER);
        preparedStatement.setInt(3, ingredient.getKilocalories());
        preparedStatement.setDouble(4, ingredient.getProtein());
        preparedStatement.setDouble(5, ingredient.getFat());
        preparedStatement.setDouble(6, ingredient.getCarbohydrate());
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

    public static IngredientDao getInstance() {
        return INSTANCE;
    }
}
