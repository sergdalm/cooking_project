package dao;

import java.util.List;

public interface Dao<T> {
    T save(T obj);

    T get(int id);

    List<T> getAll();

    T update(T obj);

    boolean delete(int id);
}
