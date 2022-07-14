package com.sergdalm.cooking.entity;

import com.sergdalm.cooking.util.ValueSetterUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecipeIngredient {
    private Integer recipeId;
    private Integer ingredientId;
    private int gram;
    private String notes;
    @Getter
    private static final String RECIPE_ID = "recipe_id";
    @Getter
    private static final String INGREDIENT_ID = "ingredient_id";
    @Getter
    private static final String GRAM = "gram";
    @Getter
    private static final String NOTES = "notes";

    public static RecipeIngredient build(ResultSet resultSet) {
        try {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipeId(resultSet.getInt(RECIPE_ID));
            recipeIngredient.setIngredientId(resultSet.getInt(INGREDIENT_ID));
            ValueSetterUtil.setEntityValueIfNotNull(recipeIngredient, RecipeIngredient::setGram, resultSet, GRAM, Integer.class);
            ValueSetterUtil.setEntityValueIfNotNull(recipeIngredient, RecipeIngredient::setNotes, resultSet, NOTES, String.class);
            return recipeIngredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
