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
public class Spice {
    private Integer id;
    private String name;
    @Getter
    private static final String ID = "id";
    @Getter
    private static final String NAME = "name";

    public static Spice build(ResultSet resultSet) {
        try {
            return new Spice(resultSet.getInt(ID),
                    resultSet.getString(NAME));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
