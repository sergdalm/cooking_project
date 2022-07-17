package com.sergdalm.cooking.dto;

import com.sergdalm.cooking.entity.Ingredient;
import com.sergdalm.cooking.entity.Recipe;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RecipeDto {
    Integer id;
    String name;
    Integer kilocalories;
    Double protein;
    Double fat;
    Double carbohydrate;

    public static RecipeDto fromEntity(Recipe recipe, List<IngredientDto> ingredients) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .kilocalories(ingredients.stream()
                        .mapToInt(IngredientDto::getKilocalories).sum())
                .protein(ingredients.stream()
                        .mapToDouble(IngredientDto::getProtein).sum())
                .fat(ingredients.stream()
                        .mapToDouble(IngredientDto::getFat).sum())
                .carbohydrate(ingredients.stream()
                        .mapToDouble(IngredientDto::getCarbohydrate).sum())
                .build();
    }

    public Ingredient toEntity() {
        return new Ingredient(id, name, null, kilocalories, protein, fat, carbohydrate);
    }
}
