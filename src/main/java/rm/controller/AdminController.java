package main.java.rm.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.java.rm.Main;
import main.java.rm.bean.Datasource;
import main.java.rm.database.DbConnect;


import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.rm.database.mySql.RTGetSQL;

public class AdminController implements Initializable{

    @FXML
    private AnchorPane parent;

    @FXML
    private TableView<Names> namesTable;
    @FXML
    private TableColumn<Names, String> idCol;
    @FXML
    private TableColumn<Names, String> nameCol;
    @FXML
    private TableColumn<Names, String> editCol;

    private double xOffSet = 0;
    private double yOffSet = 0;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Names names = null;



    ObservableList<Names> NamesList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        RTGetSQL DbConnect = new RTGetSQL();
        Datasource datasource = DbConnect.getProvider().getDatasource();
        datasource.setPort("3306");
        datasource.setUrl("127.0.0.1");
        datasource.setSource("mysql:");
        loadDate();
        makeStageDragable();
    }

    @FXML
    private void minimize_stage(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void getAddView() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/AddName.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refreshTable() {
        NamesList.clear();

        query = "SELECT * FROM `name`";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                NamesList.add(new Names(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
                namesTable.setItems(NamesList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadDate() {

        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    private void makeStageDragable() {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> {
            Main.stage.setX(event.getScreenX() - xOffSet);
            Main.stage.setY(event.getScreenY() - yOffSet);
            Main.stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) -> {
            Main.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) -> {
            Main.stage.setOpacity(1.0f);
        });
    }
}
