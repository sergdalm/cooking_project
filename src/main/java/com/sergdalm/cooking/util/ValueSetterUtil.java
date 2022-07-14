package com.sergdalm.cooking.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ValueSetterUtil {
    private ValueSetterUtil() {
    }

    // Set the value to preparedStatement if the value is not null, otherwise set null
    public static <T, E> void setPreparedStatementValueIfNotNull(PreparedStatement preparedStatement, int parameterIndex, E entity, Function<E, T> function, int sqlType) throws SQLException {
        if (function.apply(entity) == null) {
            preparedStatement.setNull(parameterIndex, sqlType);
        } else {
            preparedStatement.setObject(parameterIndex, function.apply(entity));
        }
    }

    // Set the value to the com.sergdalm.cooking.entity from resultSet if the value is presented
    public static <T, E> void setEntityValueIfNotNull(E entity, BiConsumer<E, T> consumer, ResultSet resultSet, String columnLabel, Class<T> targetClass) throws SQLException {
        if (resultSet.getObject(columnLabel) != null) {
            consumer.accept(entity, resultSet.getObject(columnLabel, targetClass));
        }
    }
}
