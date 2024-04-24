package org.borghisales.salessysten.model;

import javafx.collections.ObservableList;

import java.util.List;

public interface CRUD<T> {
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(String id);
    void setTable(ObservableList<T> table);
}

