package org.borghisales.salessysten.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManagementController extends MenuController {

    @FXML
    Button sellerButton;
    @FXML
    void openSeller(){
        openNewStage(SELLER_VIEW_FXML);
    }

}
