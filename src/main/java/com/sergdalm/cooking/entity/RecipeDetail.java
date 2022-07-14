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
public class RecipeDetail {
    private Integer recipeId;
    private Integer userId;
    private String description;
    private Integer activeCookingTimeMinute;
    private Integer totalCookingTimeMinute;
    private Integer numberOfPortions;
    @Getter
    private static final String RECIPE_ID = "recipe_id";
    @Getter
    private static final String USER_ID = "user_id";
    @Getter
    private static final String DESCRIPTION = "description";
    @Getter
    private static final String ACTIVE_COOKING_TIME_MINUTE = "active_cooking_time_minute";
    @Getter
    private static final String TOTAL_COOKING_TIME_MINUTE = "total_cooking_time_minute";
    @Getter
    private static final String NUMBER_OF_PORTION = "number_of_portion";

    public static RecipeDetail build(ResultSet resultSet) {
        try {
            RecipeDetail recipeDetail = new RecipeDetail();
            recipeDetail.setRecipeId(resultSet.getInt(RECIPE_ID));
            recipeDetail.setUserId(resultSet.getInt(USER_ID));
            recipeDetail.setDescription(resultSet.getString(DESCRIPTION));
            ValueSetterUtil.setEntityValueIfNotNull(recipeDetail, RecipeDetail::setActiveCookingTimeMinute, resultSet, ACTIVE_COOKING_TIME_MINUTE, Integer.class);
            ValueSetterUtil.setEntityValueIfNotNull(recipeDetail, RecipeDetail::setTotalCookingTimeMinute, resultSet, TOTAL_COOKING_TIME_MINUTE, Integer.class);
            ValueSetterUtil.setEntityValueIfNotNull(recipeDetail, RecipeDetail::setNumberOfPortions, resultSet, NUMBER_OF_PORTION, Integer.class);
            return recipeDetail;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
