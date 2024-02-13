package org.borghisales.salessysten.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.DBConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends MenuController implements Initializable {
    @FXML
    TextField user;
    @FXML
    TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user.setText("44994806");
        password.setText("chimu");
    }

    @FXML
    private void signIn(){
        if (DBConnection.login(user.getText(),password.getText())) {
            closeCurrentStage(user);
            openNewStage(MANAGEMENT_VIEW_FXML);
        }
    }

}
