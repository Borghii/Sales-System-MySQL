package org.borghisales.salessysten.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.Customer;
import org.borghisales.salessysten.model.CustomerDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateSaleController implements Initializable {


    CustomerDAO customerDAO = new CustomerDAO();

    public TextField serial;
    public TextField codCustomer;
    public TextField codProduct;
    public TextField price;
    public TextField customerName;
    public TextField product;
    public TextField stock;
    public TextField sell;

    public TextField total;
    public TextField date;

    public Spinner quantity;
    public TableView tableSale;
    public TableColumn colNro;
    public TableColumn colCod;
    public TableColumn colProduct;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public TableColumn colTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void searchCustomer(ActionEvent actionEvent) {
        Customer customer = customerDAO.searchCustomer(Integer.parseInt(codCustomer.getText()));
        if (customer!=null)
            customerName.setText(customer.name());
    }

    public void searchProduct(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void generateSale(ActionEvent actionEvent) {
    }


}
