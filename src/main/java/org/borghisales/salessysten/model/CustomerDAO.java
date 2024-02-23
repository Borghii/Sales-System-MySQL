package org.borghisales.salessysten.model;

import javafx.collections.ObservableList;

public class CustomerDAO implements CRUD<Customer> {
    @Override
    public boolean create(Customer entity) {
        return false;
    }

    @Override
    public boolean read(int id) {
        return false;
    }

    @Override
    public boolean update(Customer entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public void setTable(ObservableList<Customer> table) {

    }
}
