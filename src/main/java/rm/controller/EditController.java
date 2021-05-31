package rm.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import rm.Main;
import rm.bean.Datasource;
import rm.bean.RoomInfo;
import rm.bean.TeacherInfo;
import rm.bean.User;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rm.database.mySql.RTGetSQL;
import org.apache.log4j.Logger;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

public class EditController {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField patronymicTextField;

    @FXML
    public void initialize() {

    }

    @FXML
    private void selectContractContractTab(MouseEvent mouseEvent) {
        
    }

    @FXML
    private void renameName(ActionEvent actionEvent) {

    }
}
