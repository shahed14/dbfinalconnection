/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class ClientViewInvoices extends Application {
    TableView<Invoices> invoicesTableView;
    TableColumn<Invoices, Integer> id_tableColumn , order_id_tableColumn , totalPrice_tableColumn;
    TableColumn<Invoices , String> date_tableColumn;
    Button back , view;
    Scene scene = null;
    Connection connection = null;
    Statement statement = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            connection = DBconnection.get_connection();
            statement = connection.createStatement();
            
             } catch (SQLException ex) {
                System.out.println(ex); 
        }
        
        eventHandler eh = new eventHandler();
        invoicesTableView = new TableView<>();
        id_tableColumn = new TableColumn<>("ID");
        id_tableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        
        order_id_tableColumn = new TableColumn<>("Oreder_Id");
        order_id_tableColumn.setCellValueFactory(new PropertyValueFactory("order_id"));
        
        totalPrice_tableColumn = new TableColumn<>("Total_price");
        totalPrice_tableColumn.setCellValueFactory(new PropertyValueFactory("totalPrice"));
        
        date_tableColumn = new TableColumn<>("Date");
        date_tableColumn.setCellValueFactory(new PropertyValueFactory("date"));
        
        invoicesTableView.getColumns().addAll(id_tableColumn , order_id_tableColumn , totalPrice_tableColumn,date_tableColumn);
        back = new Button("Bach to dashboard");
        back.setId("rich-blue");
        back.setOnAction(eh);
        view = new Button("View Invoices");
        view.setId("rich-blue");
        view.setOnAction(eh);
        HBox hbButton = new HBox(15 , view , back);
        hbButton.setAlignment(Pos.CENTER);
        hbButton.setPadding(new Insets(10));
        VBox vbAll = new VBox(10 , invoicesTableView , hbButton);
        vbAll.setAlignment(Pos.CENTER);
        vbAll.setPadding(new Insets(10));
        
        
        
        
        
        
            scene = new Scene(vbAll ,400,500);
            primaryStage.setTitle("view Invoices!");
            primaryStage.setScene(scene);
         scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

            primaryStage.show();
    }
           public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==view){
                try {
                    String sql = "select * from invoices ";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<Invoices> invoices_List = new ArrayList<>();
                    while(rs.next()){
                        Invoices invoice = new Invoices();
                        invoice.setId(rs.getInt("Id"));
                        invoice.setOrder_id(rs.getInt("Order_id"));
                        invoice.setTotalPrice(rs.getInt("Total_price"));
                        invoice.setDate(rs.getString("Date"));
                        invoices_List.add(invoice);
                    }
                    invoicesTableView.getItems().setAll(invoices_List);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                
            }
            
        }
               
           }
           

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    private boolean validate_input(String input){
        return !input.equals("");
    }
    
    private boolean validate_numric_value(String input) {
        if (validate_input(input)) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
        return false;
    }

}
