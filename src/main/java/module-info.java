module org.borghisales.salessysten {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.borghisales.salessysten to javafx.fxml;
    exports org.borghisales.salessysten.controllers;
    opens org.borghisales.salessysten.controllers to javafx.fxml;
    exports org.borghisales.salessysten;
}