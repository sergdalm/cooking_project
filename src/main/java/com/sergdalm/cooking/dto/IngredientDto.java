package com.sergdalm.cooking.dto;

import com.sergdalm.cooking.entity.Ingredient;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IngredientDto {
    Integer id;
    String name;
    Integer weightOfOePieceGram;
    Integer kilocalories;
    Double protein;
    Double fat;
    Double carbohydrate;

    public Ingredient toEntity() {
        return new Ingredient(id, name, weightOfOePieceGram, kilocalories, protein, fat, carbohydrate);
    }

    public static IngredientDto fromEntity(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .weightOfOePieceGram(ingredient.getWeightOfOnePieceGram())
                .kilocalories(ingredient.getKilocalories())
                .protein(ingredient.getProtein())
                .fat(ingredient.getFat())
                .carbohydrate(ingredient.getCarbohydrate())
                .build();
    }
}
