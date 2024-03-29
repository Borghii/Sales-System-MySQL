package org.borghisales.salessysten.controllers;


import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.borghisales.salessysten.model.Seller;
import org.borghisales.salessysten.model.SellerDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerController implements Initializable {
    private final SellerDAO sellerDAO = new SellerDAO();
    private final ObservableList<Seller.State> stateList = FXCollections.observableArrayList(Seller.State.ACTIVE, Seller.State.DISACTIVE);
    private static ObservableList<Seller> sellers = null;

    @FXML
    private TextField dni;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField user;
    @FXML
    private ComboBox<Seller.State> cbState;

    @FXML
    private TableView<Seller> tableSellers;
    @FXML
    private TableColumn<Seller,Integer> colId;
    @FXML
    private TableColumn<Seller,String> colDni;
    @FXML
    private TableColumn<Seller,String> colName;
    @FXML
    private TableColumn<Seller,String> colPhone;
    @FXML
    private TableColumn<Seller, Seller.State> colState;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //Capturar el evento de click en fila
        tableSellers.setOnMouseClicked(mouseEvent -> {
            if (!tableSellers.getSelectionModel().isEmpty() && mouseEvent.getClickCount()==2){
                Seller seller = tableSellers.getSelectionModel().getSelectedItem();
                setCells(seller);
            }
        });
        cbState.setValue(Seller.State.ACTIVE);
        cbState.setItems(stateList);

        colId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idSeller()).asObject());
        colDni.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().dni()));
        colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
        colPhone.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().phoneNumber()));
        colState.setCellValueFactory(p -> new SimpleObjectProperty<Seller.State>(p.getValue().state()));

        tableSellers.getItems().clear();

        if (sellers == null){
            sellers = FXCollections.observableArrayList();
            sellerDAO.setTable(sellers);
        }
        tableSellers.setItems(sellers);

    }

    public void addSeller(ActionEvent actionEvent){
        Seller seller = new Seller(dni.getText(),name.getText(),phone.getText(), cbState.getValue(),user.getText());
        if (sellerDAO.create(seller)) {
            MenuController.cleanCells(dni, name, phone, user);
            updateTable();
        }
    }
    public void updateSeller(ActionEvent actionEvent) {
        Seller seller = new Seller(dni.getText(),name.getText(),phone.getText(),(Seller.State) cbState.getValue(),user.getText());
        if (sellerDAO.update(seller)) {
            MenuController.cleanCells(dni, name, phone, user);
            updateTable();
        }
    }

    public void deleteSeller(ActionEvent actionEvent) {
        if (sellerDAO.delete(dni.getText())){
            MenuController.cleanCells(dni,name,phone,user);
            updateTable();
        }
    }

    public void cleanCellsScreen(ActionEvent actionEvent) {
        MenuController.cleanCells(dni,name,phone,user);
    }

    private void setCells(Seller seller){
        dni.setText(seller.dni());
        name.setText(seller.name());
        phone.setText(seller.phoneNumber());
        user.setText(seller.user());
        cbState.setValue(seller.state());
    }

    private void updateTable(){
        tableSellers.getItems().clear();
        sellerDAO.setTable(sellers);
        tableSellers.setItems(sellers);
    }


}
