package main.java.rm.bean;

import javafx.beans.property.*;
import org.apache.log4j.Logger;

class Teacher extends TeacherInfo {
    private static final Logger logger = Logger.getLogger(Room.class);

    private BooleanProperty usesRoom;
    private IntegerProperty usedRoomId;

    public Teacher(String name) {
        super(name);
    }

    public boolean usesRoom() {
        return usesRoom.get();
    }

    public BooleanProperty usesRoomProperty() {
        return usesRoom;
    }

    public void setUsedRoom(int roomId) {
        this.usesRoom.set(true);
        this.usedRoomId.set(roomId);
    }

    public void setNotUsedRoom() {
        this.usesRoom.set(false);
        this.usedRoomId.set(0);
    }

    /**
     * Getter for room id that teacher is using at the moment
     * @return room id that teacher is use or null if teacher is not use any room
     */
    public Integer getUsedRoomId() {
        if (!usesRoom.get()) {
            return null;
        }
        return usedRoomId.get();
    }

    public IntegerProperty usedRoomIdProperty() {
        return usedRoomId;
    }
}
