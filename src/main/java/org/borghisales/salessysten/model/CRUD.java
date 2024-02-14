package org.borghisales.salessysten.model;

import java.util.List;

public interface CRUD<T> {
    boolean create(T entity);
    boolean read(int id);
    boolean update(T entity);
    boolean delete(int id);
    List<T> getAll();
}

