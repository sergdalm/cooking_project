package com.sergdalm.cooking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.net.IDN;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetail {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BIRTHDAY = "birthday";

    public static UserDetail build(ResultSet resultSet) {
        try {
            return new UserDetail(
                    resultSet.getInt(USER_ID),
                    resultSet.getString(FIRST_NAME),
                    resultSet.getString(LAST_NAME),
                    resultSet.getTimestamp(BIRTHDAY).toLocalDateTime().toLocalDate()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
