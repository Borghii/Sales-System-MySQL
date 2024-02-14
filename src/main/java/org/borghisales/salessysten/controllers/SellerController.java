package org.borghisales.salessysten.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.borghisales.salessysten.model.Seller;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerController implements Initializable {

    ObservableList<Seller.State> stateList = FXCollections.observableArrayList(Seller.State.ACTIVE, Seller.State.DISACTIVE);

    @FXML
    ComboBox cbState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbState.setValue(Seller.State.ACTIVE);
        cbState.setItems(stateList);
    }


    public void updateSeller(ActionEvent actionEvent) {
    }

    public void deleteSeller(ActionEvent actionEvent) {
    }

    public void cleanCells(ActionEvent actionEvent) {
    }
}
