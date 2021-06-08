/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;

import java.sql.Connection;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class AdminDashborad extends Stage {
    Button manageorder , manageinvice , manageclient ,manageproduct,changepassword,logout;
    MenuBar allMenues;
     Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
   // Connection connection;
   // Statement statement;
   // Scene scene;
  public AdminDashborad(){
        Text text = new Text("Admin Dashboard");
        text.setId("title");
        text.setStyle("-fx-font-size:30");
        // eventHandler eventhandler = new eventHandler();
    /*  allMenues = new MenuBar();
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
*/
     
       manageclient = new Button("Manage Client");
       manageclient.setId("record-sales");
      // manageclient.setCursor(Cursor.HAND);
       AdminManageClients amc = new AdminManageClients();
       manageclient.setOnAction(e->{
          amc.show();
       });
       manageorder = new Button("Manage Orders");
       manageorder.setId("record-sales");
       AdminManageOrders amo = new AdminManageOrders();
       manageorder.setOnAction(e->{
           amo.show();
       });
       manageinvice = new Button("Manage Invoices");
       manageinvice.setId("record-sales");
       AdminManageInvoices ami = new AdminManageInvoices();
       manageinvice.setOnAction(e->{
           ami.show();
       });
       manageproduct = new Button("Manage Products");
       manageproduct.setId("record-sales");
       AdminManageProducts amp = new AdminManageProducts();
       manageproduct.setOnAction(e->{
           amp.show();
       });
       changepassword = new Button("Change Password");
       AdminChangePassword acp = new AdminChangePassword();
       changepassword.setOnAction(e->{
           acp.show();
       });
       changepassword.setId("record-sales");
       logout = new Button("Logout");
       logout.setId("record-sales");
        HBox hb1 = new HBox(20,manageclient,manageproduct,manageorder);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(30));
            HBox hb2 = new HBox(20,manageinvice,changepassword,logout);
            hb2.setAlignment(Pos.CENTER);
          hb2.setPadding(new Insets(30));
          
          VBox vb = new VBox(35,text,hb1,hb2);
          vb.setAlignment(Pos.CENTER);
       Scene scene = new Scene(vb ,700,400);
        
        setTitle("Admin Dashboard");
        setScene(scene);
                scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

    }
   /*  public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
              if (event.getSource() == fontColor) {
                ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                   // text.setStyle("-fx-fill:"+new_color+";");
                    

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
                   // scene.getRoot().setStyle("-fx-background-color:" + new_color + ";");
                });
                VBox vbox_Color2 = new VBox(10, cp, apply_color);
                vbox_Color2.setPadding(new Insets(20));

                Scene scene2 = new Scene(vbox_Color2, 200, 100);
                new_stage.setScene(scene2);
                new_stage.show();
            }
        }
         
     }
   /*  public static void openNewStage(Class myClass,String to,String title) {
        Stage stage=new Stage();
        AdminChangePassword a = new AdminChangePassword();
        Scene scene=new Scene(getClass().getResource("AdminChangePassword"));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();*/

    
     
   
    
}
