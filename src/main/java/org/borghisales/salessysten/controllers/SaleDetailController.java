package org.borghisales.salessysten.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.SalesDAO;
import org.borghisales.salessysten.model.ShoppingCart;

import java.net.URL;
import java.util.ResourceBundle;

public class SaleDetailController implements Initializable {



    private static final SalesDAO salesDAO = new SalesDAO();
    private static ObservableList<ShoppingCart> productsDetails;
    private static int idSale;

    @FXML
    public  TableView<ShoppingCart> tableSale;
    @FXML
    public TableColumn<ShoppingCart,Integer> colNro;
    @FXML
    public TableColumn<ShoppingCart,String> colCod;
    @FXML
    public TableColumn<ShoppingCart,String> colProduct;
    @FXML
    public TableColumn<ShoppingCart, Integer> colQuantity;
    @FXML
    public TableColumn<ShoppingCart, Double> colPrice;
    @FXML
    public TableColumn<ShoppingCart,Double> colTotal;
    public TextField totalSale;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colNro.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().nr()).asObject());
        colCod.setCellValueFactory(p->new SimpleStringProperty(p.getValue().cod()));
        colProduct.setCellValueFactory(p->new SimpleStringProperty(p.getValue().product()));
        colQuantity.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().quantity()).asObject());
        colPrice.setCellValueFactory(p->new SimpleDoubleProperty(p.getValue().price()).asObject());
        colTotal.setCellValueFactory(p->new SimpleDoubleProperty(p.getValue().total()).asObject());

        tableSale.getItems().clear();

        productsDetails = FXCollections.observableArrayList();

        salesDAO.setTableDetails(productsDetails,idSale);

        double sumTotal = productsDetails.stream()
                .mapToDouble(ShoppingCart::total)
                .sum();

        totalSale.setText(String.format("%.2f", sumTotal));


        tableSale.setItems(productsDetails);

    }

    public static void setIdSale(int idSale) {
        SaleDetailController.idSale = idSale;
    }


}
