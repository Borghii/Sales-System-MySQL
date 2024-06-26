package org.borghisales.salessysten.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.Seller;
import org.borghisales.salessysten.model.SellerDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends MenuController implements Initializable {
    @FXML
    private  TextField user;
    @FXML
    private TextField password;

    public static Seller sellerLog;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        user.setText("44994806");
//        password.setText("chimu");

        //In case the user is changed, the reports of that user should be updated.
        ReportsController.setSales(null);
        ReportsController.setPieChartData(null);
        ReportsController.setLineChartData(null);

    }

    @FXML
    private void signIn(){
        if (SellerDAO.login(user.getText(),password.getText())) {
            openNewStage(MANAGEMENT_VIEW_FXML, "Management");
            closeCurrentStage(user);
        }
    }

}
