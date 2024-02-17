package org.borghisales.salessysten.model;

import java.util.List;

public class ProductDAO implements CRUD<Product>{


    @Override
    public boolean create(Product entity) {
        return false;
    }

    @Override
    public boolean read(int id) {
        return false;
    }

    @Override
    public boolean update(Product entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }


    @Override
    public List<Product> getAll() {
        return null;
    }
}
