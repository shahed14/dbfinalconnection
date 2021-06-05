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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class AdminChangePassword extends Application {
    
   MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Label oldPasswordLabel ,newPasswordLabel;
    TextField oldPasswordText , newPasswordText;
    Button reset , change , back;
    Connection connection ;
    Statement statement;
    Scene scene ;
    @Override
    public void start(Stage primaryStage) throws Exception {
      connection = DBconnection.get_connection();
      statement = connection.createStatement();
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
        oldPasswordLabel = new Label("Old Password :");
        oldPasswordText = new TextField();
        HBox hb1 = new HBox(10,oldPasswordLabel,oldPasswordText);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(10));
        newPasswordLabel = new Label("New Password ;");
        newPasswordText = new TextField();
        HBox hb2 = new HBox(10 , newPasswordLabel ,newPasswordText);
        hb2.setAlignment(Pos.CENTER);
        hb2.setPadding(new Insets(10));
        reset = new Button("reset");
        reset.setOnAction(eventhandler);
        reset.setId("rich-blue");
        change = new Button("change");
        change.setOnAction(eventhandler);
        change.setId("rich-blue");
        back = new Button("Back to dashboard");
        back.setOnAction(eventhandler);
        back.setId("rich-blue");
        HBox hb3 = new HBox(15 , change ,reset , back);
        hb3.setAlignment(Pos.CENTER);
        hb3.setPadding(new Insets(10));
        
        VBox vbAll = new VBox(50,allMenues , hb1,hb2,hb3);
          vbAll.setAlignment(Pos.CENTER);
        vbAll.setPadding(new Insets(10));
        
        scene = new Scene(vbAll ,500,350);
        primaryStage.setScene(scene);
      scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

        primaryStage.setTitle("change password");
        
        primaryStage.show();
        
        
        
        
    }
     public class eventHandler implements EventHandler<ActionEvent> {
        
        
        

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==change){
              String old_input = oldPasswordText.getText();
              String new_input = newPasswordText.getText();
              
              if(validate_password(new_input)&&validate_password(old_input)){
                  try {
                      String sql = "select Role from users";
                      ResultSet rs = statement.executeQuery(sql);
                      while(rs.next()){
                        users user = new users();
                         user.setRole(rs.getString("Role"));
                         String role = user.getRole();
                         String a = "Admin";
                         if(role.equalsIgnoreCase(a)){
 String sql2 = "update users set Password ='"+new_input+"'where Password ='"+old_input+"'and Role='"+role+"'";
 int excuteQuery = statement.executeUpdate(sql2);
 if(excuteQuery>0){
                          informationBox("password chanded successfully", null, "success");
                                 
                      }
 else{
     informationBox("change failed,please entre a valid password and try again", null, "failed");
 }   
                      }}
                      /*  try {
                      users user = new users();
                      String sql2= "select Role form users";
                      ResultSet rs = statement.executeQuery(sql2);
                      while(rs.next()){
                      String Role = rs.getString("Role");
                      if(Role.equalsIgnoreCase(Role))
                      {
                      String sql   = "UPDATE users SET Password='"+new_input
                      +"' WHERE Password='"+old_input+"'";
                      int excute = statement.executeUpdate(sql);
                      if(excute>0){
                      informationBox("password changed successfully", null, "success");
                      }
                      else{
                      informationBox("change failed! the password you entred is not exist , please try another password", null, "Failed");
                      }
                      
                      
                      }
                      
                      }   } catch (SQLException ex) {
                      System.out.println(ex);
                      }
                      
                      
                  }*/ } catch (SQLException ex) {
                      System.out.println(ex);
                  }
            }
              else{
                  informationBox("you have entred an invalid password entre another one and try again", null, "Invlid");
              }
            
        
            
             if (event.getSource() == fontColor) {
                ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                    scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    oldPasswordLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    newPasswordLabel.setStyle("-fx-text-fill:" + new_color + ";");
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
            if(event.getSource()==reset){
                oldPasswordText.setText("");
                newPasswordText.setText("");
            }
            if(event.getSource()==back){
                
            }
        }

        }
     }
         private static void informationBox(String infoMessage, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
          private boolean validate_password(String input) {
        if (input.length() < 7) {
            return false;
        }
        else{
        return true;
    }
    }
    
    
    
    
    
    
    

    public static void main(String[] args) {
        launch(args);
    }
    
}
