package main.java.rm.bean;

import javafx.beans.property.*;
import org.apache.log4j.Logger;

class Room extends RoomInfo {
    private final static Logger logger = Logger.getLogger(Room.class);

    private final BooleanProperty isAvailable;
    private final IntegerProperty occupiedBy;

    public Room(String number, HousingInfo housing) {
        super(number, housing);
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
        if(!this.isAvailable.get()) {
            logger.warn("Attempt to get id of teacher that " +
                    "occupied room while room is available");

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
}
