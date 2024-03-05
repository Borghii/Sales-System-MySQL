package org.borghisales.salessysten.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.borghisales.salessysten.model.Product;
import org.borghisales.salessysten.model.ProductDAO;
import org.borghisales.salessysten.model.Seller;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    private final ProductDAO productDAO = new ProductDAO();

    private final ObservableList<Product.State> stateList = FXCollections.observableArrayList(Product.State.ACTIVE, Product.State.DISACTIVE);

    private ObservableList<Product> products;
    @FXML
    private ComboBox<Product.State> cbState;

    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField stock;

    @FXML
    private TableView<Product> tableProducts;
    @FXML
    private TableColumn<Product,Integer> colId;
    @FXML
    private TableColumn<Product,String> colName;
    @FXML
    private TableColumn<Product,Double> colPrice;
    @FXML
    private TableColumn<Product,Integer> colStock;
    @FXML
    private TableColumn<Product,Product.State> colState;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Capturar el evento de click en fila
        tableProducts.setOnMouseClicked(mouseEvent -> {
            if (!tableProducts.getSelectionModel().isEmpty() && mouseEvent.getClickCount()==2){
                Product product = tableProducts.getSelectionModel().getSelectedItem();
                setCells(product);
            }
        });

        Platform.runLater(() -> {
            cbState.setValue(Product.State.ACTIVE);
            cbState.setItems(stateList);

            products = FXCollections.observableArrayList();

            colId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idProduct()).asObject());
            colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
            colPrice.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().price()).asObject());
            colStock.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().stock()).asObject());
            colState.setCellValueFactory(p -> new SimpleObjectProperty<Product.State>(p.getValue().state()));

            tableProducts.getItems().clear();
            productDAO.setTable(products);
            tableProducts.setItems(products);
        });
    }

    public void addProduct(ActionEvent actionEvent) {
        Product product = new Product(name.getText(),Double.parseDouble(price.getText()),
                          Integer.parseInt(stock.getText()), cbState.getValue());
        if (productDAO.create(product)) {
            MenuController.cleanCells(name,price,stock);
            updateTable();
        }
    }

    public void updateProduct(ActionEvent actionEvent) {
        Product product = new Product(name.getText(),Double.parseDouble(price.getText()),
                Integer.parseInt(stock.getText()), cbState.getValue());
        if (productDAO.update(product)) {
            MenuController.cleanCells(name,price,stock);
            updateTable();
        }
    }

    public void deleteProduct(ActionEvent actionEvent) {
        if (productDAO.delete(name.getText())) {
            MenuController.cleanCells(name,price,stock);
            updateTable();
        }
    }

    public void cleanCellsScreen(ActionEvent actionEvent) {
        MenuController.cleanCells(name,price,stock);
    }

    private void setCells(Product product){
        name.setText(product.name());
        price.setText(String.valueOf(product.price()));
        stock.setText(String.valueOf(product.stock()));
        cbState.setValue(product.state());
    }

    private void updateTable() {
        tableProducts.getItems().clear();
        productDAO.setTable(products);
        tableProducts.setItems(products);
    }


}
