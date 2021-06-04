package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private Label numberLabel;
    @FXML
    private Label notUsedRoomLabel;
    @FXML
    private Label roomHousingIdLabel;
    @FXML
    private Label occupiedByTeacherLabel;
    @FXML
    private Label accessLabel;

    private final Notifications notifications;

    private ConnectionsList rtAccess;
    private HashMap<Integer, HousingInfo> housings;
    private HashMap<Integer, Teacher> teachers;

    private final ObjectProperty<Teacher> selectedTeacher;
    private final ObjectProperty<Room> selectedRoom;
    private final ObjectProperty<HousingInfo> selectedHousing;

    /**
     * Default constructor. Object initialization
     */
    public EditController() {
        notifications = (Notifications) Beans.context().
                get("notifications");
        selectedTeacher = (ObjectProperty<Teacher>)
                Beans.context().get("selectedTeacher");
        selectedRoom = (ObjectProperty<Room>)
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

                    if (t1.getUsesRoom()) {
                        roomKeyLabel.setText(String.valueOf(t1.getUsedRoomId())); // поменять
                    } else {
                        roomKeyLabel.setText("");
                    }
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
                    numberLabel.setText(t1.getNumber());
                    if (t1.getNotUsedReason() != null) {
                        notUsedRoomLabel.setText(
                                t1.getNotUsedReason());
                    } else {
                        notUsedRoomLabel.setText("");
                    }
                    if (t1.getHousingId() != null) {
                        roomHousingIdLabel.setText(
                                housings.get(t1.getHousingId()).
                                        getName());
                    }
                    if (t1.getOccupiedBy() != null) {
                        occupiedByTeacherLabel.setText(String.valueOf(selectedTeacher.get().getId())); // переделать!
                        //occupiedByTeacherLabel.setText(teachers.get(t1.getOccupiedBy()).getName());
                    } else {
                        occupiedByTeacherLabel.setText("");
                    }
                } else if (t1 == null) {
                    numberLabel.setText("");
                    roomHousingIdLabel.setText("");
                    notUsedRoomLabel.setText("");
                    occupiedByTeacherLabel.setText("");
                }
                tryToDisplayAccess();
            });
        }
    }

    /**
     * Setter for connectionsList, for housing HashMap
     * @param rtAccess getting ConnectionsList
     * @param housings getting clonedHousing
     */
    public void setEditTeachers(ConnectionsList rtAccess,
                                HashMap<Integer, HousingInfo> housings/*, HashMap<Integer, Teacher> teachers*/) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        Assertions.isNotNull(housings, "Housing map", logger);
        this.rtAccess = rtAccess;
        this.housings = housings;
        //this.teachers = teachers;
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

    public void giveKey(MouseEvent mouseEvent) {
        if (!selectedTeacher.get().getUsesRoom()) {
            selectedTeacher.get().setUsedRoom(selectedRoom.get().getId());

            selectedRoom.get().setOccupiedBy(selectedTeacher.get().getId());
            occupiedByTeacherLabel.setText(String.valueOf(selectedTeacher.get().getId())); // переделать!
            System.out.println("Ключ выдан " + selectedTeacher.get().getName() + ": Аудитория - " + selectedRoom.get().getId());
        } else {
            System.out.println("Ключ уже выдан!");
        }

    }

    public void returnKey(MouseEvent mouseEvent) {
        if (selectedTeacher.get().getUsesRoom()) {
            selectedTeacher.get().setNotUsedRoom();
            System.out.println("Ключ получен!");
        }
    }

    public void returnAll(MouseEvent mouseEvent) {
    }
}
