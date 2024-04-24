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
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;

public class ReportsController implements Initializable {

    private static final String[] monthsShowed = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static int idxMonth = LocalDate.now().getMonth().getValue()-1;
    private static int yearsShowed = LocalDate.now().getYear();


    private static HashMap<Integer,HashMap<Integer,XYChart.Series<String,Integer>>> cacheReportLineChart= new HashMap<>();

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

    public static void removeCacheLineChart(int year, int month) {
        if (cacheReportLineChart != null && cacheReportLineChart.containsKey(year)) {
            HashMap<Integer, XYChart.Series<String, Integer>> yearData = cacheReportLineChart.get(year);
            if (yearData != null && yearData.containsKey(month)) {
                yearData.remove(month);
                lineChartData = null;
            } else {
                System.err.println("No cache found for month " + month + " in year " + year);
            }
        } else {
            System.err.println("No cache found for year " + year);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLineChart();
        setupPieChart();
        setupTableView();
        setupEventHandlers();
        setupComboBox();
    }

    private void setupLineChart() {
        x_time.setAutoRanging(true);

        List<String> categories = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            categories.add(String.valueOf(i));
        }

        x_time.setAutoRanging(false);
        x_time.setCategories(FXCollections.observableArrayList(categories));
        x_time.setLabel("Days of the month");
        y_amountSales.setLabel("Sales amount");

        year.setText(String.valueOf(yearsShowed));
        month.setText(monthsShowed[idxMonth]);

        if (lineChartData == null) {
            lineChartData = new XYChart.Series<>();
            salesDAO.setLineChart(lineChartData, Integer.parseInt(year.getText()), idxMonth + 1);
        }

        fillMissingDays(lineChartData);
        salesLineChart.getData().add(lineChartData);
    }

