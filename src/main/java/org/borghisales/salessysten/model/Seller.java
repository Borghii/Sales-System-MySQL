package org.borghisales.salessysten.model;

public record Seller(int idSeller, String dni, String name, String phoneNumber, State state, String user) {
    public enum State {ACTIVE(), DISACTIVE()}
    public Seller(String dni, String name, String phoneNumber, State state, String user) {
        this(0, dni, name, phoneNumber, state, user);
    }

    public Seller(int idSeller, String dni, String name, String phoneNumber, State state) {
        this(idSeller, dni, name, phoneNumber, state, null);
    }
}

