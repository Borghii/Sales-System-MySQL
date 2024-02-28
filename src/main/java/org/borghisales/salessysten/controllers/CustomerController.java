package org.borghisales.salessysten.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.Customer;
import org.borghisales.salessysten.model.Seller;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    ObservableList<Customer.State> stateList = FXCollections.observableArrayList(Customer.State.ACTIVE, Customer.State.DISACTIVE);

    private ObservableList<Customer> customers;

    @FXML
    public TextField dni;
    @FXML
    public TextField name;
    @FXML
    public TextField address;
    @FXML
    public ComboBox<Customer.State> cbState;
    @FXML
    public TableView<Customer> tableCustomers;
    @FXML
    public TableColumn<Customer,Integer> colId;
    @FXML
    public TableColumn<Customer,String> colDni;
    @FXML
    public TableColumn<Customer,String> colName;
    @FXML
    public TableColumn<Customer,String> colAdress;
    @FXML
    public TableColumn<Customer,Customer.State> colState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableCustomers.setOnMouseClicked(mouseEvent->{

        });
    }
    @FXML
    public void addCustomer(ActionEvent actionEvent) {
    }
    @FXML
    public void updateCustomer(ActionEvent actionEvent) {
    }
    @FXML
    public void deleteCustomer(ActionEvent actionEvent) {
    }
    @FXML
    public void cleanCellsScreen(ActionEvent actionEvent) {
    }


}
