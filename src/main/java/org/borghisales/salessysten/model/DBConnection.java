package org.borghisales.salessysten.model;

import javafx.scene.control.Alert;
import org.borghisales.salessysten.controllers.MenuController;

import java.sql.*;

public class DBConnection {

    private static Connection connection() throws SQLException{
        String url = "jdbc:mysql://salesdb.mysql.database.azure.com:3306/salesystem";
        String user = "Borghi";
        String password = "Qwertyuiop1$";
        return DriverManager.getConnection(url,user,password);
    }

    private static boolean verifyDuplicates(String name, String surname){
        String query = "SELECT* FROM employee_data WHERE NAME = ? AND SURNAME = ?";
        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query) ){

            pstmt.setString(1,name.strip());
            pstmt.setString(2,surname.strip());

            try (ResultSet rs = pstmt.executeQuery()){
                return !(rs.next());
            }

        }catch(SQLException e){
            MenuController.setAlert(Alert.AlertType.ERROR,"Error adding employee: " + e.getMessage());
            return false;
        }

    }

    public static boolean login(String dni,String user){

        if (dni ==null || user ==null || dni.isEmpty()||user.isEmpty() ){
            MenuController.setAlert(Alert.AlertType.ERROR,"User or passsword empty");
            return false;
        }


        String query = "SELECT * from seller where dni = ? and user = ?";

        try (Connection conn = connection();
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

//    public static boolean addEmployee(Employee employee) {
//        String query = "INSERT INTO employee_data (NAME , SURNAME, HIRE_DATE,STATE) VALUES (?, ?, ?,?)";
//
//        if (verifyDuplicates(employee.getName(),employee.getSurname())) {
//            try (Connection conn = connection();
//                 PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//                pstmt.setString(1, employee.getName());
//                pstmt.setString(2, employee.getSurname());
//                pstmt.setDate(3, Date.valueOf(employee.getHireDate()));
//                pstmt.setString(4, String.valueOf(employee.getState()));
//
//                pstmt.executeUpdate();
//
//                MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Employee added");
//                return true;
//
//            } catch (SQLException e) {
//                MenuController.setAlert(Alert.AlertType.ERROR, "Error adding employee: " + e.getMessage());
//                return false;
//            }
//        }else {
//            MenuController.setAlert(Alert.AlertType.ERROR,"Duplicate employee");
//            return false;
//        }
//
//    }
//
//    public static void searchEmployee(TextField NAME, TextField SURNAME, TextField HIRE_DATE, int id){
//
//        String query = "SELECT * FROM employee_data WHERE ID_EMPLOYEE = ? ";
//
//        try (Connection conn = connection();
//            PreparedStatement pstmt = conn.prepareStatement(query)){
//
//            pstmt.setInt(1,id);
//
//            try (ResultSet rs = pstmt.executeQuery()){
//
//                    if (rs.next()){
//                        if (rs.getDate("UNHIRE_DATE")==null) {
//                            NAME.setText(rs.getString("NAME"));
//                            SURNAME.setText(rs.getString("SURNAME"));
//                            HIRE_DATE.setText(String.valueOf(rs.getDate("HIRE_DATE")));
//                            MenuController.setAlert(Alert.AlertType.CONFIRMATION, "Employee found");
//                        }else{
//                            MenuController.setAlert(Alert.AlertType.INFORMATION,"Employee: "+rs.getString("SURNAME")+" no longer employed since: "+rs.getDate("UNHIRE_DATE"));
//                        }
//                    }else {
//                        MenuController.setAlert(Alert.AlertType.ERROR,"Employee with id: "+id+" does not exist");
//                    }
//
//            }
//        }catch (SQLException e){
//            MenuController.setAlert(Alert.AlertType.ERROR,"Employee not found: " + e.getMessage());
//        }
//
//    }
//
//    public static boolean modifyEmployee(TextField NAME, TextField SURNAME, TextField HIRE_DATE, int id){
//        String query = "UPDATE employee_data SET NAME= ?, SURNAME= ?, HIRE_DATE= ? WHERE ID_EMPLOYEE= ?";
//
//        try(Connection conn = connection();
//            PreparedStatement pstmt = conn.prepareStatement(query)){
//
//            pstmt.setString(1,NAME.getText());
//            pstmt.setString(2,SURNAME.getText());
//            pstmt.setDate(3,Date.valueOf(HIRE_DATE.getText()));
//            pstmt.setInt(4,id);
//            pstmt.executeUpdate();
//
//            MenuController.setAlert(Alert.AlertType.CONFIRMATION,"Employee modify correctly");
//
//            return true;
//
//
//        }catch (SQLException e){
//            MenuController.setAlert(Alert.AlertType.ERROR,"Employee not modify: " + e.getMessage());
//            return false;
//
//        }catch (IllegalArgumentException e){
//            MenuController.setAlert(Alert.AlertType.ERROR,"Format of date wrong: (yyyy-mm-dd)");
//            return false;
//        }
//    }
//    public static boolean deleteEmployee(int id){
//        String query = "UPDATE employee_data SET UNHIRE_DATE=?, STATE=? WHERE ID_EMPLOYEE= ?";
//        try (Connection conn = connection();
//             PreparedStatement pstmt = conn.prepareStatement(query)){
//
//            pstmt.setDate(1, Date.valueOf(LocalDate.now().plusDays(100)));
//            pstmt.setString(2,String.valueOf(Employee.State.INACTIVE));
//            pstmt.setInt(3,id);
//
//            pstmt.executeUpdate();
//
//            MenuController.setAlert(Alert.AlertType.CONFIRMATION,"Employee unhired correctly");
//
//            return true;
//
//
////            if (pstmt.executeUpdate()>0){
////                MenuController.setAlert(Alert.AlertType.CONFIRMATION,"Employee delete correctly");
////                return true;
////            }else {
////                MenuController.setAlert(Alert.AlertType.ERROR,"Employee not delete: ");
////                return false;
////            }
//
//
//        }catch (SQLException e){
//            MenuController.setAlert(Alert.AlertType.ERROR,"Employee not delete: " + e.getMessage());
//            return false;
//        }
//    }
//
//    static public void setTable(ObservableList<Employee> employees){
//        String query = "SELECT * FROM employee_data";
//
//        try (Connection conn = connection();
//             PreparedStatement stmt = conn.prepareStatement(query))   {
//
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()){
//                Date unhire = rs.getDate("UNHIRE_DATE");
//                LocalDate unHire = null;
//
//                if (!(unhire==null))
//                    unHire = unhire.toLocalDate();
//
//                Employee employee = new Employee(rs.getInt("ID_EMPLOYEE"),unHire,
//                                    rs.getString("NAME"),rs.getString("SURNAME"),rs.getDate("HIRE_DATE").toLocalDate(),
//                                     Employee.State.valueOf(rs.getString("STATE")));
//                employees.add(employee);
//
//            }
//
//        }catch (SQLException e){
//            MenuController.setAlert(Alert.AlertType.ERROR,"Employees doesn`t found: " + e.getMessage());
//        }
//
//    }

}
