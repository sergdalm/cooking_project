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
public class UserRecipe {
    private Integer userId;
    private Integer recipeId;
    private static final String USER_ID = "user_id";
    private static final String RECIPE_ID = "recipe_id";

    public static UserRecipe build(ResultSet resultSet) {
        try {
            return new UserRecipe(
                    resultSet.getInt("user_id"),
                    resultSet.getInt("recipe_id")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
