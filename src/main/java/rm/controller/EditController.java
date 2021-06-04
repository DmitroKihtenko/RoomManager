package rm.controller;

import javafx.beans.property.ObjectProperty;
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
            Logger.getLogger(EditController.class);

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label patronymicLabel;
    @FXML
    private Label roomKeyLabel;
    @FXML
    private TextField numberTextField;
    @FXML
    private TextField notUsedRoomField;
    @FXML
    private Label roomHousingIdLabel;
    @FXML
    private Label accessLabel;

    private final Notifications notifications;

    private ConnectionsList rtAccess;
    private HashMap<Integer, HousingInfo> housings;

    private final ObjectProperty<TeacherInfo> selectedTeacher;
    private final ObjectProperty<RoomInfo> selectedRoom;
    private final ObjectProperty<HousingInfo> selectedHousing;

    /**
     * Default constructor. Object initialization
     */
    public EditController() {
        notifications = (Notifications) Beans.context().
                get("notifications");
        selectedTeacher = (ObjectProperty<TeacherInfo>)
                Beans.context().get("selectedTeacher");
        selectedRoom = (ObjectProperty<RoomInfo>)
                Beans.context().get("selectedRoom");
        selectedHousing = (ObjectProperty<HousingInfo>)
                Beans.context().get("selectedHousing");
    }

    /**
     * Setter listeners for initialized objects
     */
    @FXML
    public void initialize() {
        if (selectedTeacher != null) {
            selectedTeacher.addListener((observableValue,
                                         teacherInfo, t1) -> {
                if (t1 != null && !t1.equals(teacherInfo)) {
                    nameLabel.setText(Objects.requireNonNullElse(
                                    t1.getName(),
                                    ""));
                    surnameLabel.setText(t1.getSurname());
                    patronymicLabel.setText(Objects.requireNonNullElse(
                            t1.getPatronymic(),
                            ""));
                } else if (t1 == null) {
                    nameLabel.setText("");
                    surnameLabel.setText("");
                    patronymicLabel.setText("");
                }
                tryToDisplayAccess();
            });

            selectedRoom.addListener((observableValue, roomInfo,
                                      t1) -> {
                roomHousingIdLabel.setText("");
                if (t1 != null && !t1.equals(roomInfo)) {
                    numberTextField.setText(t1.getNumber());
                    if (t1.getNotUsedReason() != null) {
                        notUsedRoomField.setText(
                                t1.getNotUsedReason());
                    } else {
                        notUsedRoomField.setText("");
                    }
                    if (t1.getHousingId() != null) {
                        roomHousingIdLabel.setText(
                                housings.get(t1.getHousingId()).
                                        getName());
                    }
                } else if (t1 == null) {
                    numberTextField.setText("");
                    roomHousingIdLabel.setText("");
                    notUsedRoomField.setText("");
                }
                tryToDisplayAccess();
            });
            numberTextField.textProperty().addListener(
                (observableValue, s, t1) -> {
            if(selectedRoom.get() != null &&
                !t1.equals(selectedRoom.get().getNumber())) {
                    try {
                        if (!t1.equals("")) {
                            selectedRoom.get().setNumber(t1);
                        } else {
                            selectedRoom.get().setNumber("");
                        }
                    } catch (IllegalArgumentException e) {
                        notifications.push("Room name syntax error: " +
                                e.getMessage());
                    }
                }
            });
            notUsedRoomField.textProperty().addListener(
                (observableValue, s, t1) -> {
                if (selectedRoom.get() != null &&
                        !t1.equals(selectedRoom.get().
                                getNotUsedReason())) {
                    try {
                        if (!t1.equals("")) {
                            selectedRoom.get().setNotUsedReason(t1);
                        } else {
                            selectedRoom.get().setNotUsedReason(null);
                        }
                    } catch (IllegalArgumentException e) {
                        notifications.push("Room info error: "
                                + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Setter new housingId for the selected room
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
     * Deletes housingId for the selected room
     */
    public void deleteHousingIdForRoom() {
        if (selectedRoom.get() != null) {
            selectedRoom.get().setHousingId(null);
            roomHousingIdLabel.setText("");
        }
    }

    /**
     * Add a new available room for the selected teacher
     */
    public void addNewRoomForTeacher() {
        if (selectedRoom.get() != null &&
                selectedTeacher.get() != null) {
            rtAccess.setConnection(selectedTeacher.get().getId(),
                    selectedRoom.get().getId());
        }
    }

    /**
     * Remove available room for the selected teacher
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
     * @param rtAccess getting ConnectionsList
     * @param housings getting clonedHousing
     */
    public void setEditTeachers(ConnectionsList rtAccess,
                                HashMap<Integer, HousingInfo> housings) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        Assertions.isNotNull(housings, "Housing map", logger);
        this.rtAccess = rtAccess;
        this.housings = housings;
        rtAccess.changedProperty().addListener(
                (observableValue, aBoolean, t1) -> tryToDisplayAccess());
    }

    /**
     * Display access between the selected room and the selected teacher
     */
    private void tryToDisplayAccess() {
        if (selectedTeacher.get() != null &&
                selectedRoom.get() != null) {
            if (rtAccess.existsConnection(
                    selectedTeacher.get().getId(),
                    selectedRoom.get().getId())) {
                accessLabel.setText("Access is allowed");
            } else {
                accessLabel.setText("Access is denied");
            }
        } else {
            accessLabel.setText("");
        }
    }
}
