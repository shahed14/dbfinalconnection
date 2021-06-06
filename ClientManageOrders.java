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

public class ClientManageOrders extends Application {

    Scene scene = null;
    Connection connection = null;
    Statement statement = null;
    MenuBar allMenues;
    Menu fileMenu, formatMenu, helpMenu, fontSize, fontfamily;
    MenuItem exit, fontColor, backgroundColor, aboutApp;
    RadioMenuItem small, medium, large, arial, sans_serif, salmon, royalblue;
    ToggleGroup fontSizeToggle, fontFamilyToggle;
    Button addOrder, editOrder, viewOrder, searchForOrder, deleteOrder, back, reset;
    Label productListLabel, quantityLabel, orderDateLabel, Product_idLabel;
    ComboBox<String> product_list;
    TextField quantityTextFeild, orderDateTextFeild, product_idText;
    TableView<orders> product_table;
    TableColumn<orders, String> productColumnTable, orderDateTableColumn;
    TableColumn<orders, Integer> quantityTableColumn, idTableColumn;

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
            di.setContentText("this page is created in order to manage orders like(add , edit , delete , search , view)orders");
            di.show();
        });
        helpMenu.getItems().add(aboutApp);
        allMenues.getMenus().addAll(fileMenu, formatMenu, helpMenu);
        productListLabel = new Label("products :");
        /*String sql = "select Name from products";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> product_list2 = new ArrayList<>();
        while (rs.next()) {
            products p = new products();
            p.setName(rs.getString("Name"));
            product_list2.add(p.getName());
        }*/
        // String[] products = new String[]{"tv" , "computer","cups","tables" };
       // ObservableList<String> observableList = FXCollections.observableArrayList(products);
        product_list = new ComboBox();
        String sql = "Select Name from products";
        ResultSet rs = statement.executeQuery(sql);
        ArrayList<String> p = new ArrayList<>();
        while(rs.next()){
            products pp = new products();
            pp.setName(rs.getString("Name"));
            p.add(pp.getName());
        }
        product_list.getItems().addAll(p);
        product_list.setEditable(true);
        HBox hb1 = new HBox(10, productListLabel, product_list);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(10));
        productListLabel = new Label("Product_number :");
        product_idText = new TextField();
        HBox hb = new HBox(10, productListLabel, product_idText);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(10));
        quantityLabel = new Label("quantity :");
        quantityTextFeild = new TextField();
        HBox hb2 = new HBox(10, quantityLabel, quantityTextFeild);
        hb2.setAlignment(Pos.CENTER);
        hb2.setPadding(new Insets(10));
        orderDateLabel = new Label("order date");
        orderDateTextFeild = new TextField();
        HBox hb3 = new HBox(10, orderDateLabel, orderDateTextFeild);
        hb3.setAlignment(Pos.CENTER);
        hb3.setPadding(new Insets(10));
        addOrder = new Button("add order");
        addOrder.setId("rich-blue");
        addOrder.setOnAction(eventhandler);
        editOrder = new Button("edit order");
        editOrder.setOnAction(eventhandler);
                editOrder.setId("rich-blue");

        viewOrder = new Button("view order");
                viewOrder.setId("rich-blue");

        viewOrder.setOnAction(eventhandler);

        searchForOrder = new Button("search");
                searchForOrder.setId("rich-blue");

        searchForOrder.setOnAction(eventhandler);
        deleteOrder = new Button("delete order");
        deleteOrder.setOnAction(eventhandler);
                deleteOrder.setId("rich-blue");

        reset = new Button("reset");
        reset.setOnAction(eventhandler);
                        reset.setId("rich-blue");

        back = new Button("back");
        back.setId("rich-blue");
        back.setOnAction(eventhandler);
        HBox hbButtons = new HBox(10, addOrder, viewOrder, editOrder, searchForOrder, deleteOrder, back, reset);
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setPadding(new Insets(10));
        product_table = new TableView<orders>();
        productColumnTable = new TableColumn("p Name");
        productColumnTable.setCellValueFactory(new PropertyValueFactory("name"));
        idTableColumn = new TableColumn("p Number");
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        quantityTableColumn = new TableColumn("Quantity");
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory("quantity"));

        orderDateTableColumn = new TableColumn("Order Date");
        orderDateTableColumn.setCellValueFactory(new PropertyValueFactory("date"));

        product_table.getColumns().addAll(idTableColumn, productColumnTable, quantityTableColumn, orderDateTableColumn);
        VBox vbAll = new VBox(15, allMenues, hb, hb1, hb2, hb3, product_table, hbButtons);
        vbAll.setAlignment(Pos.CENTER);
        vbAll.setPadding(new Insets(10));

        scene = new Scene(vbAll, 900, 700);
        primaryStage.setTitle("manage products");
        primaryStage.setScene(scene);
              scene.getStylesheets().add("file:src//dbfinal//clientProfile.css");

        primaryStage.show();

    }

    public class eventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == addOrder) {
                String product_input = product_list.getValue();
                String productnum_input = product_idText.getText();
                String quantity_input = quantityTextFeild.getText();
                String orderDate_input = orderDateTextFeild.getText();

                if (validate_input(product_input)&&validate_numric_value(productnum_input)
                        &&validate_numric_value(quantity_input) && validate_input(orderDate_input)) {
                    try {

                        String sql = "insert into orders ( Id ,Name , Quantity , Date)"
                                + "values('" + productnum_input + "','" + product_input
                                + "','" + quantity_input + "','" + orderDate_input + "')";

                        int excuteUpdate = statement.executeUpdate(sql);

                        if (excuteUpdate > 0) {
                            informationBox("order added successfully", null, "success");
                            clear();
                        } else {
                            informationBox("order addition failed", null, "Failed");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
            if (event.getSource() == viewOrder) {
                try {
                    String sql = "select Id, Name , Quantity , Date from orders";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<orders> order_list = new ArrayList<>();
                    while (rs.next()) {
                        orders o = new orders();
                        o.setId(rs.getInt("Id"));
                        o.setName(rs.getString("Name"));
                        o.setQuantity(rs.getInt("Quantity"));
                        o.setDate(rs.getString("Date"));
                        order_list.add(o);

                    }
                    product_table.getItems().setAll(order_list);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }

            }
            if (event.getSource() == editOrder) {
                try {
                    Window box = editOrder.getScene().getWindow();

                    orders order = product_table.getSelectionModel().getSelectedItem();
                    String sql = "update orders set Id ='" + order.getId() + "',Name = '" + order.getName()
                            + "',Quantity ='" + order.getQuantity() + "',Date  ='" + order.getDate() + "'where Id ='" + order.getId() + "'";
                    int excuteUpdate = statement.executeUpdate(sql);
                    if (excuteUpdate > 0) {
                        informationBox("order updated successfully", null, "success");
                    } else {
                        informationBox("update failed! please try again", null, "failed");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }

            }
            if (event.getSource() == deleteOrder) {
                orders order = product_table.getSelectionModel().getSelectedItem();
                if (order != null) {
                    try {
                        String sql = "delete from orders where Id ='" + order.getId() + "'";
                        int excute = statement.executeUpdate(sql);
                        System.out.println("affected rows" + excute);
                        if(excute>0){
                       informationBox("order deleted successfully", null, "success"); 
                    }
                       else{
                    informationBox("deletion failed! please try again", null, "failed");
              
                               }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }

            if (event.getSource() == searchForOrder) {
                try {
                  //  orders order2 = product_table.getSelectionModel().getSelectedItem();
                    String id = product_idText.getText();
                    String sql = "select Id , name , Quantity , Date from orders where Id ='" +id+ "'";
                    ResultSet rs = statement.executeQuery(sql);
                    ArrayList<orders>order_search = new ArrayList<>();
                    while (rs.next()) {
                      //  products p = new products();
                      //  p.setName(rs.getString("Name"));
                          orders order2 = new orders();
                        order2.setId(rs.getInt("Id"));
                        order2.setName(rs.getString("name"));
                        order2.setQuantity(rs.getInt("Quantity"));
                        order2.setDate(rs.getString("Date"));
                        order_search.add(order2);
                        
                    }
                    if(order_search!=null){
                    product_table.getItems().setAll(order_search);
                    }
                    else{
                        informationBox("order is not exist", null, "failed");
                    }
                   

                } catch (SQLException ex) {
                    System.out.println(ex);
                }

            }
            if (event.getSource() == reset) {
                product_idText.setText("");
                product_list.setValue("");
                quantityTextFeild.setText("");
                orderDateTextFeild.setText("");
            }
            if(event.getSource()==back){
                //clientProfile cp = new clientProfile();
                
            }

            if (event.getSource() == fontColor) {
                ColorPicker cp = new ColorPicker();
                Stage new_stage = new Stage();
                Button apply_color = new Button("color");
                apply_color.setOnAction((ActionEvent e) -> {
                    Color value = cp.getValue();
                    String new_color = value.toString().replace("0x", "#");
                    // scene.getRoot().setStyle("-fx-text-fill:"+new_color+";");
                    productListLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    quantityLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    orderDateLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    Product_idLabel.setStyle("-fx-text-fill:" + new_color + ";");
                    product_table.setStyle("-fx-text-fill:" + new_color + ";");

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

    private static void informationBox(String infoMessage, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    private boolean validate_input(String input) {
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
    private void clear(){
        product_idText.setText("");
        product_list.setValue("");
        quantityTextFeild.setText("");
        orderDateTextFeild.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
