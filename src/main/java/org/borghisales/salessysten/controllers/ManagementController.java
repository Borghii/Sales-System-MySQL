package org.borghisales.salessysten.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController extends MenuController implements Initializable {

    private static int lastTab ;

    @FXML
    Button sellerButton;

    @FXML
    TabPane tabPaneManage;

    @FXML
    void openSeller(){
        lastTab = tabPaneManage.getSelectionModel().getSelectedIndex();
        closeCurrentStage(sellerButton);
        openNewStage(SELLER_VIEW_FXML);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPaneManage.getSelectionModel().select(lastTab);
    }

    public void openCustomer(ActionEvent actionEvent) {
        lastTab = tabPaneManage.getSelectionModel().getSelectedIndex();
        closeCurrentStage(sellerButton);
        openNewStage(CUSTOMER_VIEW_FXML);

    }

    public void openProduct(ActionEvent actionEvent) {
        lastTab = tabPaneManage.getSelectionModel().getSelectedIndex();
        closeCurrentStage(sellerButton);
        openNewStage(PRODUCT_VIEW_FXML);

    }
}
