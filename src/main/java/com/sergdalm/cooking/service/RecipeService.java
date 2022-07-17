package com.sergdalm.cooking.service;

import com.sergdalm.cooking.dao.RecipeDao;
import com.sergdalm.cooking.dao.RecipeDetailDao;
import com.sergdalm.cooking.dto.RecipeDetailDto;
import com.sergdalm.cooking.dto.RecipeDto;

import java.util.List;

public class RecipeService {
    private static final RecipeService INSTANCE = new RecipeService();
    private static final RecipeDao recipeDao = RecipeDao.getInstance();
    private static final RecipeDetailDao recipeDetailDao = RecipeDetailDao.getInstance();
    private static final IngredientService ingredientService = IngredientService.getInstance();

    private RecipeService() {
    }

    public List<RecipeDto> findAll() {
        return recipeDao.getAll().stream()
                .map(recipe -> RecipeDto.fromEntity(recipe,
                        ingredientService.findIngredientsByRecipe(recipe.getId())))
                .toList();
    }

    public RecipeDetailDto findRecipeDetail(Integer recipeId) {
        return RecipeDetailDto.fromEntity(recipeDetailDao.get(recipeId),
                recipeDao.get(recipeId));
    }

    public static RecipeService getInstance() {
        return INSTANCE;
    }
}
