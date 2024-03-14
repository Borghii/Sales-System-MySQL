package org.borghisales.salessysten.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.DBConnection;
import org.borghisales.salessysten.model.SellerDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends MenuController implements Initializable {
    @FXML
    private TextField user;
    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user.setText("44994807");
        password.setText("chimu");
    }

    @FXML
    private void signIn(){
        if (SellerDAO.login(user.getText(),password.getText())) {
            openNewStage(MANAGEMENT_VIEW_FXML);
            closeCurrentStage(user);
        }
    }

}
