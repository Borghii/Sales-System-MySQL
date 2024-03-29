package org.borghisales.salessysten.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.borghisales.salessysten.model.Product;
import org.borghisales.salessysten.model.Sales;
import org.borghisales.salessysten.model.SalesDAO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportsController implements Initializable {

    private final ObservableList<String> exportList = FXCollections.observableArrayList(".PDF",".XLSX",".CSV");

    private final SalesDAO salesDAO = new SalesDAO();
    private static ObservableList<Sales> sales = null;

    @FXML
    private DatePicker minDate;
    @FXML
    private DatePicker maxDate;
    @FXML
    private TextField minAmount;
    @FXML
    private TextField maxAmount;
    @FXML
    private ComboBox<String> cbTypeExport;
    @FXML
    private TableView<Sales> tableReport;
    @FXML
    private TableColumn<Sales,Integer> colIdSales;
    @FXML
    private TableColumn<Sales,Integer> colIdCustomer;
    @FXML
    private TableColumn<Sales,Integer> colIdSeller;
    @FXML
    private TableColumn<Sales,String> colNumberSales;
    @FXML
    private TableColumn<Sales, LocalDate> colSaleDate;
    @FXML
    private TableColumn<Sales,Double> colAmount;
    @FXML
    private TableColumn<Sales, Sales.State> colState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbTypeExport.setValue(".PDF");
        cbTypeExport.setItems(exportList);

        colIdSales.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().idSales()).asObject());
        colIdCustomer.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().idCustomer()).asObject());
        colIdSeller.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().idSeller()).asObject());
        colNumberSales.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().numberSales()));
        colSaleDate.setCellValueFactory(p-> new SimpleObjectProperty<>(p.getValue().saleDate()));
        colAmount.setCellValueFactory(p-> new SimpleDoubleProperty((p.getValue().amount())).asObject());
        colState.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().state()));

        tableReport.getItems().clear();

        if (sales==null){
            sales = FXCollections.observableArrayList();
            salesDAO.setTable(sales);
        }

        tableReport.setItems(sales);
    }

    public void onFilter(ActionEvent actionEvent) {
        if (sales.isEmpty()){
            MenuController.setAlert(Alert.AlertType.WARNING,"There are no sales");
            return;
        }

        try {
            double minAmount = this.minAmount.getText().isEmpty() ? Double.MIN_VALUE: Double.parseDouble(this.minAmount.getText());
            double maxAmount = this.maxAmount.getText().isEmpty() ? Double.MAX_VALUE: Double.parseDouble(this.maxAmount.getText());


            LocalDate minDate = this.minDate.getValue() == null ? LocalDate.MIN : this.minDate.getValue();
            LocalDate maxDate = this.maxDate.getValue() == null ? LocalDate.MAX : this.maxDate.getValue();


            if (minAmount >= maxAmount) {
                MenuController.setAlert(Alert.AlertType.ERROR,"Set correct amount intervals");
                return;
            }
            if (minDate.isAfter(maxDate)) {
                MenuController.setAlert(Alert.AlertType.ERROR,"Set correct date intervals");
                return;
            }

            Predicate<Sales> amountFilter = p -> p.amount() >= minAmount && p.amount() <= maxAmount;
            Predicate<Sales> dateFilter = p -> p.saleDate().isAfter(minDate) && p.saleDate().isBefore(maxDate);

            tableReport.setItems(FXCollections.observableArrayList(sales.stream()
                    .filter(amountFilter)
                    .filter(dateFilter)
                    .toList()));

        } catch (NumberFormatException e) {
            MenuController.setAlert(Alert.AlertType.WARNING,e.getMessage());
        }
    }


    public void onExport(ActionEvent actionEvent) {
    }

    public static void setSales(ObservableList<Sales> sales) {
        ReportsController.sales = sales;
    }
}
