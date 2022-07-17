package com.sergdalm.cooking.dto;

import com.sergdalm.cooking.entity.Recipe;
import com.sergdalm.cooking.entity.RecipeDetail;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecipeDetailDto {
    Integer recipeId;
    String name;
    Integer userId;
    String description;
    Integer activeCookingTimeMinute;
    Integer totalCookingTimeMinute;
    Integer numberOfPortions;

    public static RecipeDetailDto fromEntity(RecipeDetail recipeDetail, Recipe recipe) {
        return RecipeDetailDto.builder()
                .recipeId(recipeDetail.getRecipeId())
                .name(recipe.getName())
                .userId(recipeDetail.getUserId())
                .description(recipeDetail.getDescription())
                .activeCookingTimeMinute(recipeDetail.getActiveCookingTimeMinute())
                .totalCookingTimeMinute(recipeDetail.getTotalCookingTimeMinute())
                .numberOfPortions(recipeDetail.getNumberOfPortions())
                .build();
    }

    public RecipeDetail toEntity() {
        return new RecipeDetail(recipeId, userId, description, activeCookingTimeMinute, totalCookingTimeMinute, numberOfPortions);
    }
}
