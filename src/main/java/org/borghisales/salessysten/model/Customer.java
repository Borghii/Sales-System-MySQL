package org.borghisales.salessysten.model;

public record Customer(int idCustomer, String dni, String name, String address, State state) {
    public enum State {ACTIVE(), DISACTIVE()}

    public Customer(String dni, String name, String address, State state){
        this(0,dni,name,address,state);
    }
}
