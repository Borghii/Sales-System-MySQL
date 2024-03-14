package org.borghisales.salessysten.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.borghisales.salessysten.model.Customer;
import org.borghisales.salessysten.model.CustomerDAO;
import org.borghisales.salessysten.model.Product;
import org.borghisales.salessysten.model.ProductDAO;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenerateSaleController extends MenuController implements Initializable {


    private final FXMLLoader fxmlLoaderCustomer = new FXMLLoader(MenuController.class.getResource(CUSTOMER_VIEW_FXML));
    private final FXMLLoader fxmlLoaderProduct = new FXMLLoader(MenuController.class.getResource(PRODUCT_VIEW_FXML));

    private Scene scene = null;
    private Stage stage;

    private final Alert alertCustomer = new Alert(Alert.AlertType.WARNING);
    private final Alert alertProduct = new Alert(Alert.AlertType.WARNING);
    private final ButtonType buttonTypeAccept = new ButtonType("YES");
    private final ButtonType buttonTypeCancel = new ButtonType("NO");


    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public TextField serial;
    public TextField codCustomer;
    public TextField codProduct;
    public TextField price;
    public TextField customerName;
    public TextField productName;
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
        alertCustomer.setTitle("New customer");
        alertCustomer.setHeaderText("The customer doesn´t exist");
        alertCustomer.setContentText("Do you want to add it?");
        alertCustomer.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);

        alertProduct.setTitle("New Product");
        alertProduct.setHeaderText("The product doesn´t exist");
        alertProduct.setContentText("Do you want to add it?");
        alertProduct.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);


    }


    public void searchCustomer(ActionEvent actionEvent) {
        Customer customer = customerDAO.searchCustomer(Integer.parseInt(codCustomer.getText()));
        if (customer!=null) {
            setAlert(Alert.AlertType.CONFIRMATION,"Customer found: "+customer.name());
            customerName.setText(customer.name());
        }else{
            alertCustomer.showAndWait().ifPresent(buttonType -> {
                if(buttonType == buttonTypeAccept){
                    try {
                        scene = new Scene(fxmlLoaderCustomer.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = new Stage();
                    stage.setTitle("Manage Customer");
                    stage.setScene(scene);
                    stage.show();
                }
            });

        }
    }

    public void searchProduct(ActionEvent actionEvent) {
        Product product = productDAO.searchProduct(Integer.parseInt(codProduct.getText()));
        if (product != null) {
            setAlert(Alert.AlertType.CONFIRMATION,"Product found: "+product.name());
            productName.setText(product.name());
            stock.setText(String.valueOf(product.stock()));
            price.setText(String.valueOf(product.price()));
        }else{

            alertProduct.showAndWait().ifPresent(buttonType -> {
                if(buttonType == buttonTypeAccept){
                    try {
                        scene = new Scene(fxmlLoaderProduct.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage = new Stage();
                    stage.setTitle("Manage Product");
                    stage.setScene(scene);
                    stage.show();
                }
            });

        }
    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void generateSale(ActionEvent actionEvent) {
    }


}
