/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;

import java.io.IOException;
import java.sql.Connection;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author HP
 */
public class Register extends Stage {
    Label nameLabel , emailLabel , mobileLabel , passwordLabel;
    TextField nameText , emailText , mobileText , passwordText;
    Button register , reset;
     MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Connection connection;
    Statement statement;
    Scene scene;
    public Register(){
        try {
            connection = DBconnection.get_connection();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
      eventHandler eventhandler = new eventHandler();
      allMenues = new MenuBar();
        fileMenu = new Menu("File");
        exit = new MenuItem("exit");
        exit.setOnAction(e -> {
         Register.this.close();
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
            di.setContentText("this page is created in order to register to the database");
            di.show();
        });
        helpMenu.getItems().add(aboutApp);
        allMenues.getMenus().addAll(fileMenu, formatMenu, helpMenu);
        nameLabel = new Label("Name :");
        nameText = new TextField();
        HBox hb1 = new HBox(15,nameLabel,nameText);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(15));
         emailLabel = new Label("Email :");
        emailText = new TextField();
        HBox hb2 = new HBox(15,emailLabel,emailText);
        hb2.setAlignment(Pos.CENTER);
        hb2.setPadding(new Insets(15));
         mobileLabel = new Label("Mobile :");
        mobileText = new TextField();
        HBox hb3 = new HBox(15,mobileLabel,mobileText);
        hb3.setAlignment(Pos.CENTER);
        hb3.setPadding(new Insets(15));
         passwordLabel = new Label("Password :");
        passwordText = new TextField();
        HBox hb4 = new HBox(15,passwordLabel,passwordText);
        hb4.setAlignment(Pos.CENTER);
        hb4.setPadding(new Insets(15));
        register = new Button("Register");
        register.setId("rich-blue");
        register.setOnAction(eventhandler);
        reset = new Button("Reset");
        reset.setOnAction(eventhandler);
        reset.setId("rich-blue");
        HBox hbButton = new HBox(20,register,reset);
        hbButton.setAlignment(Pos.CENTER);
        hbButton.setPadding(new Insets(15));
        VBox vb = new VBox(15,allMenues,hb1,hb2,hb3,hb4,hbButton);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10));
        scene = new Scene(vb ,600,400 );
       setScene(scene);
        scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");
      setTitle("Registration screen");
    }

   
    
         public class eventHandler implements EventHandler<ActionEvent> {

        @Override
         public void handle(ActionEvent event) {
             if(event.getSource()==register){
                      Window owner = register.getScene().getWindow();

                String name_input = nameText.getText();
                String email_input = emailText.getText();
                String password_input = passwordText.getText();
                String mobile_input = mobileText.getText();

                if (name_input.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, owner, "register Error!",
                            "Please enter your name ");
                    return;
                }
                if (email_input.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, owner, "register Error!", "pleae entre your email");
                    return;
                }
                if (mobile_input.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, owner, "register Error!", "please entre your mobile");
                    return;
                }
                if (password_input.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, owner, "register Error!", "please entre your password");
                    return;
                }

                if (validate_input(name_input) && validate_input(email_input)
                        && validate_password(password_input) && validate_input(mobile_input)) {
                    try {
                        String sql = "insert into users(Name,Email,Mobile,Password)"
                                + "values('" + name_input + "','" + email_input + "','" + mobile_input + "','" + password_input + "')";
                        int excuteUpdate = statement.executeUpdate(sql);
                        if (excuteUpdate > 0) {
                            infoBox("client added successfully", null, "Success");
                            clear();
                            
                        } else {
                            infoBox("addition failed,please try again", null, "Failed");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
             if(event.getSource()==reset){
                 nameText.setText("");
                 emailText.setText("");
                 mobileText.setText("");
                 passwordText.setText("");

             }

            if (event.getSource() == fontColor) {
                ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                    // scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    nameLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    emailLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    mobileLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    passwordLabel.setStyle("-fx-text-fill:" + new_color + ";");

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
              nameText.setText("");
                 emailText.setText("");
                 mobileText.setText("");
                 passwordText.setText("");
         }

    private boolean validate_input(String input) {
        return !input.equals("");
    }

    private boolean validate_numeric_val(String input) {
        if (validate_input(input)) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private boolean validate_password(String input) {
        if (input.length() < 7) {
            return false;
        }
        else{
        return true;
    }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

  

}
