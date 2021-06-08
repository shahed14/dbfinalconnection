/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author HP
 */
public class ClientProfile extends Stage {
   Button viewProfile , editProfile , backtoDashboard ; 
    TextField nameTextFeild , emailTextFeild , mobileTextFeild;
    Label nameLabel , emailLabel , mobileLabel;
    TableView<users> clientsTableView;
    TableColumn<users, String> nameTableColumn;
    TableColumn<users, String> emailTableColumn;
    TableColumn<users, String> mobileTableColumn;
    MenuBar allMenues;
    Menu fileMenu , formatMenu , helpMenu , fontSize ,fontfamily;
    MenuItem exit  , fontColor  , backgroundColor , aboutApp;
    RadioMenuItem small,medium,large,arial,sans_serif , salmon , royalblue ;
    ToggleGroup fontSizeToggle , fontFamilyToggle;
   Connection conn = null;
    Statement statement = null;
    public Scene scene;
    
    
    
   public ClientProfile(){
       try {
            conn = DBconnection.get_connection();
            statement = conn.createStatement();
           
       } catch (Exception ex) {
           System.out.println(ex);
       }
       eventHandler eventhandler = new eventHandler();
       allMenues = new MenuBar();
       fileMenu = new Menu("File");
       exit = new MenuItem("exit");
       exit.setOnAction(e->{
       ClientProfile.this.close();
       });
       fileMenu.getItems().add(exit);
       formatMenu = new Menu("format");
       fontSize = new Menu("font size");
       fontSizeToggle = new ToggleGroup();
       small = new RadioMenuItem("small");
       small.setOnAction(e->{
           scene.getRoot().setStyle("-fx-font-size:small");
       });
      small.setToggleGroup(fontSizeToggle);
       medium = new RadioMenuItem("medium");
       medium.setOnAction(e->{
           scene.getRoot().setStyle("-fx-font-size:medium");
       });
       medium.setToggleGroup(fontSizeToggle);
       large = new RadioMenuItem("large");
       large.setOnAction(e->{
           scene.getRoot().setStyle("-fx-font-size:large");
       });
       large.setToggleGroup(fontSizeToggle);
       fontSize.getItems().addAll(small ,medium ,large);
       fontColor = new MenuItem("font color");
       fontColor.setOnAction(eventhandler);
       
       
       fontfamily = new Menu("font family");
       fontFamilyToggle = new ToggleGroup();
      arial = new RadioMenuItem("arial");

        arial.setOnAction(e->{
      scene.getRoot().setStyle("-fx-font-family:arial;");
        });
        arial.setToggleGroup(fontFamilyToggle);
       
        sans_serif = new RadioMenuItem("sans_serif");
        sans_serif.setToggleGroup(fontFamilyToggle);
        sans_serif.setOnAction(e->{
    scene.getRoot().setStyle("-fx-font-family:sans_serif;");
        });
       fontfamily.getItems().addAll(arial , sans_serif);
       backgroundColor = new MenuItem("backgraound color");
       backgroundColor.setOnAction(eventhandler);
       formatMenu.getItems().addAll(fontSize , new SeparatorMenuItem() , fontColor , new SeparatorMenuItem(), fontfamily , new SeparatorMenuItem()
       , backgroundColor);
       helpMenu = new Menu("Help");
       aboutApp = new MenuItem("about app");
       aboutApp.setOnAction(e->{
            Dialog<String> di = new Dialog<>();
               ButtonType ok = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
               di.setHeaderText("About app");
               di.getDialogPane().getButtonTypes().add(ok );
         di.setContentText("this page is created in order to edit or view information about the clients");
                di.show();
       });
       helpMenu.getItems().add(aboutApp);
       allMenues.getMenus().addAll(fileMenu ,  formatMenu  , helpMenu);
       
       nameLabel = new Label("Name :");
       nameTextFeild = new TextField();
        HBox hb1 = new HBox(10,nameLabel,nameTextFeild);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(10));
       emailLabel = new Label("Email :");
       emailTextFeild = new TextField();
       HBox hb2 = new HBox(10,emailLabel,emailTextFeild);
       hb2.setAlignment(Pos.CENTER);
       hb2.setPadding(new Insets(10));
       mobileLabel = new Label("Mobile :");
       mobileTextFeild = new TextField();
       HBox hb3 = new HBox(10,mobileLabel , mobileTextFeild);
       hb3.setAlignment(Pos.CENTER);
       hb3.setPadding(new Insets(10));
       viewProfile = new Button("view");
       viewProfile.setOnAction(eventhandler);
       viewProfile.setId("rich-blue");
       editProfile = new Button("edit");
       editProfile.setOnAction(eventhandler);
              editProfile.setId("rich-blue");

