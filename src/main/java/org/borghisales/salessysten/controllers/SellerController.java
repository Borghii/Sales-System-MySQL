package org.borghisales.salessysten.controllers;


import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import javafx.scene.control.cell.PropertyValueFactory;
import org.borghisales.salessysten.model.Seller;
import org.borghisales.salessysten.model.SellerDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerController implements Initializable {
    SellerDAO sellerDAO = new SellerDAO();
    ObservableList<Seller.State> stateList = FXCollections.observableArrayList(Seller.State.ACTIVE, Seller.State.DISACTIVE);
    private ObservableList<Seller> sellers;

    @FXML
    TextField dni;
    @FXML
    TextField name;
    @FXML
    TextField phone;
    @FXML
    TextField user;
    @FXML
    ComboBox cbState;

    @FXML
    TableView<Seller> tableSellers;
    @FXML
    TableColumn<Seller,Integer> colId;
    @FXML
    TableColumn<Seller,String> colDni;
    @FXML
    TableColumn<Seller,String> colName;
    @FXML
    TableColumn<Seller,String> colPhone;
    @FXML
    TableColumn<Seller, Seller.State> colState;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbState.setValue(Seller.State.ACTIVE);
        cbState.setItems(stateList);

        sellers = FXCollections.observableArrayList();

        colId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idSeller()).asObject());
        colDni.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().dni()));
        colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
        colPhone.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().phoneNumber()));
        colState.setCellValueFactory(p -> new SimpleObjectProperty<Seller.State>(p.getValue().state()));

        sellerDAO.setTable(sellers);

        tableSellers.setItems(sellers);

    }

    public void addSeller(ActionEvent actionEvent){
        Seller seller = new Seller(dni.getText(),name.getText(),phone.getText(),(Seller.State) cbState.getValue(),user.getText());
        if (sellerDAO.create(seller))
            MenuController.cleanCells(dni,name,phone,user);

    }
    public void updateSeller(ActionEvent actionEvent) {
        Seller seller = new Seller(dni.getText(),name.getText(),phone.getText(),(Seller.State) cbState.getValue(),user.getText());
        if (sellerDAO.update(seller))
            MenuController.cleanCells(dni,name,phone,user);
    }

    public void deleteSeller(ActionEvent actionEvent) {
        if (sellerDAO.delete(dni.getText()))
            MenuController.cleanCells(dni,name,phone,user);
    }

    public void cleanCellsScreen(ActionEvent actionEvent) {
        MenuController.cleanCells(dni,name,phone,user);
    }
}
