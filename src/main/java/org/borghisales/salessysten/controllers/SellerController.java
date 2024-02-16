package org.borghisales.salessysten.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.Seller;
import org.borghisales.salessysten.model.SellerDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerController implements Initializable {
    SellerDAO sellerDAO = new SellerDAO();
    ObservableList<Seller.State> stateList = FXCollections.observableArrayList(Seller.State.ACTIVE, Seller.State.DISACTIVE);


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbState.setValue(Seller.State.ACTIVE);
        cbState.setItems(stateList);
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
    }

    public void cleanCellsScreen(ActionEvent actionEvent) {
    }
}
