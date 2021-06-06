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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class AdminManageOrders extends Application {
    MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Label listOfClientLabel , listOfProductsLabel , quantityLabel , orderDateLabel;
    TextField quantityTextFeild , orderDateTextField;
    ComboBox<String> listClient , listProducts;
    Button addOrder , reset , searchOrder , back , viewOrder;
    TableView<orders> order_tableView;
    TableColumn<orders, Integer> listOfClient_tableColumn;
    TableColumn<orders, Integer> listOfProducts_tableColumn;
    TableColumn<orders, Integer> quantity_tableColumn;
    TableColumn<orders, String>orderDate_tableColumn;
    Connection connection;
    Statement statement;
    Scene scene;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            connection = DBconnection.get_connection();
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        eventHandler eventhandler = new eventHandler();
        allMenues = new MenuBar();
        fileMenu = new Menu("File");
        exit = new MenuItem("exit");
        exit.setOnAction(e -> {
            primaryStage.close();
        });
        fileMenu.getItems().add(exit);
        formatMenu = new Menu("format");
        fontSize = new Menu("font size");
        fontSizeToggle = new ToggleGroup();
        small = new RadioMenuItem("small");
        small.setOnAction(e -> {
            scene.getRoot().setStyle("-fx-font-size:small");
        });
        small.setToggleGroup(fontSizeToggle);
        medium = new RadioMenuItem("medium");
        medium.setOnAction(e -> {
            scene.getRoot().setStyle("-fx-font-size:medium");
        });
        medium.setToggleGroup(fontSizeToggle);
        large = new RadioMenuItem("large");
        large.setOnAction(e -> {
            scene.getRoot().setStyle("-fx-font-size:large");
        });
        large.setToggleGroup(fontSizeToggle);
        fontSize.getItems().addAll(small, medium, large);
        fontColor = new MenuItem("font color");
        fontColor.setOnAction(eventhandler);

        fontfamily = new Menu("font family");
        fontFamilyToggle = new ToggleGroup();
        arial = new RadioMenuItem("arial");

        arial.setOnAction(e -> {
            scene.getRoot().setStyle("-fx-font-family:arial;");
        });
        arial.setToggleGroup(fontFamilyToggle);

        sans_serif = new RadioMenuItem("sans_serif");
        sans_serif.setToggleGroup(fontFamilyToggle);
        sans_serif.setOnAction(e -> {
            scene.getRoot().setStyle("-fx-font-family:sans_serif;");
        });
        fontfamily.getItems().addAll(arial, sans_serif);
        backgroundColor = new MenuItem("backgraound color");
        backgroundColor.setOnAction(eventhandler);
        formatMenu.getItems().addAll(fontSize, new SeparatorMenuItem(), fontColor, new SeparatorMenuItem(), fontfamily, new SeparatorMenuItem(),
                 backgroundColor);
        helpMenu = new Menu("Help");
        aboutApp = new MenuItem("about app");
        aboutApp.setOnAction(e -> {
            Dialog<String> di = new Dialog<>();
            ButtonType ok = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
            di.setHeaderText("About app");
            di.getDialogPane().getButtonTypes().add(ok);
            di.setContentText("this page is created in order to manage orders like add,search or edit orders");
            di.show();
        });
        helpMenu.getItems().add(aboutApp);
        allMenues.getMenus().addAll(fileMenu, formatMenu, helpMenu);
        
        listOfClientLabel =new Label("clients :");
        String role = "Client";
         String sql = "select Name from users where Role='"+role+"'";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> client_list = new ArrayList<>();
        while (rs.next()) {
            users u = new users();
            u.setName(rs.getString("Name"));
            client_list.add(u.getName());
        }
       
        listClient = new ComboBox();
        listClient.getItems().addAll(client_list);
        listClient.setEditable(true);
    
    HBox hb1 = new HBox(10,listOfClientLabel,listClient);
     hb1.setAlignment(Pos.CENTER);
     hb1.setPadding(new Insets(10));
     listOfProductsLabel = new Label("Products:");
     String sql2 = "select Name from products";
     ResultSet rs2 = statement.executeQuery(sql2);
     ArrayList<String> product_array = new ArrayList<>();
     while(rs2.next()){
         products p = new products();
         p.setName(rs2.getString("Name"));
         product_array.add(p.getName());
     }
     listProducts = new ComboBox<>();
     listProducts.getItems().addAll(product_array);
     listProducts.setEditable(true);
    HBox hb2 = new HBox(10,listOfProductsLabel,listProducts);
     hb2.setAlignment(Pos.CENTER);
     hb2.setPadding(new Insets(10));
     quantityLabel = new Label("quantity :");
     quantityTextFeild = new TextField();
     HBox hb3 = new HBox(10 , quantityLabel,quantityTextFeild);
      hb3.setAlignment(Pos.CENTER);
     hb3.setPadding(new Insets(10));
     
     orderDateLabel = new Label("Order Date :");
     orderDateTextField = new TextField();
     HBox hb4 = new HBox(10,orderDateLabel,orderDateTextField);
      hb4.setAlignment(Pos.CENTER);
     hb4.setPadding(new Insets(10));
     order_tableView =new TableView<>();
     listOfClient_tableColumn = new TableColumn<>("Clients ");
     listOfClient_tableColumn.setCellValueFactory(new PropertyValueFactory("user_id"));
     
     listOfProducts_tableColumn = new TableColumn<>("Products ");
     listOfProducts_tableColumn.setCellValueFactory(new PropertyValueFactory("Product_id"));
     
     quantity_tableColumn = new TableColumn<>("Quantity ");
     quantity_tableColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
     
     orderDate_tableColumn = new TableColumn<>("order Date ");
     orderDate_tableColumn.setCellValueFactory(new PropertyValueFactory("date"));
     
     order_tableView.getColumns().addAll(listOfClient_tableColumn,listOfProducts_tableColumn,quantity_tableColumn,orderDate_tableColumn);
     
     
     
     
     
     
     
     addOrder = new Button("Add Order");
     addOrder.setOnAction(eventhandler);
     addOrder.setId("rich-blue");
     reset = new Button("Reset");
     reset.setOnAction(eventhandler);
     reset.setId("rich-blue");
     searchOrder = new Button("Search Order");
     searchOrder.setOnAction(eventhandler);
     searchOrder.setId("rich-blue");
     viewOrder = new Button("View Order");
     viewOrder.setOnAction(eventhandler);
     viewOrder.setId("rich-blue");
     back = new Button("Back");
     back.setOnAction(eventhandler);
     back.setId("rich-blue");
     HBox hbButton = new HBox(10 , addOrder , viewOrder,searchOrder,reset,back);
      hbButton.setAlignment(Pos.CENTER);
     hbButton.setPadding(new Insets(10));
        VBox vbAll = new VBox(10 , allMenues , hb1,hb2,hb3,hb4,order_tableView,hbButton);
        vbAll.setAlignment(Pos.CENTER);
        vbAll.setPadding(new Insets(10));
        
     scene = new Scene(vbAll , 750,700);
     primaryStage.setScene(scene);
scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");
primaryStage.setTitle("manage Orders");
primaryStage.show();

     
    }
    
    
    
            public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==addOrder){
                String client_input = listClient.getValue();
                String product_input = listProducts.getValue();
                String quantity_input = quantityTextFeild.getText();
                String orderDate_input = orderDateTextField.getText();
                
                if(validate_input(client_input)&&validate_input(product_input)
                        &&validate_numric_value(quantity_input)&&validate_input(orderDate_input)){
                    try {
                       
                        String sql3 = "select products.Id,users.Id from products , users where products.Name='"+product_input+"'and users.Name='"+client_input+"'";
                       // String sql2  = "select Id from users where Name ='"+client_input+"'";
                       // ResultSet rs = statement.executeQuery(sql2);
                        ResultSet rs2 = statement.executeQuery(sql3);
                        while(rs2.next()){
                           users user = new users();
                        products product = new products();
                        user.setId(rs2.getInt("Id"));
                        product.setId(rs2.getInt("Id"));
                        String sql = "insert into orders(User_id,Product_id ,Quantity,Date)"
                                +"values('"+user.getId()+"','"+product.getId()+"','"+quantity_input+"','"+orderDate_input+"')";
                        int excuteUpdate = statement.executeUpdate(sql);
                        if(excuteUpdate>0){
                            informationBox("order added successfully", null, "Success");
                            clear();
                        }
                        else{
                            informationBox("order addition failed please try again", null, "Failed");
                            clear();
                        }
                        
                    } }catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
            
            if(event.getSource()==viewOrder){
                try {
                    String sql2 = "select products.id from products";
                    ResultSet rs2 = statement.executeQuery(sql2);
                    while(rs2.next()){
                        products product = new products();
                        product.setId(rs2.getInt("Id"));
                    String sql = "select Product_Id , User_id ,orders.Quantity ,Date from orders where Product_id='"+product.getId()+"'";
                    
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<orders> orders = new ArrayList<>();
                    while(rs.next()){
                        orders order = new orders();
                        product.setId(rs.getInt("Id"));
                        order.setUser_id(rs.getInt("User_id"));
                        order.setProduct_id(rs.getInt("Product_id"));
                        order.setQuantity(rs.getInt("Quantity"));
                        order.setDate(rs.getString("Date"));
                        orders.add(order);
                    }
                    order_tableView.getItems().setAll(orders);
                    
                }} catch (SQLException ex) {
                    System.out.println(ex);
                }
                
                
                   
            }
            if(event.getSource()==reset){
                listClient.setValue("");
                listProducts.setValue("");
                quantityTextFeild.setText("");
                orderDateTextField.setText("");
                
            }
            if(event.getSource()==searchOrder){
                String client_input = listClient.getValue();
               
               if(validate_input(client_input)){
                    try {
                        String sql2 = "select users.Id from users where Name='"+client_input+"'";
                        ResultSet rs = statement.executeQuery(sql2);
                        if(rs.next()){
                            users user = new users();
                            user.setId(rs.getInt("Id"));
                        String sql = "select Product_id ,User_id , Quantity ,Date from orders where User_id='"+user.getId()+"'";
                        ResultSet rs2 = statement.executeQuery(sql);
                        ArrayList<orders> ol = new ArrayList<>();
                        while(rs2.next()){
                            orders order = new orders();
                            order.setUser_id(rs2.getInt("User_id"));
                            order.setProduct_id(rs2.getInt("Product_id"));
                            order.setQuantity(rs2.getInt("Quantity"));
                            order.setDate(rs2.getString("Date"));
                            ol.add(order);
                        }
                        order_tableView.getItems().setAll(ol);
                        } } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                        
                   
               }
            }
            
            
            
            
            
            
            
          
            
               if(event.getSource()==fontColor){
             ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                    //scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    listOfClientLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    listOfProductsLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    orderDateLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    quantityLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    order_tableView.setStyle("-fx-text-fill:" + new_color + ";");

                });
                VBox vbox_Color = new VBox(10, cp, apply_color);
                vbox_Color.setPadding(new Insets(20));

                Scene scene = new Scene(vbox_Color, 200, 100);
                new_stage.setScene(scene);
                new_stage.show();
           }
            if (event.getSource() == backgroundColor) {

                ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                    scene.getRoot().setStyle("-fx-background-color:" + new_color + ";");
                });
                VBox vbox_Color2 = new VBox(10, cp, apply_color);
                vbox_Color2.setPadding(new Insets(20));

                Scene scene2 = new Scene(vbox_Color2, 200, 100);
                new_stage.setScene(scene2);
                new_stage.show();
            }
        }
        }
           private void clear(){
             listClient.setValue("");
                listProducts.setValue("");
                quantityTextFeild.setText("");
                orderDateTextField.setText("");  
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
    private static void informationBox(String infoMessage, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
        
    public static void main(String[] args) {
        launch(args);
    }
    
}
