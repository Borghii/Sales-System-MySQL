package org.borghisales.salessysten.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;

public class MenuController {

    public static final String MAIN_VIEW_FXML = "/org/borghisales/salessysten/MainView.fxml";
    public static final String MANAGEMENT_VIEW_FXML = "/org/borghisales/salessysten/ManagementView.fxml";

    static Alert alert;
    static ButtonType botonAceptar = new ButtonType("Aceptar");
    public static HashMap<String, String > map = new HashMap<>();

    void closeCurrentStage(TextField textField) {
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }
    void closeCurrentStage(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }


    public void openNewStage(String fxmlFileName) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Admin");
            stage.setScene(scene);
            stage.show();

            if (!fxmlFileName.equals(MAIN_VIEW_FXML))
                stage.setOnCloseRequest(e->{

                    openNewStage(getFxmlFather(fxmlFileName));
                });

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir al cargar el nuevo FXML
        }
    }

    String getFxmlFather(String fxml){
        return map.get(fxml);
    }

    static public void setAlert(Alert.AlertType alertType,String argument){
        alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.getButtonTypes().setAll(botonAceptar);
        alert.setContentText(argument);
        alert.showAndWait();
    }

    void cleanCells(TextField textField1, TextField textField2){
        textField1.clear();
        textField2.clear();
    }
    void cleanCells(TextField textField1, TextField textField2, TextField textField3, TextField textField4){
        textField1.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
    }

}