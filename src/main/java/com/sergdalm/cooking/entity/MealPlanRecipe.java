package com.sergdalm.cooking.entity;

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
public class MealPlanRecipe {
    private Integer id;
    private Integer mealPlanId;
    private Integer recipeId;
    private Integer dayNumber;
    private MealType mealType;
    private static final String ID = "id";
    private static final String MEAL_PLAN_ID = "meal_plan_id";
    private static final String RECIPE_ID = "recipe_id";
    private static final String DAY_NUMBER = "day_number";
    private static final String MEAL_TYPE = "meal_type";

    public static MealPlanRecipe build(ResultSet resultSet) {
        try {
            return new MealPlanRecipe(
                    resultSet.getInt(ID),
                    resultSet.getInt(MEAL_PLAN_ID),
                    resultSet.getInt(RECIPE_ID),
                    resultSet.getInt(DAY_NUMBER),
                    MealType.valueOf(resultSet.getString(MEAL_TYPE)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getID() {
        return ID;
    }

    public int getId() {
        return id;
    }
}
