package org.borghisales.salessysten.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Product(int idProduct, String name, double price, int stock, State state) {
    public enum State {ACTIVE(), DISACTIVE()}

    public Product(String name, double price, int stock, State state){
        this(0,name,price,stock,state);
    }

    public static Product fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idProduct");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int stock = rs.getInt("stock");
        State state = Product.State.valueOf(rs.getString("state"));
        return new Product(id, name, price, stock, state);
    }

}
