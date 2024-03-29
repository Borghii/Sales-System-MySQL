package org.borghisales.salessysten.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.borghisales.salessysten.model.*;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class GenerateSaleController extends MenuController implements Initializable {

    private final SalesDAO salesDAO = new SalesDAO();
    private int idSale;

    private static int contProducts =1;
    private static ObservableList<ShoppingCart> products;

    private static String sellerName;
    private static int idSeller;

    private final LocalDate now = LocalDate.now();

    private final FXMLLoader fxmlLoaderCustomer = new FXMLLoader(MenuController.class.getResource(CUSTOMER_VIEW_FXML));
    private final FXMLLoader fxmlLoaderProduct = new FXMLLoader(MenuController.class.getResource(PRODUCT_VIEW_FXML));

    private Scene scene = null;
    private Stage stage;

    private final Alert alertCustomer = new Alert(Alert.AlertType.WARNING);
    private final Alert alertProduct = new Alert(Alert.AlertType.WARNING);
    private final ButtonType buttonTypeAccept = new ButtonType("YES");
    private final ButtonType buttonTypeCancel = new ButtonType("NO");


    private final CustomerDAO customerDAO = new CustomerDAO();

    private Customer customer;
    private final ProductDAO productDAO = new ProductDAO();

    public TextField serial;
    public TextField codCustomer;
    public TextField codProduct;
    public TextField price;
    public TextField customerName;
    public TextField productName;
    public TextField stock;
    public TextField seller;
    public TextField total;
    public TextField date;


    public Spinner<Integer> quantity;
    public TableView<ShoppingCart> tableSale;
    public TableColumn<ShoppingCart,Integer> colNro;
    public TableColumn<ShoppingCart,String> colCod;
    public TableColumn<ShoppingCart,String> colProduct;
    public TableColumn<ShoppingCart, Integer> colQuantity;
    public TableColumn<ShoppingCart, Double> colPrice;
    public TableColumn<ShoppingCart,Double> colTotal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(idSeller);

        total.setText("0.0");

        setSerial();


        seller.setText(sellerName);
        date.setText(String.valueOf(now));

        alertCustomer.setTitle("New customer");
        alertCustomer.setHeaderText("The customer doesn´t exist");
        alertCustomer.setContentText("Do you want to add it?");
        alertCustomer.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);

        alertProduct.setTitle("New Product");
        alertProduct.setHeaderText("The product doesn´t exist");
        alertProduct.setContentText("Do you want to add it?");
        alertProduct.getButtonTypes().setAll(buttonTypeAccept, buttonTypeCancel);

        colNro.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().nr()).asObject());
        colCod.setCellValueFactory(p->new SimpleStringProperty(p.getValue().cod()));
        colProduct.setCellValueFactory(p->new SimpleStringProperty(p.getValue().product()));
        colQuantity.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().quantity()).asObject());
        colPrice.setCellValueFactory(p->new SimpleDoubleProperty(p.getValue().price()).asObject());
        colTotal.setCellValueFactory(p->new SimpleDoubleProperty(p.getValue().total()).asObject());

        tableSale.getItems().clear();

        products = FXCollections.observableArrayList();

    }


    public void searchCustomer(ActionEvent actionEvent) {
        customer = customerDAO.searchCustomer(Integer.parseInt(codCustomer.getText()));
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
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, product.stock(), 0);
            quantity.setValueFactory(valueFactory);
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
        MenuController.cleanCells(codCustomer,codProduct,customerName,productName,price,stock);
        quantity.getValueFactory().setValue(null);
        tableSale.getItems().clear();
        MenuController.setAlert(Alert.AlertType.INFORMATION,"Sale Canceled");
        total.clear();
    }

    public void generateSale(ActionEvent actionEvent) {

        if (products.isEmpty()){
            MenuController.setAlert(Alert.AlertType.ERROR,"There is no product added");
            return;
        }

        Sales sales = new Sales(customer.idCustomer(),idSeller, serial.getText(),
                          LocalDate.parse(date.getText()),Double.parseDouble(total.getText()),
                          Sales.State.ACTIVE);


        if (salesDAO.SaveSale(sales) && salesDAO.SaveDetailsSale(products,idSale) ){
            productDAO.subtractStock(products);
            MenuController.setAlert(Alert.AlertType.CONFIRMATION,"Successful Sale");
            MenuController.cleanCells(codCustomer,codProduct,customerName,productName,price,stock);
            quantity.getValueFactory().setValue(null);
            tableSale.getItems().clear();
            setSerial();
            total.setText("0.0");
            products.clear();

            //Para que al ingresar a reports se actulize la tabla
            ReportsController.setSales(null);
        }

    }

    public static void setSellerName(String sellerName) {
        GenerateSaleController.sellerName = sellerName;
    }

    public static void setIdSeller(int idSeller) {
        GenerateSaleController.idSeller = idSeller;
    }

    public void addShoppingCart(ActionEvent actionEvent) {

        String errorMessage = validateInputs();

        if (errorMessage != null) {
            MenuController.setAlert(Alert.AlertType.ERROR, errorMessage);
            return;
        }

        ShoppingCart product = new ShoppingCart(contProducts++,codProduct.getText(),
                productName.getText(),quantity.getValue(),
                Double.parseDouble(price.getText()));

        if (products.stream().anyMatch(e -> Objects.equals(e.cod(), product.cod()))){
            MenuController.setAlert(Alert.AlertType.ERROR, "This product is already in your shopping cart");
            return;
        }

        products.add(product);
        tableSale.setItems(products);
        total.setText(String.format("%.2f",Double.parseDouble(total.getText()) + product.total()));
    }

    private String validateInputs() {
        if (productName.getText().isEmpty() || customerName.getText().isEmpty()) {
            return "Missing customer name or product name.";
        } else if (quantity.getValue() == 0) {
            return "Quantity can't be 0.";
        }
        return null;
    }

    private void setSerial(){
        idSale = 1+salesDAO.IdSale();
        String formattedId= String.format("%04d", idSale);
        serial.setText(formattedId);
    }


}
