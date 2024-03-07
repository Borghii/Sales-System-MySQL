package org.borghisales.salessysten.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Seller(int idSeller, String dni, String name, String phoneNumber, State state, String user) {
    public enum State {ACTIVE(), DISACTIVE()}
    public Seller(String dni, String name, String phoneNumber, State state, String user) {
        this(0, dni, name, phoneNumber, state, user);
    }

    public static Seller fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idSeller");
        String dni = rs.getString("dni");
        String name = rs.getString("name");
        String phoneNumber = rs.getString("phone_number");
        State state = Seller.State.valueOf(rs.getString("state"));
        String user = rs.getString("user");
        return new Seller(id, dni, name, phoneNumber, state, user);
    }
}

