package rm.bean;

import javafx.beans.property.*;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

import java.util.Objects;

public class RoomInfo extends IdHolder implements Cloneable {
    private static final Logger logger =
            Logger.getLogger(RoomInfo.class);

    private final StringProperty number;
    private final IntegerProperty housingId;
    private final BooleanProperty isUsed;
    private final StringProperty notUsedReason;

    /**
     * Constructor for RoomInfo class objects
     *
     * @param number number of room, similarly parameter to setter {@link #setNumber(String)}
     */
    public RoomInfo(String number) {
        this.number = new SimpleStringProperty();
        setNumber(number);
        housingId = new SimpleIntegerProperty(Integer.MIN_VALUE);
        this.isUsed = new SimpleBooleanProperty(true);
        this.notUsedReason = new SimpleStringProperty(null);
    }

    public void setIsUsed(int id) {
        super.setId(id);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    /**
     * Setter for room number
     *
     * @param number number of room, not null, must be whole word and visible on screen
     */
    public void setNumber(String number) {
        Assertions.isNotNull(number, "Room number", logger);
        StringLogic.isVisible(number, "Room number", logger);
        StringLogic.isWholeWord(number, "Room number", logger);

        this.number.set(number);
    }

    /**
     * Returns id of housing object in which this room located
     *
     * @return if room located in some housing returns its id otherwise returns null
     */
    public Integer getHousingId() {
        if (housingId.get() == Integer.MIN_VALUE) {
            return null;
        }
        return housingId.get();
    }

    /**
     * Setter for id of housing object
     *
     * @param housingId id of housing object
     */
    public void setHousingId(Integer housingId) {
        this.housingId.set(Objects.requireNonNullElse(housingId, Integer.MIN_VALUE));
    }

    public boolean isUsed() {
        return isUsed.get();
    }

    public BooleanProperty isUsedProperty() {
        return isUsed;
    }

    /**
     * Sets that room can be used
     */
    public void setUsed() {
        this.isUsed.set(true);
        this.notUsedReason.set(null);
    }

    /**
     * Getter for reason if room can't be used
     *
     * @return if room can't be used returns reason otherwise returns empty string
     */
    public String getNotUsedReason() {
        return notUsedReason.get();
    }

    public StringProperty notUsedReasonProperty() {
        return notUsedReason;
    }

    public IntegerProperty housingIdProperty() {
        return housingId;
    }

    /**
     * Sets that room can't be used for some reason
     *
     * @param notUsedReason the reason why room is not used, not null, must be visible on screen
     */
    public void setNotUsedReason(String notUsedReason) {
        if(notUsedReason != null) {
            StringLogic.isVisible(notUsedReason,
                    "Not used reason", logger);
            this.isUsed.set(false);
            this.notUsedReason.set(notUsedReason);
        } else {
            setUsed();
        }
    }

    @Override
    public Replicable replicate(Replicable object) {
        RoomInfo newRoomInfo = (RoomInfo) object;
        newRoomInfo.setNumber(getNumber());
        newRoomInfo.setHousingId(this.housingId.get());
        if (!this.isUsed()) {
            newRoomInfo.setNotUsedReason(this.getNotUsedReason());
        } else {
            newRoomInfo.setUsed();
        }
        super.replicate(newRoomInfo);
        return newRoomInfo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return replicate(new RoomInfo("number"));
    }

    @Override
    public String toString() {
        String result = "";

        if (getNumber() != null) {
            result += "number - " + getNumber() + ", ";
        }
        if (getHousingId() != null) {
            result += "housingId - " + getHousingId() + ", ";
        }

        result += "isUsed - " + isUsed() + ", ";

        if (getNotUsedReason() != null) {
            result += "notUsedReason - " + getNotUsedReason() + ". ";
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
        if (!(obj instanceof RoomInfo)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }

        RoomInfo guest = (RoomInfo) obj;
        Object s1 = Objects.requireNonNullElse(
                getNotUsedReason(), "");
        Object s2 = Objects.requireNonNullElse(guest.
                getNotUsedReason(), "");

        if (!s1.equals(s2)) {
            return false;
        }

        s1 = Objects.requireNonNullElse(
                getHousingId(), Integer.MIN_VALUE);
        s2 = Objects.requireNonNullElse(guest.
                getHousingId(), Integer.MIN_VALUE);

        if (!s1.equals(s2)) {
            return false;
        }

        return number.get().equals(guest.getNumber());
    }
}
