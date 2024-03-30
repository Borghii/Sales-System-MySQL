module org.borghisales.salessysten {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens org.borghisales.salessysten to javafx.fxml;
    exports org.borghisales.salessysten.controllers;
    opens org.borghisales.salessysten.controllers to javafx.fxml;
    exports org.borghisales.salessysten;
}