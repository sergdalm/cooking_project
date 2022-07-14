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
public class RecipeInRecipe {
    private Integer recipeId;
    private Integer usedRecipeId;
    private Integer gram;
    private String notes;
    @Getter
    private static final String RECIPE_ID = "recipe_id";
    @Getter
    private static final String USED_RECIPE_ID = "used_recipe_id";
    @Getter
    private static final String GRAM = "gram";
    @Getter
    private static final String NOTES = "notes";


    public static RecipeInRecipe build(ResultSet resultSet) {
        try {
            RecipeInRecipe recipeInRecipe = new RecipeInRecipe();
            recipeInRecipe.setRecipeId(resultSet.getInt(RECIPE_ID));
            recipeInRecipe.setUsedRecipeId(resultSet.getInt(USED_RECIPE_ID));
            ValueSetterUtil.setEntityValueIfNotNull(recipeInRecipe, RecipeInRecipe::setNotes, resultSet, NOTES, String.class);
            return recipeInRecipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
