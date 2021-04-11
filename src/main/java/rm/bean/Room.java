package main.java.rm.bean;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.apache.log4j.Logger;

class Room extends IdHolder {

    IntegerProperty roomNum;
    BooleanProperty useEmpty;
    StringProperty corps;
    StringProperty whoTookTheKeyOrFree;
    BooleanProperty roomAvailable;

    Room() {

        roomNum = new SimpleIntegerProperty(0);
        usyEmpty = new SimpleBooleanProperty(false);
        corps = new SimpleStringProperty("null");
        whoTookTheKeyOrFree = new SimpleStringProperty("null");
        roomAvailable = new SimpleBooleanProperty(false);

    }

    private static final Logger logger = Logger.getLogger(Room.class);

    /**
     * Check string for invalid chatacters.
     *
     * @param check string to be ckecked
     */
    public boolean checkString(String check) {
        for (int i = 0; i < check.length(); i++) {
            if (!Character.isLetter(check.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Initialize roomNum change
     *
     * @param roomNum int number room, more than 1
     */
    public void setRoomNum(int roomNum) {
        if (roomNum < 1) {
            logger.error("Invalid room number" + roomNum);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Room Error" +
                            "Invalid room number:" + roomNum
            );

        } else {
            this.roomNum.set(roomNum);
        }
    }

    /**
     * Initialize usyEmpty change
     *
     * @param usyEmpty boolean empty room or usy
     */
    public void setUsyEmpty(boolean usyEmpty) {
        this.usyEmpty.set(usyEmpty);
    }

    /**
     * Initialize corps change
     *
     * @param corps string room corps, not null, onli letter
     */
    public void setCorps(String corps) {
        if (corps == null || !checkString(corps)) {
            logger.error("Incorrect value of the variable corps" + corps);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Room Error" +
                            "Incorrect value of the variable corps:" + corps
            );

        } else {
            this.corps.set(corps);
        }
    }

    /**
     * Initialize whoTookTheKeyOrFree change
     *
     * @param whoTookTheKeyOrFree string Who took the key or free, not null, onli letter
     */
    public void setWhoTookTheKeyOrFree(String whoTookTheKeyOrFree) {
        if (whoTookTheKeyOrFree == null || !checkString(whoTookTheKeyOrFree)) {
            logger.error("Incorrect value of the variable whoTookTheKeyOrFree" + whoTookTheKeyOrFree);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Room Error" +
                            "Incorrect value of the variable whoTookTheKeyOrFree:" + whoTookTheKeyOrFree
            );

        } else {
            this.whoTookTheKeyOrFree.set(whoTookTheKeyOrFree);
        }
    }

    /**
     * Initialize roomAvailable change
     *
     * @param roomAvailable boolean available room or no
     */
    public void setRoomAvailable(boolean roomAvailable) {
        this.roomAvailable.set(roomAvailable);
    }

    public int getRoomNum() {
        return roomNum.get();
    }

    public IntegerProperty roomNumProperty() {
        return roomNum;
    }

    public boolean isUsyEmpty() {
        return usyEmpty.get();
    }

    public BooleanProperty usyEmptyProperty() {
        return usyEmpty;
    }

    public String getCorps() {
        return corps.get();
    }

    public StringProperty corpsProperty() {
        return corps;
    }

    public String getWhoTookTheKeyOrFree() {
        return whoTookTheKeyOrFree.get();
    }

    public StringProperty whoTookTheKeyOrFreeProperty() {
        return whoTookTheKeyOrFree;
    }

    public boolean isRoomAvailable() {
        return roomAvailable.get();
    }

    public BooleanProperty roomAvailableProperty() {
        return roomAvailable;
    }

    public static Logger getLogger() {
        return logger;
    }
}
