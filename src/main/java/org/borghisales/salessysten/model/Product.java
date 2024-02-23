package org.borghisales.salessysten.model;

public record Product(int idProduct, String name, double price, int stock, State state) {
    public enum State {ACTIVE(), DISACTIVE()}
}
