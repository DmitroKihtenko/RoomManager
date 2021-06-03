package rm.model;

import javafx.beans.property.*;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Teacher extends TeacherInfo implements Cloneable {
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
     *
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

    @Override
    public Replicable replicate(Replicable object) {
        Teacher teacher = (Teacher) object;
        if (this.usesRoom()) {
            teacher.setUsedRoom(getUsedRoomId());
        } else {
            teacher.setNotUsedRoom();
        }
        super.replicate(teacher);
        return teacher;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return replicate(new Teacher("surname"));
    }

    @Override
    public String toString() {
        String result = "";

        result += "usesRoom - " + usesRoom() + ", ";

        if (getUsedRoomId() != null) {
            result += "usedRoomId - " + getUsedRoomId() + ". ";
        }
        return result += super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Teacher)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }

        Teacher guest = (Teacher) obj;
        Object s1 = Objects.requireNonNullElse(
                usesRoom(), Boolean.FALSE);
        Object s2 = Objects.requireNonNullElse(guest.
                usesRoom(), Boolean.FALSE);

        if (!s1.equals(s2)) {
            return false;
        }

        s1 = Objects.requireNonNullElse(
                getUsedRoomId(), Integer.MIN_VALUE);
        s2 = Objects.requireNonNullElse(guest.
                getUsedRoomId(), Integer.MIN_VALUE);

        if (!s1.equals(s2)) {
            return false;
        }

        return true;
    }
}
