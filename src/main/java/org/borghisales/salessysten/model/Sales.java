package org.borghisales.salessysten.model;

import java.time.LocalDate;

public record Sales(int idSales, int idCustomer, int idSeller, String numberSales, LocalDate saleDate, Double amount,State state) {
    public enum State{ACTIVE,DISACTIVE};
}
