package org.borghisales.salessysten.model;

public record ShoppingCart(int nr, String cod,String product,int quantity,double price, double total){
    public ShoppingCart(int nr, String cod, String product, int quantity, double price) {
        this(nr, cod, product, quantity, price, Double.parseDouble(String.format("%.2f",quantity*price)));
    }
}
