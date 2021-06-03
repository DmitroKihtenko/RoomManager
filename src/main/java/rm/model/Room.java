package rm.model;

import javafx.beans.property.*;
import org.apache.log4j.Logger;

import java.util.Objects;

/**
 * Class successor of class {@link RoomInfo} that contains info about owner of room
 */
public class Room extends RoomInfo implements Cloneable {
    private final static Logger logger = Logger.getLogger(Room.class);

    private final BooleanProperty isAvailable;
    private final IntegerProperty occupiedBy;

    /**
     * Constructor, sets number of room as the default
     * @param number number of room
     */
    public Room(String number) {
        super(number);
        this.isAvailable = new SimpleBooleanProperty(true);
        this.occupiedBy = new SimpleIntegerProperty(0);
    }

    /**
     * Indicates whether the room is occupied by any teacher
     * @return true if room is occupied vy any teacher
     */
    public boolean isAvailable() {
        return isAvailable.get();
    }

    public BooleanProperty isAvailableProperty() {
        return isAvailable;
    }

    /**
     * Getter for id of teacher that occupies room
     * @return id of teacher if room is occupied by teacher, otherwise returns null
     */
    public Integer getOccupiedBy() {
        if (!this.isAvailable.get()) {
            return null;
        }
        if(this.occupiedBy.get() == IdHolder.DEFAULT_ID) {
            return null;
        }
        return this.occupiedBy.get();
    }

    /**
     * Used to track id property changes of teacher that occupies room
     * @return int property for java fx mvc
     */
    public IntegerProperty occupiedByProperty() {
        return occupiedBy;
    }

    /**
     * Sets that room is not occupied by any teacher
     */
    public void setNotOccupied() {
        this.isAvailable.set(true);
        this.occupiedBy.set(IdHolder.DEFAULT_ID);
    }

    /**
     * Setter for id of teacher that occupies room
     * @param teacherId id of teacher that occupies room
     */
    public void setOccupiedBy(int teacherId) {
        this.isAvailable.set(false);
        this.occupiedBy.set(teacherId);
    }

    @Override
    public void replicate(Replicable object) {
        Room room = (Room) object;
        if(getOccupiedBy() != null) {
            room.setOccupiedBy(getOccupiedBy());
        } else {
            room.setNotOccupied();
        }
        super.replicate(room);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Room room = new Room("0");
        replicate(room);
        return room;
    }

    @Override
    public String toString() {
        String result = "IsAvailable: " + isAvailable() + ", ";
        if (getOccupiedBy() != null) {
            result += "OccupiedBy: " + getOccupiedBy();
        }
        return super.toString() + ", " + result;
    }
}
