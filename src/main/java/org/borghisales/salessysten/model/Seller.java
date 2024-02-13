package org.borghisales.salessysten.model;

public record Seller(int idSeller,
                     String dni,
                     String name,
                     String phoneNumber,
                     State state,
                     String user) {

    public enum State {
        ACTIVE,
        DISACTIVE
    }
}

