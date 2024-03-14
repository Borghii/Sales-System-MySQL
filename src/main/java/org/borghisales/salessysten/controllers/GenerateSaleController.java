package org.borghisales.salessysten.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.borghisales.salessysten.model.Customer;
import org.borghisales.salessysten.model.CustomerDAO;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenerateSaleController extends MenuController implements Initializable {


    private final FXMLLoader fxmlLoaderCustomer = new FXMLLoader(MenuController.class.getResource(CUSTOMER_VIEW_FXML));
    private Scene scene = null;
    private Stage stage;

    private final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    private final ButtonType buttonTypeAceptar = new ButtonType("Aceptarr");
    private final ButtonType buttonTypeCancelar = new ButtonType("Cancelar");


    CustomerDAO customerDAO = new CustomerDAO();

    public TextField serial;
    public TextField codCustomer;
    public TextField codProduct;
    public TextField price;
    public TextField customerName;
    public TextField product;
    public TextField stock;
    public TextField sell;

    public TextField total;
    public TextField date;

    public Spinner quantity;
    public TableView tableSale;
    public TableColumn colNro;
    public TableColumn colCod;
    public TableColumn colProduct;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public TableColumn colTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert.setTitle("New customer");
        alert.setHeaderText("The customer doesn´t exist");
        alert.setContentText("Do you want to add it?");
        alert.getButtonTypes().setAll(buttonTypeAceptar, buttonTypeCancelar);


    }


    public void searchCustomer(ActionEvent actionEvent) {
        Customer customer = customerDAO.searchCustomer(Integer.parseInt(codCustomer.getText()));
        if (customer!=null) {
            setAlert(Alert.AlertType.CONFIRMATION,"Customer found: "+customer.name());
            customerName.setText(customer.name());
        }else{
            alert.showAndWait().ifPresent(buttonType -> {
                if(buttonType == buttonTypeAceptar ){
                    try {
                        scene = new Scene(fxmlLoaderCustomer.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = new Stage();
                    stage.setTitle("Admin");
                    stage.setScene(scene);
                    stage.show();
                }
            });

        }
    }

    public void searchProduct(ActionEvent actionEvent) {

    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void generateSale(ActionEvent actionEvent) {
    }


}
