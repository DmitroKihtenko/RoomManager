package rm.model;

import javafx.beans.property.*;

/**
 * Class successor of class {@link TeacherInfo} that contains data about used by teacher room
 */
public class Teacher extends TeacherInfo implements Cloneable {
    private final BooleanProperty usesRoom;
    private final IntegerProperty usedRoomId;

    /**
     * Constructor, sets default value of teacher surname
     * @param surname teacher surname
     */
    public Teacher(String surname) {
        super(surname);
        usesRoom = new SimpleBooleanProperty(false);
        usedRoomId = new SimpleIntegerProperty(IdHolder.DEFAULT_ID);
    }

    /**
     * Indicates whether the teacher used some room
     * @return true if teacher uses some room, otherwise returns false
     */
    public boolean getUsesRoom() {
        return usesRoom.get();
    }

    /**
     * Used to track property of using bu teacher some room
     * @return int property for java fx mvc
     */
    public BooleanProperty usesRoomProperty() {
        return usesRoom;
    }

    /**
     Getter for room id that teacher is using at the moment
     * @param roomId room id that teacher is use or null if teacher is not use any room
     */
    public void setUsedRoom(int roomId) {
        this.usesRoom.set(true);
        this.usedRoomId.set(roomId);
    }

    /**
     * Sets that teacher is not using any room
     */
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

    /**
     * Used to track room id property that teacher is using
     * @return int property for java fx mvc
     */
    public IntegerProperty usedRoomIdProperty() {
        return usedRoomId;
    }

    @Override
    public void replicate(Replicable object) {
        Teacher teacher = (Teacher) object;
        if (this.getUsesRoom()) {
            teacher.setUsedRoom(getUsedRoomId());
        } else {
            teacher.setNotUsedRoom();
        }
        super.replicate(teacher);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Teacher object = new Teacher("surname");
        replicate(object);
        return object;
    }

    @Override
    public String toString() {
        String result = "UsesRoom: " + getUsesRoom() + ", ";
        result += "UsedRoomId: " + getUsedRoomId();
        return super.toString() + ", " + result;
    }
}
