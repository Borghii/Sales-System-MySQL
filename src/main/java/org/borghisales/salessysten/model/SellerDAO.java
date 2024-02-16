package org.borghisales.salessysten.model;

import javafx.scene.control.Alert;
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
                MenuController.setAlert(Alert.AlertType.ERROR, "Error adding seller: ");
                return false;
            }

        }catch (SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR, "Error updating seller: " + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Seller> getAll() {
        return null;
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
                if (rs.next())
                    return true;
                else  {
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
