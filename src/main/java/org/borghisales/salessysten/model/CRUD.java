package org.borghisales.salessysten.model;

import java.util.List;

public interface CRUD<T> {
    T create(T entity);
    T read(int id);
    T update(T entity);
    boolean delete(int id);
    List<T> getAll();
}

