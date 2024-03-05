package org.borghisales.salessysten.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.borghisales.salessysten.model.CustomerDAO;


import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private final CustomerDAO customerDAO = new CustomerDAO();

    private final ObservableList<Customer.State> stateList = FXCollections.observableArrayList(Customer.State.ACTIVE, Customer.State.DISACTIVE);

    private ObservableList<Customer> customers;

    @FXML
    private TextField dni;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private ComboBox<Customer.State> cbState;
    @FXML
    private TableView<Customer> tableCustomers;
    @FXML
    private TableColumn<Customer,Integer> colId;
    @FXML
    private TableColumn<Customer,String> colDni;
    @FXML
    private TableColumn<Customer,String> colName;
    @FXML
    private TableColumn<Customer,String> colAdress;
    @FXML
    private TableColumn<Customer,Customer.State> colState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableCustomers.setOnMouseClicked(mouseEvent->{
            if (!tableCustomers.getSelectionModel().isEmpty() && mouseEvent.getClickCount()==2){
                Customer customer = tableCustomers.getSelectionModel().getSelectedItem();
                setCells(customer);
            }
        });

        Platform.runLater(() -> {
            cbState.setValue(Customer.State.ACTIVE);
            cbState.setItems(stateList);

            customers = FXCollections.observableArrayList();

            colId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idCustomer()).asObject());
            colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
            colDni.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().dni()));
            colAdress.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().address()));
            colState.setCellValueFactory(p -> new SimpleObjectProperty<Customer.State>(p.getValue().state()));

            tableCustomers.getItems().clear();
            customerDAO.setTable(customers);
            tableCustomers.setItems(customers);
        });

    }
    @FXML
    public void addCustomer(ActionEvent actionEvent) {
        Customer customer = new Customer(dni.getText(),name.getText(),address.getText(),cbState.getValue());
        if (customerDAO.create(customer)) {
            MenuController.cleanCells(dni, name, address);
            updateTable();
        }
    }
    @FXML
    public void updateCustomer(ActionEvent actionEvent) {
        Customer customer = new Customer(dni.getText(),name.getText(),address.getText(),cbState.getValue());
        if (customerDAO.update(customer)) {
            MenuController.cleanCells(dni, name, address);
            updateTable();
        }
    }
    @FXML
    public void deleteCustomer(ActionEvent actionEvent) {
        if (customerDAO.delete(dni.getText())){
            MenuController.cleanCells(dni,name,address);
            updateTable();
        }
    }
    @FXML
    public void cleanCellsScreen(ActionEvent actionEvent) {
        MenuController.cleanCells(dni,name,address);
    }
    private void setCells(Customer customer){
        name.setText(customer.name());
        dni.setText(customer.dni());
        address.setText(customer.address());
        cbState.setValue(customer.state());
    }

    private void updateTable() {
        tableCustomers.getItems().clear();
        customerDAO.setTable(customers);
        tableCustomers.setItems(customers);
    }


}