    private void setupPieChart() {
        if (pieChartData == null) {
            pieChartData = FXCollections.observableArrayList();
            ProductDAO.setPieChart(pieChartData);

            pieChartData.forEach(data ->
                    data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " amount: ", (int) data.pieValueProperty().doubleValue()
                            )
                    )
            );
        }
        pieChartProducts.getData().addAll(pieChartData);
    }

    private void setupTableView() {
        colIdSales.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idSales()).asObject());
        colIdCustomer.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idCustomer()).asObject());
        colIdSeller.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().idSeller()).asObject());
        colNumberSales.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().numberSales()));
        colSaleDate.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().saleDate()));
        colAmount.setCellValueFactory(p -> new SimpleDoubleProperty((p.getValue().amount())).asObject());
        colState.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().state()));

        tableReport.getItems().clear();

        if (sales == null) {
            sales = FXCollections.observableArrayList();
            salesDAO.setTable(sales);
        }

        tableReport.setItems(sales);
    }

    private void setupEventHandlers() {
        tableReport.setOnMouseClicked(mouseEvent -> {
            if (!tableReport.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 2) {
                int idSales = tableReport.getSelectionModel().getSelectedItem().idSales();
                SaleDetailController.setIdSale(idSales);

                FXMLLoader fxmlLoaderSaleDetails = new FXMLLoader(MenuController.class.getResource(MainController.SALE_DETAIL_VIEW_FXML));

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
    }

    private void setupComboBox() {
        cbTypeExport.setValue(".PDF");
        cbTypeExport.setItems(exportList);
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


    // Method to add values to the HashMap
    public static void setCacheReportLineChart(int year, int month, XYChart.Series<String, Integer> lineChartData) {
        if (lineChartData != null) {
            // Check if the year is already in the HashMap
            if (!cacheReportLineChart.containsKey(year)) {
                cacheReportLineChart.put(year, new HashMap<>());
            }
            //  Obtain the map of months for the given year
            HashMap<Integer, XYChart.Series<String, Integer>> yearData = cacheReportLineChart.get(year);
            // Check if a series already exists for the given month
            if (!yearData.containsKey(month)) {
                // Add data series to the map for the given month
                XYChart.Series<String, Integer> series = new XYChart.Series<>();
                series.getData().addAll(lineChartData.getData());
                series.setName(monthsShowed[idxMonth]);
                yearData.put(month, series);
            } else {
                System.out.println("There is already a series for the year" + year + " and the month " + month + ". No new series will be added");
            }
        } else {
            System.out.println("Error: lineChartData is null. Cannot add to cache map.");
        }

        recorrerHashMap();
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


        lineChartData.getData().clear();


        if (checkCache()){
            salesDAO.setLineChart(lineChartData,yearsShowed, idxMonth+1);
        }else {
            lineChartData.getData().addAll(cacheReportLineChart.get(yearsShowed).get(idxMonth+1).getData());
        }



        fillMissingDays(lineChartData);


        salesLineChart.getData().clear();
        salesLineChart.getData().add(lineChartData);


        year.setText(String.valueOf(yearsShowed));
    }

    public void subtractYear(ActionEvent actionEvent) {
        yearsShowed--;


        lineChartData.getData().clear();


        if (checkCache()){
            salesDAO.setLineChart(lineChartData,yearsShowed, idxMonth+1);
        }else {
            lineChartData.getData().addAll(cacheReportLineChart.get(yearsShowed).get(idxMonth+1).getData());
        }



        fillMissingDays(lineChartData);


        salesLineChart.getData().clear();
        salesLineChart.getData().add(lineChartData);





        year.setText(String.valueOf(yearsShowed));
    }

    public void sumMonth(ActionEvent actionEvent) {
        if (idxMonth==11) return;

        idxMonth++;

        lineChartData.getData().clear();


        if (checkCache()){
            salesDAO.setLineChart(lineChartData,Integer.parseInt(year.getText()), idxMonth+1);
        }else {
            lineChartData.getData().addAll(cacheReportLineChart.get(yearsShowed).get(idxMonth+1).getData());
        }



        fillMissingDays(lineChartData);


        salesLineChart.getData().clear();
        salesLineChart.getData().add(lineChartData);


        month.setText(monthsShowed[idxMonth]);

    }

    public void subtractMonth(ActionEvent actionEvent) {
        if (idxMonth==0) return;
        idxMonth--;


        salesLineChart.getData().remove(lineChartData);



        lineChartData.getData().clear();


        if (checkCache()){
            salesDAO.setLineChart(lineChartData,Integer.parseInt(year.getText()), idxMonth+1);
        }else {
            lineChartData.getData().addAll(cacheReportLineChart.get(yearsShowed).get(idxMonth+1).getData());
        }

        fillMissingDays(lineChartData);


        salesLineChart.getData().add(lineChartData);

        month.setText(monthsShowed[idxMonth]);
    }


    public static void recorrerHashMap() {
        for (Integer year : cacheReportLineChart.keySet()) {
            System.out.println("Year: " + year);
            HashMap<Integer, XYChart.Series<String, Integer>> yearData = cacheReportLineChart.get(year);
            for (Integer month : yearData.keySet()) {
                System.out.println("  Month: " + month);
                XYChart.Series<String, Integer> series = yearData.get(month);
                if (series != null) {
                    System.out.println("    Series: " + series.getName());
                    for (XYChart.Data<String, Integer> data : series.getData()) {
                        System.out.println("      Data: " + data.getXValue() + ", " + data.getYValue());
                    }
                } else {
                    System.out.println("    No hay serie asociada para este mes.");
                }
            }
        }
    }

    private static boolean checkCache() {
        // First, check if the year is not null
        if (cacheReportLineChart.get(yearsShowed) != null) {
            // If the year is not null, then you can check if the data for the month is null or not
            return cacheReportLineChart.get(yearsShowed).get(idxMonth + 1) == null;
        } else {
            // If the year is null, you can return false or any value that is appropriate for your logic
            return true;
        }
    }


    public void fillMissingDays(XYChart.Series<String, Integer> lineChartData) {
        // Create a map to store sales by day
        Map<Integer, Integer> salesByDay = new HashMap<>();

        // Get existing data from the series and store it in the map
        for (XYChart.Data<String, Integer> data : lineChartData.getData()) {
            int day = Integer.parseInt(data.getXValue());
            int sales = data.getYValue();
            salesByDay.put(day, sales);
        }

        // Iterate over all days of the month and add missing series
        for (int i = 1; i <= 31; i++) { // Assuming a maximum of 31 days in a month
            if (!salesByDay.containsKey(i)) {
                // If there is no data for this day, add a point with a value of zero
                XYChart.Data<String, Integer> data = new XYChart.Data<>(String.valueOf(i), 0);
                lineChartData.getData().add(data);
            }
        }

        // Sort the data in the series by day
        lineChartData.getData().sort(Comparator.comparingInt(data -> Integer.parseInt(data.getXValue())));
    }






}
