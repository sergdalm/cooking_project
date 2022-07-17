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
public class MealPlan {
    private Integer id;
    private Integer userId;
    private String name;
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String NAME = "name";


    public static MealPlan build(ResultSet resultSet) {
        try {
            return new MealPlan(
                    resultSet.getInt(ID),
                    resultSet.getInt(USER_ID),
                    resultSet.getString(NAME)
            );
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
