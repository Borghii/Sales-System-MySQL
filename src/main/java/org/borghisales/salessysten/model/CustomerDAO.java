package org.borghisales.salessysten.model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.borghisales.salessysten.controllers.MenuController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO implements CRUD<Customer> {

    public Customer searchCustomer(int dni){
        String sql = "SELECT * FROM customer WHERE dni=?";
        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,dni);

            try (ResultSet rs = pstmt.executeQuery()){
                rs.next();
                return Customer.fromResultSet(rs);
            }

        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error searching customer: " + e.getMessage());
            return null;
        }

    }
    @Override
    public boolean create(Customer entity) {
        String sql = "Insert into customer (dni,name,address,state) values(?,?,?,?)";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, entity.dni());
            pstmt.setString(2, entity.name());
            pstmt.setString(3, entity.address());
            pstmt.setString(4, entity.state().toString());

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Customer added correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error adding customer: ");
                return false;
            }

        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error adding customer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean read(int id) {
        return false;
    }

    @Override
    public boolean update(Customer entity) {
        String sql = "UPDATE customer set name=?,address=?,state=? where dni=?";

        try(Connection conn = DBConnection.connection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.address());
            pstmt.setString(3, entity.state().name());
            pstmt.setString(4, entity.dni());


            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Customer updated correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error updating customer");
                return false;
            }

        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error updating customer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM customer where dni=?";

        try(Connection conn = DBConnection.connection();
            PreparedStatement pstmt = conn.prepareStatement(sql))    {


            pstmt.setString(1,id);

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Customer deleted correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error deleting customer: ");
                return false;
            }



        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void setTable(ObservableList<Customer> customers) {
        String sql = "SELECT * FROM customer";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            try (ResultSet rs = pstmt.executeQuery()){

                while (rs.next()){
                    Customer customer = Customer.fromResultSet(rs);
                    customers.add(customer);
                }

            }
        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error setting the table customer: " + e.getMessage());
        }

    }
}
