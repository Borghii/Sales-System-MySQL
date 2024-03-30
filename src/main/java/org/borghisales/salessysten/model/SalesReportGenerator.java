package org.borghisales.salessysten.model;

import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javafx.collections.ObservableList;
import org.borghisales.salessysten.controllers.MenuController;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;

import java.awt.*;
import java.io.IOException;

public class SalesReportGenerator {

    public static void generateCSVReport(ObservableList<Sales> salesList, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            // Escribir encabezados de columna
            writer.append("ID Sales,ID Customer,ID Seller,Number Sales,Sale Date,Amount,State\n");

            // Escribir datos de ventas
            for (Sales sale : salesList) {
                writer.append(String.valueOf(sale.idSales())).append(",");
                writer.append(String.valueOf(sale.idCustomer())).append(",");
                writer.append(String.valueOf(sale.idSeller())).append(",");
                writer.append(sale.numberSales()).append(",");
                writer.append(sale.saleDate().toString()).append(",");
                writer.append(String.valueOf(sale.amount())).append(",");
                writer.append(sale.state().toString()).append("\n");
            }

            MenuController.setAlert(Alert.AlertType.CONFIRMATION, "CSV Report generated successfully at " + outputPath);

        } catch (IOException e) {
            MenuController.setAlert(Alert.AlertType.ERROR, "CSV Report generated unsuccessfully: " + e.getMessage());
        }
    }
    public static void generateExcelReport(ObservableList<Sales> salesList, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sales Report");

            // Crear encabezados de columna
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID Sales", "ID Customer", "ID Seller", "Number Sales", "Sale Date", "Amount", "State"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Agregar datos de ventas
            int rowNum = 1;
            for (Sales sale : salesList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sale.idSales());
                row.createCell(1).setCellValue(sale.idCustomer());
                row.createCell(2).setCellValue(sale.idSeller());
                row.createCell(3).setCellValue(sale.numberSales());
                row.createCell(4).setCellValue(sale.saleDate().toString());
                row.createCell(5).setCellValue(sale.amount());
                row.createCell(6).setCellValue(sale.state().toString());
            }

            // Ajustar el ancho de las columnas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el libro de trabajo en un archivo
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

            MenuController.setAlert(Alert.AlertType.CONFIRMATION, "EXCEL Report generated successfully at " + outputPath);

        } catch (IOException e) {
            MenuController.setAlert(Alert.AlertType.ERROR, "EXCEL Report generated unsuccessfully: " + e.getMessage());
        }
    }
    public static void generatePDFReport(ObservableList<Sales> salesList, String outputPath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;


            // Obtener los nombres de las columnas
            String[] columnNames = { "ID Sales", "ID Customer", "ID Seller", "Number Sales", "Sale Date", "Amount",
                    "State" };

            // Calcular los anchos de columna basados en los nombres de columna más largos
            float[] columnWidths = calculateColumnWidths(columnNames, PDType1Font.HELVETICA, 12);

            // Dibujar encabezados de columna
            drawRow(contentStream, margin, yPosition, tableWidth, rowHeight, columnWidths, Color.LIGHT_GRAY, true,
                    columnNames);
            yPosition -= rowHeight;

            // Dibujar filas con los datos de salesList
            for (Sales sale : salesList) {
                drawRow(contentStream, margin, yPosition, tableWidth, rowHeight, columnWidths, Color.WHITE, false,
                        String.valueOf(sale.idSales()), String.valueOf(sale.idCustomer()),
                        String.valueOf(sale.idSeller()), sale.numberSales(), sale.saleDate().toString(),
                        String.valueOf(sale.amount()), sale.state().toString());
                yPosition -= rowHeight;
            }

            contentStream.close();

            document.save(outputPath);
            MenuController.setAlert(Alert.AlertType.CONFIRMATION, "PDF Report generated successfully at " + outputPath);

        } catch (IOException e) {
            MenuController.setAlert(Alert.AlertType.ERROR, "PDF Report generated unsuccessfully: " + e.getMessage());

        }
    }

    private static float[] calculateColumnWidths(String[] columnNames, PDType1Font font, int fontSize)
            throws IOException {
        float[] columnWidths = new float[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            float textWidth = font.getStringWidth(columnNames[i]) / 1000 * fontSize;
            columnWidths[i] = textWidth + 2 * 5; // Add some margin
        }
        return columnWidths;
    }

    private static void drawRow(PDPageContentStream contentStream, float x, float y, float width, float rowHeight,
                                float[] columnWidths, Color backgroundColor, boolean isHeader, String... data) throws IOException {
        contentStream.setNonStrokingColor(backgroundColor);
        contentStream.fillRect(x, y, width, rowHeight);
        contentStream.setNonStrokingColor(Color.BLACK);
        float cellMargin = 5;

        float nextX = x;
        for (int i = 0; i < data.length; i++) {
            String text = data[i];
            float cellWidth = columnWidths[i];

            float textX = nextX + cellMargin;
            float textY = y + rowHeight / 2 - 12 / 2; // Font size 12
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText(text);
            contentStream.endText();

            nextX += cellWidth;

            // Agregar un espacio entre las columnas
            if (i < data.length - 1) {
                nextX += 10; // Espacio entre columnas
            }
        }
    }

}
