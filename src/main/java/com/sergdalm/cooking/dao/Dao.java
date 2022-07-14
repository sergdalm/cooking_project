package com.sergdalm.cooking.dao;

import java.util.List;

public interface Dao<T> {
    T save(T obj);

    List<T> getAll();

    T update(T obj);
}
