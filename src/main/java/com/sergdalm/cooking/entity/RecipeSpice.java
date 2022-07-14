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
public class RecipeSpice {
    private Integer recipeId;
    private Integer spiceId;
    private String notes;
    @Getter
    private static final String RECIPE_ID = "recipe_id";
    @Getter
    private static final String SPICE_ID = "spice_id";
    @Getter
    private static final String NOTES = "notes";

    public static RecipeSpice build(ResultSet resultSet) {
        try {
            RecipeSpice recipeSpice = new RecipeSpice();
            recipeSpice.setRecipeId(resultSet.getInt("recipe_id"));
            recipeSpice.setSpiceId(resultSet.getInt("spice_id"));
            ValueSetterUtil.setEntityValueIfNotNull(recipeSpice, RecipeSpice::setNotes, resultSet, "notes", String.class);
            return recipeSpice;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
