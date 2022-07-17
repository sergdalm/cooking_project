package com.sergdalm.cooking.service;

import com.sergdalm.cooking.dao.IngredientDao;
import com.sergdalm.cooking.dao.RecipeIngredientDao;
import com.sergdalm.cooking.dto.IngredientDto;
import com.sergdalm.cooking.dto.RecipeIngredientDto;

import java.util.List;

public class IngredientService {
    private static final IngredientService INSTANCE = new IngredientService();
    private static final IngredientDao ingredientDao = IngredientDao.getInstance();
    private static final RecipeIngredientDao recipeIngredientDao = RecipeIngredientDao.getInstance();

    private IngredientService() {
    }

    public List<IngredientDto> findAll() {
        return ingredientDao.getAll().stream()
                .map(IngredientDto::fromEntity)
                .toList();
    }

    public List<RecipeIngredientDto> findRecipeIngredientsByRecipe(Integer recipeId) {
        return recipeIngredientDao.get(recipeId).stream()
                .map(recipeIngredient ->
                        RecipeIngredientDto.fromEntity(recipeIngredient,
                                ingredientDao.get(recipeIngredient.getIngredientId())))
                .toList();
    }

    public List<IngredientDto> findIngredientsByRecipe(Integer recipeId) {
        return recipeIngredientDao.get(recipeId).stream()
                .map(recipeIngredient -> ingredientDao.get(recipeIngredient.getIngredientId()))
                .map(IngredientDto::fromEntity)
                .toList();
    }

    public static IngredientService getInstance() {
        return INSTANCE;
    }
}
