/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class LoginPage extends Application{
     MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Label userNameLabel , passwordLabel;
    TextField userNameText , passwordText;
    Button login , register , reset;
     Connection connection;
    Statement statement;
    Scene scene;

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
            di.setContentText("this page is created in order to login to dashboard or register if you dont login");
            di.show();
        });
        helpMenu.getItems().add(aboutApp);
        allMenues.getMenus().addAll(fileMenu, formatMenu, helpMenu);
      userNameLabel = new Label("userName :");
      userNameText = new TextField();
        HBox hb1 = new HBox(15,userNameLabel,userNameText);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(15));
        passwordLabel = new Label("Password");
        passwordText = new TextField();
        HBox hb2 = new HBox(15,passwordLabel,passwordText);
        hb2.setAlignment(Pos.CENTER);
        hb2.setPadding(new Insets(15));
        login = new Button("Login");
        login.setId("rich-blue");
        login.setOnAction(eventhandler);
        register = new Button("Register");
        register.setOnAction(eventhandler);
        register.setId("rich-blue");
        reset = new  Button("Reset");
        reset.setId("rich-blue");
        reset.setOnAction(eventhandler);
        HBox hb3 = new HBox(15,login,register,reset);
        hb3.setAlignment(Pos.CENTER);
        hb3.setPadding(new Insets(15));
        VBox vb = new VBox(20,allMenues,hb1,hb2,hb3);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(15));
        scene = new Scene(vb ,500,300);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");
        primaryStage.setTitle("login screen");
       primaryStage.show();
    }
    
    
                public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource()==login){
                String userName_input = userNameText.getText();
                String Password_input = passwordText.getText();
                
                if(validate_input(userName_input)&&validate_input(Password_input)){
                    try {
                        String sql = "select Role from users where Email ='"+userName_input
                                +"'and Password='"+Password_input+"'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()){
                         String Role = rs.getString("Role");
                         if(Role.equalsIgnoreCase("Role")){
                             System.out.println("hye "+Role);
                             
                         }
                         else{
                             System.out.println("hye"+Role);
                         }
                        }
                        
                        
                        
                        
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
            if(event.getSource()==reset){
                userNameText.setText("");
                passwordText.setText("");
            }
            if(event.getSource()==register){
                System.out.println("register now my baby but i cant take you there!!");
                Stage newstage = new Stage();
                Register r = new Register();
               // Scene scene2 = new Scene(r.start(newstage);
                newstage.setScene(scene);
                newstage.show();
            }
            
            
            
            
            
            
            
            
            
        
   if(event.getSource()==fontColor){
             ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                    //scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    userNameLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    passwordLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    login.setStyle("-fx-text-fill:" + new_color + ";");
                    register.setStyle("-fx-text-fill:" + new_color + ";");
                    reset.setStyle("-fx-text-fill:" + new_color + ";");

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
    

