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
public class AdminManageClients extends Application {
     MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    TableView<users> client_tableView;
    TableColumn<users, String> name_tableColumn , email_tableColumn , Mobile_tableColumn , role_tableColumn , Password_tableColumn ;
    TableColumn<users, Integer> id_tableColumn;
    Button view , deleteById , searchByName , refresh,back;
    Label idLabel , nameLabel;
    TextField idText , nameText;
    Connection connection ;
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
            di.setContentText("this page is created in order to change your old password to a new password");
            di.show();
        });
        helpMenu.getItems().add(aboutApp);
        allMenues.getMenus().addAll(fileMenu, formatMenu, helpMenu);
        idLabel = new Label("id for delete :");
        idText = new TextField();
        HBox hb1 = new HBox(10,idLabel,idText);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(10));
         nameLabel = new Label("name for search :");
        nameText = new TextField();
        HBox hb12 = new HBox(10,nameLabel,nameText);
        hb12.setAlignment(Pos.CENTER);
        hb12.setPadding(new Insets(10));
        client_tableView = new TableView<>();
        id_tableColumn = new TableColumn<>("Id");
        id_tableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        name_tableColumn = new TableColumn<>("Name");
        name_tableColumn.setCellValueFactory(new PropertyValueFactory("name"));
         email_tableColumn = new TableColumn<>("Email");
        email_tableColumn.setCellValueFactory(new PropertyValueFactory("email"));
         Mobile_tableColumn = new TableColumn<>("Mobile");
        Mobile_tableColumn.setCellValueFactory(new PropertyValueFactory("mobile"));
        Password_tableColumn = new TableColumn<>("Password");
        Password_tableColumn.setCellValueFactory(new PropertyValueFactory("password")); 
        role_tableColumn = new TableColumn<>("Role");
        role_tableColumn.setCellValueFactory(new PropertyValueFactory("role"));
        client_tableView.getColumns().addAll(id_tableColumn,name_tableColumn
         ,email_tableColumn,Mobile_tableColumn,Password_tableColumn,role_tableColumn);
       view = new Button("View Clients");
       view.setId("rich-blue");
       view.setOnAction(eventhandler);
       deleteById = new Button("Delete");
       deleteById.setId("rich-blue");
       deleteById.setOnAction(eventhandler);
       searchByName = new Button("Search");
       searchByName.setId("rich-blue");
       searchByName.setOnAction(eventhandler);
       refresh = new Button("Refresh");
       refresh.setId("rich-blue");
       refresh.setOnAction(eventhandler);
       back = new Button("Back");
       back.setOnAction(eventhandler);
       back.setId("rich-blue");
        HBox hbButtons = new HBox(20 , view,deleteById,searchByName,refresh,back);
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setPadding(new Insets(20));
        VBox vb = new VBox(20,allMenues,hb1,hb12,client_tableView,hbButtons);
        scene = new Scene(vb);
        primaryStage.setScene(scene);
        primaryStage.setTitle("manage Orders");
        scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");
           
        primaryStage.show();
    }

                public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==view){
                 try {
                     users user = new users();
                     String sql1 = "Select Role from users where Role ='Client'";
                     ResultSet rs2 = statement.executeQuery(sql1);
                     while(rs2.next()){
                    String sql = "select Id, Name , Email , Mobile,Password ,Role from users where Role='"+rs2.getString("Role")+"'";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<users> client_list = new ArrayList<>();
                    while (rs.next()) {
                        users user2 = new users();
                      user2.setId(rs.getInt("Id"));
                      user2.setName(rs.getString("Name"));
                      user2.setEmail(rs.getString("Email"));
                      user2.setMobile(rs.getString("Mobile"));
                      user2.setPassword(rs.getString("Password"));
                      user2.setRole(rs.getString("Role"));
                        client_list.add(user2);

                    }
                    client_tableView.getItems().setAll(client_list);
                }} catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            
            if(event.getSource()==deleteById){
               //  users user = client_tableView.getSelectionModel().getSelectedItem();
                   String id_input = idText.getText();
                if (validate_input(id_input)) {
                     
                     try {
                         String sql = "delete from users where Id ='" + id_input+ "'";
                         int excute = statement.executeUpdate(sql);
                         System.out.println("affected rows" + excute);
                         if(excute>0){
                             informationBox("user deleted successfully", null, "success");
                         }
                         else{
                             informationBox("deletion failed,please try again", null, "failed"); 
                         }
                     } catch (SQLException ex) {
                         System.out.println(ex);
                     }
                         }
                     
            }
            if(event.getSource()==searchByName){
                
                try {
                    String name_input = nameText.getText();
                    String sql = "select Id,  Name , Email , Mobile,Password ,Role"
                            + " from users where Name ='" + name_input+ "'";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<users>client_user = new ArrayList<>();
                    while (rs.next()) {
                        users user = new users();
                        user.setId(rs.getInt("Id"));
                        user.setName(rs.getString("Name"));
                        user.setEmail(rs.getString("Email"));
                        user.setMobile(rs.getString("Mobile"));
                        user.setPassword(rs.getString("Password"));
                        user.setRole(rs.getString("Role"));
                        client_user.add(user);
                        
                    }
                    client_tableView.getItems().setAll(client_user);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            if(event.getSource()==refresh){
                idText.setText("");
                idText.setText("");
               client_tableView.getItems().clear();
            }
            
            if(event.getSource()==back){
                
            }
              if(event.getSource()==fontColor){
             ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                  //  scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    client_tableView.setStyle("-fx-text-fill:" + new_color + ";");
                    //newPasswordLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    //orderDateLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    //product_table.setStyle("-fx-text-fill:" + new_color + ";");

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