       backtoDashboard = new Button("back");
       backtoDashboard.setId("rich-blue");
       backtoDashboard.setOnAction(eventhandler);
       HBox hb4 = new HBox(15,viewProfile,editProfile,backtoDashboard);
       hb4.setAlignment(Pos.CENTER);
       hb4.setPadding(new Insets(10));
        clientsTableView = new TableView<users>();
        nameTableColumn = new TableColumn("Name");
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        nameTableColumn.setCellFactory(TextFieldTableCell.<users>forTableColumn());
        nameTableColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });
        emailTableColumn = new TableColumn("Email");
        emailTableColumn.setCellValueFactory(new PropertyValueFactory("email"));
        emailTableColumn.setCellFactory(TextFieldTableCell.<users>forTableColumn());
        emailTableColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });

        mobileTableColumn = new TableColumn("Mobile");
        mobileTableColumn.setCellValueFactory(new PropertyValueFactory("mobile"));

        mobileTableColumn.setCellFactory(TextFieldTableCell.<users>forTableColumn());

        mobileTableColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setMobile(e.getNewValue());
        });
       
       clientsTableView.getColumns().addAll(nameTableColumn,emailTableColumn,mobileTableColumn);
       
      
        VBox vb = new VBox(20 ,allMenues,hb1,hb2,hb3,clientsTableView,hb4);
       
        scene = new Scene(vb,400,500);
       setScene(scene);
       scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

       
    }
        public class eventHandler implements EventHandler<ActionEvent>{

 @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==viewProfile){
                try {
                    String Role = "Client";
                    String sql = "select Name , Email , Mobile from users where Role='"+Role+"'";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<users> client_list = new ArrayList<>();
                    while(rs.next()){
                        users user = new users();
                        user.setName(rs.getString("Name"));
                        user.setEmail(rs.getString("Email"));
                        user.setMobile(rs.getString("Mobile"));
                        client_list.add(user);
                        
                    }
                    clientsTableView.getItems().setAll(client_list);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                
            }
            if(event.getSource()==editProfile){
                        Window box = editProfile.getScene().getWindow();
                        String name_input = nameTextFeild.getText();
                        String email_input = emailTextFeild.getText();
                        String mobile_input = mobileTextFeild.getText();
                        if(validate_input(name_input)&& validate_input(email_input)&& validate_input(mobile_input)){
                try {
                    
                    users user = clientsTableView.getSelectionModel().getSelectedItem();
                    String sql = "update users set Name ='" + name_input+ "',Email = '" + email_input
                            + "',Mobile ='" + mobile_input+  "' where Name ='" + user.getName() + "'";
                    int excuteUpdate = statement.executeUpdate(sql);
                    if(excuteUpdate>0){
                        informationBox("user updated successfully", null, "success");
                    }
                    else{
                        informationBox("update failed! please try again", null, "failed");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            }
            
          if(event.getSource()==backtoDashboard){
             ClientDashboard cd = new ClientDashboard();
             ClientProfile.this.close();
             cd.show();
              
              
          }  
            
            
            
            
            
          if(event.getSource()==fontColor){
               ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                 // scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                  nameLabel.setStyle("-fx-text-fill:"+new_color+";");
                                    emailLabel.setStyle("-fx-text-fill:"+new_color+";");
                  mobileLabel.setStyle("-fx-text-fill:"+new_color+";");
                  clientsTableView.setStyle("-fx-text-fill:"+new_color+";");
                nameTableColumn.setStyle("-fx-text-fill:"+new_color+";");
                  emailTableColumn.setStyle("-fx-text-fill:"+new_color+";");
                  mobileTableColumn.setStyle("-fx-text-fill:"+new_color+";");
               allMenues.setStyle("-fx-text-fill:"+new_color+";");



        });
           VBox vbox_Color = new VBox(10 , cp ,apply_color);
                vbox_Color.setPadding(new Insets(20));
                
                Scene scene = new Scene(vbox_Color,200,100);
                new_stage.setScene(scene);
                new_stage.show();
        }
           if(event.getSource()==backgroundColor){
            
               ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                  scene.getRoot().setStyle("-fx-background-color:"+new_color+";");  
        });
           VBox vbox_Color2 = new VBox(10 , cp ,apply_color);
                vbox_Color2.setPadding(new Insets(20));
                
                Scene scene2 = new Scene(vbox_Color2,200,100);
                new_stage.setScene(scene2);
                new_stage.show();
          }
        }
        
        }
         private static void informationBox(String infoMessage , String header , String title){
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setContentText(infoMessage);
       alert.setTitle(title);
       alert.setHeaderText(header);
       alert.showAndWait();
   }
         private boolean validate_input(String input){
             return !input.equals("");
         }
            
        
  

      
    
}
