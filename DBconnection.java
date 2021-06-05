/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class DBconnection {
    public static Connection get_connection() throws Exception{
        Connection conn = null;
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/ordersdesktop";
            String username = "root";
            String password = "";
            Class.forName(driver);
       conn = DriverManager.getConnection(url, username, password);
            System.out.println("connected");
        }catch(Exception e){
            System.out.println(e);
           
        }
        return conn;
    }
    
    Stage stage;
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ordersdesktop";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "";
	private static final String SELECT_QUERY = "SELECT * FROM users WHERE Email = ? and Password = ? ";
	//private static final String Select_query2 = "SELECT Role FROM users ";
	public boolean validate(String emailId, String password ) throws SQLException, ClassNotFoundException, IOException {
		
       
                Class.forName(driver);
        try (Connection connection = DriverManager
            .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, emailId);
            preparedStatement.setString(2, password);
            
            System.out.println(preparedStatement);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                
            String username_in =    resultSet.getString("Email");
             String password_in = resultSet.getString("Password");
             String role = resultSet.getString("Role");
             if(role ==null){
                 System.out.println("role is empty");
             }
             else if(role.equalsIgnoreCase(role)){
                 System.out.println("hye " + role);
                 if("Admin".equals(role)){
                     FXMLLoader loader = new FXMLLoader();
                     
                     
                     
                 }
             }
             return true;
            }           	
            
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
      public static void openNewScene(Class myClass,AnchorPane from , String to , String title) throws IOException {
        Stage stage=(Stage)from.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(myClass.getResource(to));
        Parent parent=loader.load();
        Scene scene=new Scene(parent);
        stage.setTitle(title);
        stage.setScene(scene);

    }
            }
        

