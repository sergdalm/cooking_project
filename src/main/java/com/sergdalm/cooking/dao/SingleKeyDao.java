package com.sergdalm.cooking.dao;

public interface SingleKeyDao<T> {
    T get(int id);

    boolean delete(int id);
}
