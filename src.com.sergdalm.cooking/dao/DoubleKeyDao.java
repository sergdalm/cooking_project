package dao;

import java.util.List;

public interface DoubleKeyDao<T> {
    T save(T obj);

    T get(int id, int id2);

    List<T> getAll();

    List<T> get(int id);

    T update(T obj);

    boolean delete(int id1, int id2);
}
