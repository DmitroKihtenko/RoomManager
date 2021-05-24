package rm.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import rm.bean.RoomInfo;
import rm.database.DbConnect;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddNameController implements Initializable {

    @FXML
    private TextField nameFid;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Names names = null;

    private boolean update;
    int nameId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void save() {
        connection = DbConnect.getConnect();

        String name = nameFid.getText();

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill name");
            alert.showAndWait();
        } else {
            getQuery();
            insert();
            clear();
        }
    }

    @FXML
    private void clear() {
        nameFid.setText(null);
    }

    private void getQuery() {

        if (!update) {
            query = "INSERT INTO `name`(`id`, `name`) VALUES ('?,?')";

        } else {
            query = "UPDATE `name` SET "
                    + "`id`=?,"
                    + "`name`=? WHERE id = '"+nameId+"'";
        }

    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameFid.getText());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void setTextField(int id, String name) {
        nameId = id;
        nameFid.setText(name);

    }

    void setUpdate(boolean b) {
        this.update = b;
    }


}
