package org.borghisales.salessysten.model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.borghisales.salessysten.controllers.GenerateSaleController;
import org.borghisales.salessysten.controllers.MainController;
import org.borghisales.salessysten.controllers.MenuController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDAO implements CRUD<Seller> {
    @Override
    public boolean create(Seller entity) {
        String sql = "INSERT INTO seller (dni,name,phone_number,state,user) values (?,?,?,?,?)";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, entity.dni());
            pstmt.setString(2, entity.name());
            pstmt.setString(3, entity.phoneNumber());
            pstmt.setString(4, entity.state().name());
            pstmt.setString(5, entity.user());

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Seller added correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error adding seller: ");
                return false;
            }


        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error adding seller: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean read(int id) {
        return false;
    }

    @Override
    public boolean update(Seller entity) {
        String sql = "UPDATE seller set name=?,phone_number=?,state=?,user=? where dni=?";

        try(Connection conn = DBConnection.connection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){


            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.phoneNumber());
            pstmt.setString(3, entity.state().name());
            pstmt.setString(4, entity.user());
            pstmt.setString(5, entity.dni());

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Seller updated correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error updating seller ");
                return false;
            }

        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error updating seller: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(String id) {

        String sql = "DELETE FROM seller where dni=?";

        try(Connection conn = DBConnection.connection();
            PreparedStatement pstmt = conn.prepareStatement(sql))    {


            pstmt.setString(1,id);

            int rows_affected = pstmt.executeUpdate();

            if (rows_affected>0){
                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Seller deleted correctly");
                return true;
            }else{
                MenuController.setAlert(Alert.AlertType.ERROR, "Error deleting seller: ");
                return false;
            }



        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error deleting seller: " + e.getMessage());
            return false;
        }

    }

    @Override
    public void setTable(ObservableList<Seller> sellers){
        String sql = "SELECT * FROM seller";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            try (ResultSet rs = pstmt.executeQuery()){

                while (rs.next()){
                    Seller seller = Seller.fromResultSet(rs);
                    sellers.add(seller);
                }

            }
        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error setting the table seller: " + e.getMessage());
        }
    }


    public static boolean login(String dni,String user){

        if (dni ==null || user ==null || dni.isEmpty()||user.isEmpty() ){
            MenuController.setAlert(Alert.AlertType.ERROR,"User or passsword empty");
            return false;
        }

        String query = "SELECT * from seller where dni = ? and user = ?";

        try (Connection conn = DBConnection.connection();
             PreparedStatement pstmt = conn.prepareStatement(query)  ){

            pstmt.setString(1,dni);
            pstmt.setString(2,user);

            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){

                    GenerateSaleController.setSellerName(rs.getString("name"));
                    GenerateSaleController.setIdSeller(rs.getInt("idSeller"));

                    MainController.sellerLog = Seller.fromResultSet(rs);

                    return true;
                }else{
                    MenuController.setAlert(Alert.AlertType.ERROR, "user not found") ;
                    return false;
                }
            }
        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error searching seller: " + e.getMessage());
            return false;
        }

    }
}
