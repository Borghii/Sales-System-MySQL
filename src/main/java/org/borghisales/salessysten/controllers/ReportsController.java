package org.borghisales.salessysten.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;





import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.borghisales.salessysten.model.*;


import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ReportsController implements Initializable {

    private final String[] monthsShowed = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static int idxMonth = LocalDate.now().getMonth().getValue()-1;
    private static int yearsShowed = LocalDate.now().getYear();


    private static HashMap<Integer,HashMap<String,XYChart.Series<String,Integer>>> cacheReportLineChart=null;

    private final ObservableList<String> exportList = FXCollections.observableArrayList(".PDF",".XLSX",".CSV");


    private final SalesDAO salesDAO = new SalesDAO();
    private static ObservableList<Sales> sales = null;
    private static ObservableList<PieChart.Data> pieChartData = null;
    private static XYChart.Series<String,Integer> lineChartData = null;

    @FXML
    private TextField year;
    @FXML
    public TextField month;


    @FXML
    private LineChart<String,Integer> salesLineChart;

    @FXML
    private CategoryAxis x_time;
    @FXML
    private NumberAxis y_amountSales;

    @FXML
    private PieChart pieChartProducts;

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

        year.setText(String.valueOf(yearsShowed));
        month.setText(monthsShowed[idxMonth]);


        //Capturar el evento de click en fila
        tableReport.setOnMouseClicked(mouseEvent -> {
            if (!tableReport.getSelectionModel().isEmpty() && mouseEvent.getClickCount()==2){


                int idSales = tableReport.getSelectionModel().getSelectedItem().idSales();
                SaleDetailController.setIdSale(idSales);

                FXMLLoader fxmlLoaderSaleDetails= new FXMLLoader(MenuController.class.getResource(MainController.SALE_DETAIL_VIEW_FXML));

                try {
                    Scene scene = new Scene(fxmlLoaderSaleDetails.load());
                    Stage stage = new Stage();
                    stage.setTitle("Sale detail");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

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

        if (lineChartData == null){
            lineChartData = new XYChart.Series<>();
            salesDAO.setLineChart(lineChartData,Integer.parseInt(year.getText()), month.getText());
        }

        if (pieChartData ==null){

            pieChartData = FXCollections.observableArrayList();
            ProductDAO.setPieChart(pieChartData);

            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " amount: ", (int)data.pieValueProperty().doubleValue()
                            )
                    )
            );
        }


        salesLineChart.getData().addAll(lineChartData);



        pieChartProducts.getData().addAll(pieChartData);
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

    public static void setSales(ObservableList<Sales> sales) {
        ReportsController.sales = sales;
    }

    public static void setPieChartData(ObservableList<PieChart.Data> pieChartData) {
        ReportsController.pieChartData = pieChartData;
    }

    public static void setLineChartData(XYChart.Series<String, Integer> lineChartData) {
        ReportsController.lineChartData = lineChartData;
    }

    public static void setCacheReportLineChart(int year, String month, XYChart.Series<String, Integer> lineChartData) {
        HashMap<String,XYChart.Series<String, Integer>> monthData = new HashMap<>();
        monthData.put(month,lineChartData);
        cacheReportLineChart.put(year,monthData);
    }

    public void onExport(ActionEvent actionEvent) {
        switch (cbTypeExport.getValue()){
            case ".PDF"  -> SalesReportGenerator.generatePDFReport(tableReport.getItems(),"src/main/resources/reports/sales_report.pdf");
            case ".XLSX" -> SalesReportGenerator.generateExcelReport(tableReport.getItems(),"src/main/resources/reports/sales_report.xlsx");
            case ".CSV" -> SalesReportGenerator.generateCSVReport(tableReport.getItems(),"src/main/resources/reports/sales_report.csv");
        }
    }

    public void sumYear(ActionEvent actionEvent) {
        yearsShowed++;
        year.setText(String.valueOf(yearsShowed));
    }

    public void subtractYear(ActionEvent actionEvent) {
        yearsShowed--;
        year.setText(String.valueOf(yearsShowed));
    }

    public void sumMonth(ActionEvent actionEvent) {
        if (idxMonth==11) return;
        idxMonth++;
        month.setText(monthsShowed[idxMonth]);
    }

    public void subtractMonth(ActionEvent actionEvent) {
        if (idxMonth==0) return;
        idxMonth--;
        month.setText(monthsShowed[idxMonth]);
    }
}
