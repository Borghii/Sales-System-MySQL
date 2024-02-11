package org.borghisales.salessysten;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    MenuController mc = new MenuController();
    @Override
    public void init() throws Exception {
        super.init();
        MenuController.map.put("ExampleView.fxml","MainView.fxml");
    }

    @Override
    public void start(Stage stage) throws IOException {
        mc.openNewStage("MainView.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}