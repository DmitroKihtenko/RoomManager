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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import rm.bean.*;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rm.database.mySql.RTGetSQL;
import org.apache.log4j.Logger;
import rm.database.mySql.RTModifySQL;
import rm.service.Assertions;
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
    private TextField numberTextField;

    private ConnectionsList rtAccess;
    ObjectProperty<TeacherInfo> selectedTeacher;
    ObjectProperty<RoomInfo> selectedRoom;
    ObjectProperty<HousingInfo> selectedHousing;

    @FXML
    public void initialize() {
        if (selectedTeacher == null) {
            selectedTeacher = (ObjectProperty<TeacherInfo>) Beans.context().get("selectedTeacher");
            selectedTeacher.addListener(new ChangeListener<TeacherInfo>() {
                @Override
                public void changed(ObservableValue<? extends TeacherInfo> observableValue,
                                    TeacherInfo teacherInfo, TeacherInfo t1) {
                    if (t1 != null && !t1.equals(teacherInfo)) {
                        nameTextField.setText(t1.getName());
                        surnameTextField.setText(t1.getSurname());
                        patronymicTextField.setText(t1.getPatronymic());
                    }
                }
            });
            nameTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.equals(s)) {
                        selectedTeacher.get().setName(t1);
                    }
                }
            });
        }

        if (selectedRoom == null) {
            selectedRoom = (ObjectProperty<RoomInfo>) Beans.context().get("selectedRoom");
            selectedRoom.addListener(new ChangeListener<RoomInfo>() {
                @Override
                public void changed(ObservableValue<? extends RoomInfo> observableValue,
                                    RoomInfo roomInfo, RoomInfo t1) {
                    if (t1 != null && !t1.equals(roomInfo)) {
                        numberTextField.setText(t1.getNumber());
                    }
                }
            });
        }

        /*
        if (selectedHousing == null) {
            selectedHousing = (ObjectProperty<HousingInfo>) Beans.context().get("selectedHousing");
            selectedHousing.addListener(new ChangeListener<HousingInfo>() {
                @Override
                public void changed(ObservableValue<? extends HousingInfo> observableValue,
                                    HousingInfo housingInfo, HousingInfo t1) {
                    if (t1 != null && !t1.equals(housingInfo)) {
                        numberTextField.setText(t1.getName());
                    }
                }
            });
        }
         */
    }

    public void setEditTeachers(ConnectionsList rtAccess) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        this.rtAccess = rtAccess;
    }
}
