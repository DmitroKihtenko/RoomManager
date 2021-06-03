package rm.model;

import javafx.beans.property.*;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Room extends RoomInfo implements Cloneable {
    private final static Logger logger = Logger.getLogger(Room.class);

    private final BooleanProperty isAvailable;
    private final IntegerProperty occupiedBy;

    public Room(String number) {
        super(number);
        this.isAvailable = new SimpleBooleanProperty(true);
        this.occupiedBy = new SimpleIntegerProperty(0);
    }

    public boolean isAvailable() {
        return isAvailable.get();
    }

    public BooleanProperty isAvailableProperty() {
        return isAvailable;
    }

    public Integer getOccupiedBy() {
        if (!this.isAvailable.get()) {
            return null;
        }
        return this.occupiedBy.get();
    }

    public IntegerProperty occupiedByProperty() {
        return occupiedBy;
    }

    public void setNotOccupied() {
        this.isAvailable.set(true);
        this.occupiedBy.set(0);
    }

    public void setOccupiedBy(int teacherId) {
        this.isAvailable.set(false);
        this.occupiedBy.set(teacherId);
    }

    @Override
    public Replicable replicate(Replicable object) {
        Room room = (Room) object;
        if(getOccupiedBy() != null) {
            room.setOccupiedBy(getOccupiedBy());
        } else {
            room.setNotOccupied();
        }
        super.replicate(room);
        return room;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return replicate(new Room("name"));
    }

    @Override
    public String toString() {
        String result = "";

        result += "isAvailable - " + isAvailable() + ", ";

        if (getOccupiedBy() != null) {
            result += "occupiedBy - " + getOccupiedBy() + ". ";
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
        if (!(obj instanceof Room)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }

        Room guest = (Room) obj;
        Object s1 = Objects.requireNonNullElse(
                getOccupiedBy(), Boolean.FALSE);
        Object s2 = Objects.requireNonNullElse(guest.
                getOccupiedBy(), Boolean.FALSE);

        if (!s1.equals(s2)) {
            return false;
        }

        return true;
    }
}
