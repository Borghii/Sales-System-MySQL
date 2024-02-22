package org.borghisales.salessysten;

import javafx.application.Application;
import javafx.stage.Stage;
import org.borghisales.salessysten.controllers.MenuController;

import java.io.IOException;

import static org.borghisales.salessysten.controllers.MenuController.*;

public class Main extends Application {
    MenuController mc = new MenuController();
    @Override
    public void init() throws Exception {
        super.init();
        map.put(MANAGEMENT_VIEW_FXML, MAIN_VIEW_FXML);
        map.put(SELLER_VIEW_FXML, MANAGEMENT_VIEW_FXML);
    }

    @Override
    public void start(Stage stage) throws IOException {
        mc.openNewStage(MAIN_VIEW_FXML);

    }

    public static void main(String[] args) {
        launch();
    }
}