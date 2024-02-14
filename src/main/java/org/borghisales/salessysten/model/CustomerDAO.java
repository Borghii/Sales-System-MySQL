package org.borghisales.salessysten.model;

import java.util.List;

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
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
