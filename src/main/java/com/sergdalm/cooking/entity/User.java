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
public class User {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private Boolean isAdmin;
    @Getter
    private static final String ID = "id";
    @Getter
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String IS_ADMIN = "is_admin";

    public static User build(ResultSet resultSet) {
        try {
            return new User(resultSet.getInt(ID),
                    resultSet.getString(EMAIL),
                    resultSet.getString(USERNAME),
                    resultSet.getString(PASSWORD),
                    resultSet.getBoolean(IS_ADMIN)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
