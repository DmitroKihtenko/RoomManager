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

import java.util.Objects;

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

    @FXML
    private TextArea notUsedHousing;

    private ConnectionsList rtAccess;
    ObjectProperty<TeacherInfo> selectedTeacher;
    ObjectProperty<RoomInfo> selectedRoom;
    ObjectProperty<HousingInfo> selectedHousing;

    int selectedHousingId = 1;

    @FXML
    public void initialize() {
        if (selectedTeacher == null) {
            selectedTeacher = (ObjectProperty<TeacherInfo>) Beans.context().get("selectedTeacher");
            selectedTeacher.addListener(new ChangeListener<TeacherInfo>() {
                @Override
                public void changed(ObservableValue<? extends TeacherInfo> observableValue,
                                    TeacherInfo teacherInfo, TeacherInfo t1) {
                    if (t1 != null && !t1.equals(teacherInfo)) {

                        nameTextField.setText(Objects.requireNonNullElse(t1.getName(), ""));
                        surnameTextField.setText(t1.getSurname());
                        patronymicTextField.setText(Objects.requireNonNullElse(t1.getPatronymic(), ""));
                    }
                }
            });
            nameTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if  (!t1.equals(selectedTeacher.get().getName())) {
                        selectedTeacher.get().setName(t1);
                    }
                }
            });
            surnameTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if  (!t1.equals(selectedTeacher.get().getSurname())) {
                        selectedTeacher.get().setSurname(t1);
                    }
                }
            });
            patronymicTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if  (!t1.equals(selectedTeacher.get().getPatronymic())) {
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
                        if (t1.getNotUsedReason() != null) {
                            notUsedHousing.setText(t1.getNotUsedReason());
                        }
                    }
                }
            });

            numberTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if  (!t1.equals(selectedRoom.get().getNumber())) {
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
                        selectedHousingId = t1.getId();
                    }
                }
            });
            housingTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if  (!t1.equals(selectedHousing.get().getName())) {
                        selectedHousing.get().setName(t1);
                    }
                }
            });
        }
    }

    public void setNewHousingIdForRoom() {
        selectedRoom.get().setHousingId(selectedHousingId);
    }

    public void setEditTeachers(ConnectionsList rtAccess) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        this.rtAccess = rtAccess;
    }
}
