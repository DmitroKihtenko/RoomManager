package rm.controller;


import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rm.bean.*;

import org.apache.log4j.Logger;
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

    @FXML
    private  TextField housingTextField;

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
            surnameTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.equals(s)) {
                        selectedTeacher.get().setSurname(t1);
                    }
                }
            });
            patronymicTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.equals(s)) {
                        selectedTeacher.get().setPatronymic(t1);
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
            numberTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.equals(s)) {
                        selectedRoom.get().setNumber(t1);
                    }
                }
            });
        }

        if (selectedHousing == null) {
            selectedHousing = (ObjectProperty<HousingInfo>) Beans.context().get("selectedHousing");
            selectedHousing.addListener(new ChangeListener<HousingInfo>() {
                @Override
                public void changed(ObservableValue<? extends HousingInfo> observableValue,
                                    HousingInfo housingInfo, HousingInfo t1) {
                    if (t1 != null && !t1.equals(housingInfo)) {
                        housingTextField.setText(t1.getName());
                    }
                }
            });
            housingTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.equals(s)) {
                        selectedHousing.get().setName(t1);
                    }
                }
            });
        }
    }

    public void setEditTeachers(ConnectionsList rtAccess) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        this.rtAccess = rtAccess;
    }
}
