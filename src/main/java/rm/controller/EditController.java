package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rm.model.*;

import org.apache.log4j.Logger;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
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
    private TextField housingTextField;
    @FXML
    private TextField notUsedHousingTextField;
    @FXML
    private Label roomHousingIdLabel;
    @FXML
    private Label infoAboutConnectRoomAndTeacherLabel;

    private Notifications notifications;

    private ConnectionsList rtAccess;
    private HashMap<Integer, HousingInfo> housings;

    ObjectProperty<TeacherInfo> selectedTeacher;
    ObjectProperty<RoomInfo> selectedRoom;
    ObjectProperty<HousingInfo> selectedHousing;

    /**
     *
     */
    @FXML
    public void initialize() {
        notifications = (Notifications) Beans.context().
                get("notifications");
        if (selectedTeacher == null) {
            selectedTeacher = (ObjectProperty<TeacherInfo>)
                    Beans.context().get("selectedTeacher");
            selectedTeacher.addListener(new ChangeListener<TeacherInfo>() {
                @Override
                public void changed(ObservableValue<?
                        extends TeacherInfo> observableValue,
                                    TeacherInfo teacherInfo,
                                    TeacherInfo t1) {
                    if (t1 != null && !t1.equals(teacherInfo)) {
                        nameTextField.setText(Objects.requireNonNullElse(
                                        t1.getName(),
                                        ""));
                        surnameTextField.setText(t1.getSurname());
                        patronymicTextField.setText(Objects.requireNonNullElse(
                                t1.getPatronymic(),
                                ""));
                    } else if (t1 == null) {
                        nameTextField.setText("");
                        surnameTextField.setText("");
                        patronymicTextField.setText("");
                    }
                    if (selectedTeacher.get() != null
                            && selectedRoom.get() != null) {
                        if (rtAccess.existsConnection(
                                selectedTeacher.get().getId(),
                                selectedRoom.get().getId())) {
                            infoAboutConnectRoomAndTeacherLabel.
                                    setText("Access is allowed");
                        } else {
                            infoAboutConnectRoomAndTeacherLabel.
                                    setText("Access is denied");
                        }
                    } else {
                        infoAboutConnectRoomAndTeacherLabel.setText("");
                    }
                }
            });
            nameTextField.textProperty().addListener(
                    new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?
                        extends String> observableValue,
                                    String s,
                                    String t1) {
                    if  (selectedTeacher.get() != null &&
                            !t1.equals(selectedTeacher.get().getName())) {
                        if (!t1.equals("")) {
                            try {
                                selectedTeacher.get().setName(t1);
                            } catch (IllegalArgumentException e) {
                                notifications.push("Username syntax " +
                                        "error: " + e.getMessage());
                            }
                        } else {
                            selectedTeacher.get().setName(null);
                        }
                    }
                }
            });
            surnameTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?
                        extends String> observableValue,
                                    String s,
                                    String t1) {
                    if  (selectedTeacher.get() != null &&
                            !t1.equals(selectedTeacher.get().getSurname())) {
                        try {
                            if (!t1.equals("")) {
                                selectedTeacher.get().setSurname(t1);
                            } else {
                                selectedTeacher.get().setSurname("");
                            }
                        } catch (IllegalArgumentException e) {
                            notifications.push("Surname syntax error: "
                                    + e.getMessage());
                        }
                    }
                }
            });
            patronymicTextField.textProperty().addListener(
                    new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?
                        extends String> observableValue,
                                    String s,
                                    String t1) {
                    if  (selectedTeacher.get() != null &&
                            !t1.equals(selectedTeacher.get().
                                    getPatronymic())) {
                        if (!t1.equals("")) {
                            selectedTeacher.get().setPatronymic(t1);
                        } else {
                            selectedTeacher.get().setPatronymic(null);
                        }
                    }
                }
            });
        }

        if (selectedRoom == null) {
            selectedRoom = (ObjectProperty<RoomInfo>)
                    Beans.context().get("selectedRoom");
            selectedRoom.addListener(
                    new ChangeListener<RoomInfo>() {
                @Override
                public void changed(ObservableValue<?
                        extends RoomInfo> observableValue,
                                    RoomInfo roomInfo,
                                    RoomInfo t1) {
                    roomHousingIdLabel.setText("");
                    if (t1 != null && !t1.equals(roomInfo)) {
                        numberTextField.setText(t1.getNumber());
                        if (t1.getNotUsedReason() != null) {
                            notUsedHousingTextField.setText(
                                    t1.getNotUsedReason());
                        } else {
                            notUsedHousingTextField.setText("");
                        }
                        if (t1.getHousingId() != null) {
                            roomHousingIdLabel.setText(
                                    housings.get(t1.getHousingId()).
                                            getName());
                        }
                    } else if (t1 == null) {
                        numberTextField.setText("");
                        roomHousingIdLabel.setText("");
                        notUsedHousingTextField.setText("");
                    }
                    if (selectedTeacher.get() != null &&
                            selectedRoom.get() != null) {
                        if (rtAccess.existsConnection(
                                selectedTeacher.get().getId(),
                                selectedRoom.get().getId())) {
                            infoAboutConnectRoomAndTeacherLabel.
                                    setText("Access is allowed");
                        } else {
                            infoAboutConnectRoomAndTeacherLabel.
                                    setText("Access is denied");
                        }
                    } else {
                        infoAboutConnectRoomAndTeacherLabel.
                                setText("");
                    }
                }
            });
            numberTextField.textProperty().addListener(
                    new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?
                        extends String> observableValue,
                                    String s,
                                    String t1) {
                    if  (selectedRoom.get() != null &&
                            !t1.equals(selectedRoom.get().getNumber())) {
                        try {
                            if (!t1.equals("")) {
                                selectedRoom.get().setNumber(t1);
                            } else {
                                selectedRoom.get().setNumber(null);
                            }
                        } catch (IllegalArgumentException e) {
                            notifications.push("Room name syntax error: " +
                                    e.getMessage());
                        }
                    }
                }
            });
            notUsedHousingTextField.textProperty().addListener(
                    new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?
                        extends String> observableValue,
                                    String s,
                                    String t1) {
                    if (selectedRoom.get() != null &&
                            !t1.equals(selectedRoom.get().
                                    getNotUsedReason())) {
                        if (!t1.equals("")) {
                            selectedRoom.get().setNotUsedReason(t1);
                        } else {
                            selectedRoom.get().setNotUsedReason(null);
                        }
                    }
                }
            });
        }

        if (selectedHousing == null) {
            selectedHousing = (ObjectProperty<HousingInfo>)
                    Beans.context().get("selectedHousing");
            selectedHousing.addListener(
                    new ChangeListener<HousingInfo>() {
                @Override
                public void changed(ObservableValue<?
                        extends HousingInfo> observableValue,
                                    HousingInfo housingInfo,
                                    HousingInfo t1) {
                    if (t1 != null && !t1.equals(housingInfo)) {
                        housingTextField.setText(t1.getName());
                    } else if (t1 == null) {
                        housingTextField.setText("");
                    }
                }
            });
            housingTextField.textProperty().addListener(
                    new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?
                        extends String> observableValue,
                                    String s,
                                    String t1) {
                    if (selectedHousing.get() != null &&
                    !t1.equals(selectedHousing.get().getName())) {
                        try {
                            if (!t1.equals("")) {
                                selectedHousing.get().setName(t1);
                            } else {
                                selectedHousing.get().setName("");
                            }
                        } catch (IllegalArgumentException e) {
                            notifications.push("Housing name syntax error: " +
                                    e.getMessage());
                        }
                    }
                }
            });
        }
    }

    /**
     *
     */
    public void setNewHousingIdForRoom() {
        if (selectedRoom.get() != null &&
                selectedHousing.get() != null) {
            selectedRoom.get().setHousingId(
                    selectedHousing.get().getId());
            roomHousingIdLabel.setText(housings.get(
                    selectedRoom.get().getHousingId()).getName());
        }
    }

    /**
     *
     */
    public void deleteHousingIdForRoom() {
        if (selectedRoom.get() != null) {
            selectedRoom.get().setHousingId(null);
            roomHousingIdLabel.setText("");
        }
    }

    /**
     *
     */
    public void addNewRoomForTeacher() {
        if (selectedRoom.get() != null &&
                selectedTeacher.get() != null) {
            rtAccess.setConnection(selectedTeacher.get().getId(),
                    selectedRoom.get().getId());
        }
    }

    /**
     *
     */
    public void removeNewRoomForTeacher() {
        if (selectedRoom.get() != null &&
                selectedTeacher.get() != null) {
            if (rtAccess.existsConnection(selectedTeacher.get().getId(),
                    selectedRoom.get().getId())) {
                rtAccess.removeConnection(selectedTeacher.get().getId(),
                        selectedRoom.get().getId());
            }
        }
    }

    /**
     * Setter for connectionsList, for housing HashMap
     * @param rtAccess
     * @param housings
     */
    public void setEditTeachers(ConnectionsList rtAccess,
                                HashMap<Integer, HousingInfo> housings) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        Assertions.isNotNull(housings, "Housing map", logger);
        this.rtAccess = rtAccess;
        this.housings = housings;
        rtAccess.changedProperty().addListener(
                new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<?
                    extends Boolean> observableValue,
                                Boolean aBoolean,
                                Boolean t1) {
                if (selectedTeacher.get() != null &&
                        selectedRoom.get() != null) {
                    if (rtAccess.existsConnection(
                            selectedTeacher.get().getId(),
                            selectedRoom.get().getId())) {
                        infoAboutConnectRoomAndTeacherLabel.
                                setText("Access is allowed");
                    } else {
                        infoAboutConnectRoomAndTeacherLabel.
                                setText("Access is denied");
                    }
                } else {
                    infoAboutConnectRoomAndTeacherLabel.
                            setText("");
                }
            }
        });
    }
}
