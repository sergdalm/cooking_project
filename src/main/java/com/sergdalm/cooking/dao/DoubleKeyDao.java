package com.sergdalm.cooking.dao;

import java.util.List;

public interface DoubleKeyDao<T> {
    T get(int id, int id2);

    List<T> get(int id);

    boolean delete(int id1, int id2);
}
