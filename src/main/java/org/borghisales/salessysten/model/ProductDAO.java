package org.borghisales.salessysten.model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.borghisales.salessysten.controllers.MenuController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO implements CRUD<Product>{

    @Override
    public boolean create(Product entity) {
        String sql = "INSERT INTO product (name,price,stock,state) values (?,?,?,?)";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, entity.name());
            pstmt.setDouble(2, entity.price());
            pstmt.setInt(3, entity.stock());
            pstmt.setString(4, entity.state().name());

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Product added correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error adding product: ");
                return false;
            }


        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error adding product: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean read(int id) {
        return false;
    }

    @Override
    public boolean update(Product entity) {
        String sql = "UPDATE product set price=?,stock=?,state=? where name=?";

        try(Connection conn = DBConnection.connection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){



            pstmt.setDouble(1, entity.price());
            pstmt.setInt(2, entity.stock());
            pstmt.setString(3, entity.state().name());
            pstmt.setString(4, entity.name());


            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Product updated correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error updating product");
                return false;
            }

        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error updating product: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM product where name=?";

        try(Connection conn = DBConnection.connection();
            PreparedStatement pstmt = conn.prepareStatement(sql))    {


            pstmt.setString(1,id);

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Product deleted correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error deleting product: ");
                return false;
            }



        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error deleting product: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void setTable(ObservableList<Product> products) {
        String sql = "SELECT * FROM product";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            try (ResultSet rs = pstmt.executeQuery()){

                while (rs.next()){
                    Product product = new Product(rs.getInt("idProduct"),rs.getString("name"),
                            rs.getDouble("price"), rs.getInt("stock"),
                            Product.State.valueOf(rs.getString("state")));

                    products.add(product);
                }

            }
        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error setting the table seller: " + e.getMessage());
        }

    }
}
