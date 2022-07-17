package com.sergdalm.cooking.dto;

import com.sergdalm.cooking.entity.Ingredient;
import com.sergdalm.cooking.entity.RecipeIngredient;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecipeIngredientDto {
    Integer recipeId;
    Integer ingredientId;
    String ingredientName;
    Integer gram;
    String notes;

    public static RecipeIngredientDto fromEntity(RecipeIngredient recipeIngredient, Ingredient ingredient) {
        return RecipeIngredientDto.builder()
                .recipeId(recipeIngredient.getRecipeId())
                .ingredientId(recipeIngredient.getIngredientId())
                .ingredientName(ingredient.getName())
                .gram(recipeIngredient.getGram())
                .notes(recipeIngredient.getNotes())
                .build();
    }

    public RecipeIngredient toEntity() {
        return new RecipeIngredient(recipeId, ingredientId, gram, notes);
    }

}
