package org.borghisales.salessysten.model;

public record SalesDetails(int idSalesDetails, int idSales, int idProduct, int quantity, double priceSale) {
    public SalesDetails(int idSales, int idProduct, int quantity, double priceSale) {
        this(0, idSales, idProduct, quantity, priceSale);
    }
}
