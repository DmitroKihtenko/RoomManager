package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
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
    private static final String SEPARATOR = "-";

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
    private HashMap<Integer, Room> rooms;


    private final ObjectProperty<Teacher> selectedTeacher;
    private final ObjectProperty<Room> selectedRoom;
    private final ChangeListener<Number> usesRoom;
    private final ChangeListener<Number> occupiedByTeacher;

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
        usesRoom = (observableValue, number, t1) -> {
            if(!number.equals(t1)) {
                if(!t1.equals(IdHolder.DEFAULT_ID)) {
                    roomKeyLabel.setText(
                            getRoomString(rooms.get(t1.
                                    intValue())));
                } else {
                    roomKeyLabel.setText("");
                }
            }
        };
        occupiedByTeacher = (observableValue, number, t1) -> {
            if(!number.equals(t1)) {
                if(!t1.equals(IdHolder.DEFAULT_ID)) {
                    occupiedByTeacherLabel.setText(
                            fullTeacherName(teachers.get(t1.
                                    intValue())));
                } else {
                    occupiedByTeacherLabel.setText("");
                }
            }
        };
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
            } else if (t1 == null) {
                numberLabel.setText("");
                roomHousingIdLabel.setText("");
                notUsedRoomLabel.setText("");
            }
            tryToDisplayAccess();
        });

        selectedRoom.addListener((observableValue,
                                  room, t1) -> {
            if (room == null && t1 != null) {
                t1.occupiedByProperty().
                        addListener(occupiedByTeacher);
                if(!t1.isAvailable()) {
                    occupiedByTeacherLabel.setText(
                            fullTeacherName(teachers.
                                    get(t1.getOccupiedBy())));
                } else {
                    occupiedByTeacherLabel.setText("");
                }
            } else if (room != null && t1 == null) {
                room.occupiedByProperty().
                        removeListener(occupiedByTeacher);
                occupiedByTeacherLabel.setText("");
            } else if (room != null) {
                room.occupiedByProperty().
                        removeListener(occupiedByTeacher);
                t1.occupiedByProperty().
                        addListener(occupiedByTeacher);
                if(!t1.isAvailable()) {
                    occupiedByTeacherLabel.setText(
                            fullTeacherName(teachers.
                                    get(t1.getOccupiedBy())));
                } else {
                    occupiedByTeacherLabel.setText("");
                }
            }
        });

        selectedTeacher.addListener((observableValue,
                                     teacher, t1) -> {
            if (teacher == null && t1 != null) {
                t1.usedRoomIdProperty().
                        addListener(usesRoom);
                if(t1.getUsesRoom()) {
                    roomKeyLabel.setText(getRoomString(
                            rooms.get(t1.getUsedRoomId())));
                } else {
                    roomKeyLabel.setText("");
                }
            } else if (teacher != null && t1 == null) {
                teacher.usedRoomIdProperty().
                        removeListener(usesRoom);
                roomKeyLabel.setText("");
            } else if (teacher != null) {
                teacher.usedRoomIdProperty().
                        removeListener(usesRoom);
                t1.usedRoomIdProperty().
                        addListener(usesRoom);
                if(t1.getUsesRoom()) {
                    roomKeyLabel.setText(getRoomString(
                            rooms.get(t1.getUsedRoomId())));
                } else {
                    roomKeyLabel.setText("");
                }
            }
        });
    }

    /**
     * Setter for connectionsList, for housing HashMap
     * @param rtAccess getting ConnectionsList
     * @param housings getting clonedHousing
     */
    public void setEditTeachers(ConnectionsList rtAccess,
                                HashMap<Integer, HousingInfo> housings,
                                HashMap<Integer, Teacher> teachers,
                                HashMap<Integer, Room> rooms) {
        Assertions.isNotNull(rtAccess, "Access connections", logger);
        Assertions.isNotNull(housings, "Housing map", logger);
        this.rtAccess = rtAccess;
        this.housings = housings;
        this.teachers = teachers;
        this.rooms = rooms;
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

    public void giveKey() {
        if(selectedTeacher.get() != null &&
                selectedRoom.get() != null) {
            if(!rtAccess.existsConnection(selectedTeacher.get().
                    getId(), selectedRoom.get().getId())) {
                logger.warn("Attempt to give from room to teacher" +
                        " that has not access");
                notifications.push("Attempt to give from room " +
                        "to teacher that has not access");
            } else {
                if(!selectedRoom.get().isUsed()) {
                    logger.warn("Attempt to give from room that " +
                            "is not used");
                    notifications.push("Room is not used");
                } else {
                    if(selectedTeacher.get().getUsesRoom()) {
                        logger.warn("Attempt to give key of " +
                                "room while teacher already has it");
                        notifications.push("Teacher already " +
                                "has key");
                    } else if(!selectedRoom.get().isAvailable()) {
                        logger.warn("Attempt to give key of room " +
                                "that is not available");
                        notifications.push("Room is not available");
                    } else {
                        selectedRoom.get().setOccupiedBy(
                                selectedTeacher.get().getId());
                        selectedTeacher.get().setUsedRoom(selectedRoom.
                                get().
                                getId());
                        logger.info("Key was gaven to teacher " +
                                selectedTeacher.get() + " from room " +
                                selectedRoom.get());
                    }
                }
            }
        }
    }

    public void returnKey() {
        if(selectedTeacher.get() != null) {
            for (Room room : rooms.values()) {
                if(!room.isAvailable()) {
                    if(room.getOccupiedBy()
                            == selectedTeacher.get().getId()) {
                        room.setNotOccupied();
                        break;
                    }
                }
            }
            selectedTeacher.get().setNotUsedRoom();
            logger.info("Returned key from teacher " +
                    selectedTeacher.get());
        }
    }

    public void returnAll() {
        for (Room room : rooms.values()) {
            if(!room.isAvailable()) {
                room.setNotOccupied();
            }
        }
        for(Teacher teacher : teachers.values()) {
            if(teacher.getUsesRoom()) {
                teacher.setNotUsedRoom();
            }
        }
        logger.info("Returned keys from all teachers");
    }

    private String fullTeacherName(Teacher teacher) {
        String value = teacher.getSurname();
        if(teacher.getName() != null) {
            value += " " + teacher.getName();
        }
        if(teacher.getPatronymic() != null) {
            value += " " + teacher.getPatronymic();
        }
        return value;
    }

    private String getRoomString(RoomInfo room) {
        String value = "";
        HousingInfo housing = housings.get(room.getHousingId());
        if(housing != null) {
            value += housing.getName() + SEPARATOR;
        }
        value += room.getNumber();
        return value;
    }
}
