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
import java.util.Set;
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
public class AdminManageInvoices extends Application {
    MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Button generate , view , search,delete , back , reset;
    TableView<Invoices> invoices_tableView;
    TableColumn<Invoices, String> Date_tableColumn;
    TableColumn<Invoices, Integer> id_tableColumn , order_id_tableColumn ;
   TableColumn<Invoices,Double> totalprice_tableColumn;
    Label idlabel;
    TextField idtext;
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
            di.setContentText("this page is created in order to manage invoices like edit ,delete,genrate or search about invoices");
            di.show();
        });
        helpMenu.getItems().add(aboutApp);
        allMenues.getMenus().addAll(fileMenu, formatMenu, helpMenu);
        invoices_tableView = new TableView<>();
        id_tableColumn = new TableColumn<>("invoice Id");
        id_tableColumn.setCellValueFactory(new PropertyValueFactory("Id"));
        
        order_id_tableColumn  = new TableColumn<>("Order Id");
        order_id_tableColumn.setCellValueFactory(new PropertyValueFactory("Order_id"));
        
         totalprice_tableColumn = new TableColumn<>("Total Price");
        totalprice_tableColumn.setCellValueFactory(new PropertyValueFactory("Total_price"));
        
        Date_tableColumn = new TableColumn<>("Order Date");
        Date_tableColumn.setCellValueFactory(new PropertyValueFactory("Date"));
        
        invoices_tableView.getColumns().addAll(id_tableColumn, order_id_tableColumn, totalprice_tableColumn, Date_tableColumn);
        view = new Button("View");
        view.setId("rich-blue");
        view.setOnAction(eventhandler);
        search = new Button("Search");
        search.setId("rich-blue");
       search.setOnAction(eventhandler);
        delete = new Button("Delete");
        delete.setId("rich-blue");
     delete.setOnAction(eventhandler);
        back = new Button("Back");
        back.setId("rich-blue");
          back.setOnAction(eventhandler);
          reset = new Button("Reset");
                   reset.setId("rich-blue");
              reset.setOnAction(eventhandler);
              generate = new Button("Generate");
              generate.setId("rich-blue");
              generate.setOnAction(eventhandler);
   HBox hb = new HBox(20,view,search,delete,generate,back,reset);
   hb.setAlignment(Pos.CENTER);
   hb.setPadding(new Insets(10));
   idlabel = new Label("Id for search:");
   idtext = new TextField();
   HBox hb2 = new HBox(20,idlabel,idtext);
    hb2.setAlignment(Pos.CENTER);
   hb2.setPadding(new Insets(10));
        VBox vb = new VBox(20,allMenues,hb2,invoices_tableView,hb);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        scene = new Scene(vb);
        primaryStage.setScene(scene);
                scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

        primaryStage.setTitle("manage invoices");
        primaryStage.show();

    }

    public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==view){
                
                Invoices invoice2 = invoices_tableView.getSelectionModel().getSelectedItem();
                 try {
                    String sql = "select * from invoices";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<Invoices> invoices_list = new ArrayList<>();
                    while (rs.next()) {
                        Invoices invoice = new Invoices();
                        invoice.setId(rs.getInt("Id"));
                        invoice.setOrder_id(rs.getInt("Order_id"));
                        invoice.setTotalPrice(rs.getInt("Total_price"));
                        invoice.setDate(rs.getString("Date"));
                        invoices_list.add(invoice);

                    }
                    invoices_tableView.getItems().setAll(invoices_list);
                   

                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            if(event.getSource()==generate){
                try {
                    String sql = "select Total_price, Quantity from orders";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                            orders order = new orders();
                     Invoices invoice = new Invoices();
                            order.setQuantity(rs.getInt("Quantity"));
                            invoice.setTotalPrice(rs.getInt("Total_price"));
                      String sql2 = "update invoices set Total_price='"+invoice.getTotalPrice()+"'*'"+order.getQuantity()+"'";
                      int excuteUpdate = statement.executeUpdate(sql2);
                      if(excuteUpdate>0){
                          System.out.println("affected rows = "+ excuteUpdate);
                      }
                } }catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            if(event.getSource()==delete){
                try {
                    Invoices invoice = invoices_tableView.getSelectionModel().getSelectedItem();
                    String sql2 = "select Id from invoices";
                    ResultSet rs = statement.executeQuery(sql2);
                    while (rs.next()){
                        invoice.setId(rs.getInt("Id"));
                    String sql = "delete from invoices where Id='"+invoice.getId()+"'";
                    int excuteUpdate = statement.executeUpdate(sql);
                    if(excuteUpdate>0){
                        informationBox("invoice deleted successfully", null, "Success");
                    }
                    else{
                        informationBox("invoice deletion failed", null, "failed");
                    }
                } }catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            if(event.getSource()==reset){
                invoices_tableView.getItems().clear();
            }
            if(event.getSource()==back){
                
            }
            if(event.getSource()==search){
                try {
                    String id = idtext.getText();
                  //  Invoices invoice = invoices_tableView.getSelectionModel().getSelectedItem();
                    //String sql = "Select Order_id from invoices";
                    //ResultSet rs = statement.executeQuery(sql);
                  //  while(rs.next()){
                      //  invoice.setOrder_id(rs.getInt("Order_id"));
                        String sql2 = "select Id ,order_id ,Total_price ,Date from invoices where Order_id='"+id+"'";
                        ResultSet rs2 = statement.executeQuery(sql2);
                       ArrayList<Invoices> invoices_list  = new ArrayList<>();
                        while(rs2.next()){
                            Invoices invoice2 = new Invoices();
                            invoice2.setId(rs2.getInt("Id"));
                            invoice2.setOrder_id(rs2.getInt("Order_id"));
                            invoice2.setTotalPrice(rs2.getInt("Total_price"));
                            invoice2.setDate(rs2.getString("Date"));
                            invoices_list.add(invoice2);
                          //  System.out.println(invoice.getId());
                        }
                        invoices_tableView.getItems().setAll(invoices_list);
                }
                 catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            
            
             if(event.getSource()==fontColor){
             ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                  //  scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    invoices_tableView.setStyle("-fx-text-fill:" + new_color + ";");
                    //newPasswordLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    //orderDateLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    //product_table.setStyle("-fx-text-fill:" + new_color + ";");

                });
                VBox vbox_Color = new VBox(10, cp, apply_color);
                vbox_Color.setPadding(new Insets(20));

                Scene scene = new Scene(vbox_Color, 300, 200);
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

                Scene scene2 = new Scene(vbox_Color2, 300, 200);
                new_stage.setScene(scene2);
                new_stage.show();

        }
        }

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
