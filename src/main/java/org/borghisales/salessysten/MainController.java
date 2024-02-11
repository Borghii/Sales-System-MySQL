package org.borghisales.salessysten;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.DBConnection;

public class MainController extends MenuController {
    @FXML
    TextField user;
    @FXML
    TextField password;

    @FXML
    private void signIn(){
        if (DBConnection.login(user.getText(),password.getText())) {
            closeCurrentStage(user);
            openNewStage("ExampleView.fxml");
        }
    }

}
