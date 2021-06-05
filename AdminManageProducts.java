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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AdminManageProducts extends Application {
    MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Label nameLabel , categoryLabel , priceLabel , quantityLabel , descriptionLabel;
    TextField nameTextFeild , priceTextFeild , quantityTextFeild;
    ComboBox<String>categoryLisr;
    TextArea descriptionTextArea;
    Button addProduct , reset , editProduct , searchForProduct , deleteProduct , viewProduct ,backTODashboard;
    TableView<products> products_tableView;
    TableColumn<products, String> name_tableColumn , category_tableColumn , description_tableColumn;
    TableColumn<products, Integer> price_tableColumn , quantity_tableColumn;
    Connection connection;
    Statement statement ;
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
         nameLabel = new Label("Name ;");
         nameTextFeild = new TextField();
         HBox hb1 = new HBox(10,nameLabel,nameTextFeild);
         hb1.setAlignment(Pos.CENTER);
         hb1.setPadding(new Insets(10));
         categoryLabel = new Label("Category :");
         String[] categoryArray = new String[]{"electical" , "home equipment" ,"wood" ,"blastic","for kidz"};
           ObservableList<String> observableList = FXCollections.observableArrayList(categoryArray);
      categoryLisr = new ComboBox<>(observableList);
      categoryLisr.setEditable(true);
      HBox hb2 = new HBox(10 ,categoryLabel,categoryLisr);
      hb2.setAlignment(Pos.CENTER);
         hb2.setPadding(new Insets(10));
      priceLabel = new Label("Price :");
      priceTextFeild = new TextField();
      HBox hb3 = new HBox(10 , priceLabel,priceTextFeild);
      hb3.setAlignment(Pos.CENTER);
         hb3.setPadding(new Insets(10));
      quantityLabel = new Label("Quantity :");
      quantityTextFeild = new TextField();
      HBox hb4 = new HBox(10,quantityLabel,quantityTextFeild);
      hb4.setAlignment(Pos.CENTER);
         hb4.setPadding(new Insets(10));
      descriptionLabel = new Label("Description :");
      descriptionTextArea = new TextArea();
      HBox hb5 = new HBox(10,descriptionLabel ,descriptionTextArea);
      hb5.setAlignment(Pos.CENTER);
         hb5.setPadding(new Insets(10));
        addProduct = new Button("Add Product");
        addProduct.setId("rich-blue");
        addProduct.setOnAction(eventhandler);
        editProduct = new Button("Edit Product");
        editProduct.setId("rich-blue");
     editProduct.setOnAction(eventhandler);
        reset = new Button("Reset");
        reset.setId("rich-blue");
        reset.setOnAction(eventhandler);
   reset.setOnAction(eventhandler);
        deleteProduct = new Button("Delete Product");
        deleteProduct.setId("rich-blue");
deleteProduct.setOnAction(eventhandler);
        searchForProduct = new Button("Search");
        searchForProduct.setId("rich-blue");
searchForProduct.setOnAction(eventhandler);
        viewProduct = new Button("View Product");
        viewProduct.setId("rich-blue");
viewProduct.setOnAction(eventhandler);
        backTODashboard = new Button("Back to dashboard");
        backTODashboard.setId("rich-blue");
backTODashboard.setOnAction(eventhandler);
     HBox hbButton1 = new HBox(10,addProduct , editProduct ,deleteProduct);
     hbButton1.setAlignment(Pos.CENTER);
         hbButton1.setPadding(new Insets(10));
         HBox hbButton2 = new HBox(10,searchForProduct,viewProduct ,reset ,backTODashboard);
         hbButton2.setAlignment(Pos.CENTER);
         hbButton2.setPadding(new Insets(10));
         products_tableView = new TableView<>();
         products_tableView.autosize();
         name_tableColumn = new TableColumn<>("Product Name");
         name_tableColumn.setCellValueFactory(new PropertyValueFactory("name"));
         category_tableColumn = new TableColumn<>("Category");
         category_tableColumn.setCellValueFactory(new PropertyValueFactory("category"));
         price_tableColumn = new TableColumn<>("Product Price");
         price_tableColumn.setCellValueFactory(new PropertyValueFactory("price"));
         quantity_tableColumn = new TableColumn<>("Product Quantity");
         quantity_tableColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
         description_tableColumn = new TableColumn<>("Description");
         description_tableColumn.setCellValueFactory(new PropertyValueFactory("description"));
         products_tableView.getColumns().addAll(name_tableColumn,category_tableColumn,price_tableColumn,quantity_tableColumn,description_tableColumn);
         VBox vbAll = new VBox(10 ,allMenues , hb1,hb2,hb3,hb4,hb5,hbButton1,products_tableView,hbButton2);
         vbAll.setAlignment(Pos.CENTER);
         vbAll.setPadding(new Insets(10));
         
         scene = new Scene(vbAll ,700,600);
         primaryStage.setScene(scene );
               scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

         primaryStage.setTitle("manage products");
         primaryStage.show();
         
         
         
         
    }
        public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            if(event.getSource()==addProduct){
                String name_input = nameTextFeild.getText();
                String category_input = categoryLisr.getValue().toString();
                String price_input = priceTextFeild.getText();
                String quantity_input = quantityTextFeild.getText();
                String desription_input = descriptionTextArea.getText();
                
                if(validate_input(name_input)&&validate_input(category_input)&&validate_numric_value(price_input)
                        &&validate_numric_value(quantity_input)&&validate_input(desription_input)){
                    try {
                        String sql = "insert into products ( Name , Category , Price,Quantity ,Description)"
                                + "values('" + name_input + "','" + category_input + "','" + price_input
                                +  "','"+quantity_input+"','"+desription_input+"')";
                        int excuteUpdate = statement.executeUpdate(sql);
                        if(excuteUpdate >0){
                            informationBox("product added successfully", null, "success");
                        }
                        else{
                            informationBox("addition failed , check your answer and try again", null, "failed");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
            if(event.getSource()==editProduct){
                try {
                    products product = products_tableView.getSelectionModel().getSelectedItem();
                     String name_input = nameTextFeild.getText();
                String category_input = categoryLisr.getValue().toString();
                String price_input = priceTextFeild.getText();
                String quantity_input = quantityTextFeild.getText();
                String desription_input = descriptionTextArea.getText();
                    String sql = "update products set Name ='" + name_input+ "',Category = '" + category_input
                            + "',Price ='" + price_input
                            + "',Quantity  ='" + quantity_input
                            + "',Description ='" + desription_input+ "'";
                    int excuteUpdate = statement.executeUpdate(sql);
                    if(excuteUpdate>0){
                        informationBox("product updateed successfully", null, "Success");
                    }
                    else{
                        informationBox("updat Failed,please try again", null, "Failed");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            if(event.getSource()==deleteProduct){
                 products product = products_tableView.getSelectionModel().getSelectedItem();
                   
                if (product != null) {
                     try {
                         String sql2  = "select Id from products";
                         ResultSet rs = statement.executeQuery(sql2);
                         while(rs.next()){
                             String sql = "delete from products where Id ='" + rs.getInt("Id")+ "'";
                        int excute = statement.executeUpdate(sql);
                        System.out.println("affected rows" + excute);
                        if(excute>0){
                            informationBox("product deleted successfully", null, "success");
                        }
                        else{
                            informationBox("deletion failed,please try again", null, "failed");
                        } 
                         }
                     } catch (SQLException ex) {
                         System.out.println(ex);
                     }
                }
            
               
                }

            
            if(event.getSource()==viewProduct){
                 try {
                    String sql = "select  Name , Category , Price,Quantity ,Description from products";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<products> products_list = new ArrayList<>();
                    while (rs.next()) {
                        products product = new products();
                      product.setName(rs.getString("Name"));
                      product.setCategory(rs.getString("Category"));
                      product.setPrice(rs.getInt("Price"));
                      product.setQuantity(rs.getInt("Quantity"));
                      product.setDescription(rs.getString("Description"));
                        products_list.add(product);

                    }
                    products_tableView.getItems().setAll(products_list);
                     informationBox("we found what you want", null, "success");
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            else{
                informationBox("on exist to what you want", null, "failed");
            }
            if(event.getSource()==searchForProduct){
                try {
                    String category_input = categoryLisr.getValue();
                    
                    String sql = "select   Name , Category , Price,Quantity ,Description"
                            + " from products where Category ='" + category_input+ "'";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<products>product_search = new ArrayList<>();
                    while (rs.next()) {
                        products product2 = new products();

                        product2.setName(rs.getString("Name"));
                        product2.setCategory(rs.getString("Category"));
                        product2.setPrice(rs.getInt("Price"));
                        product2.setQuantity(rs.getInt("Quantity"));
                        product2.setDescription(rs.getString("Description"));
                        product_search.add(product2);
                        
                    }
                    products_tableView.getItems().setAll(product_search);
                   /* Dialog<products> dialog = new Dialog<>();
                    ButtonType ok = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
                    dialog.setHeaderText(" search success message");

                    dialog.setTitle(" Message");
                    dialog.getDialogPane().getButtonTypes().addAll(ok);
                    dialog.setContentText(product_search + " ");
                    dialog.show();*/
                

                } catch (SQLException ex) {
                    System.out.println(ex);
                }
           if(event.getSource()==reset){
         nameTextFeild.setText("");
         categoryLisr.setValue("");
         priceTextFeild.setText("");
         quantityTextFeild.setText("");
         descriptionTextArea.setText("");
         
     }
           if(event.getSource()==backTODashboard){
               ClientChangePassword cp = new ClientChangePassword();
           //    scene = new Scene(dbfinal.ClientChangePassword);
            //   Stage stage = new Stage();
            //   stage.setScene(dbfinal.ClientChangePassword.launch(args));
               
               
           }
            
            
            
            
            
            
            
            
           if(event.getSource()==fontColor){
             ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                   /* scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    oldPasswordLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    newPasswordLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    //orderDateLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    //product_table.setStyle("-fx-text-fill:" + new_color + ";");*/

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
