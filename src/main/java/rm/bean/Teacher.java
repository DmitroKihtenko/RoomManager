package main.java.rm.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.log4j.Logger;

class Teacher extends IdHolder {

    StringProperty name;
    StringProperty surname;
    StringProperty fatherName;
    IntegerProperty numberRoomWhichHasKey;

    Teacher() {

        name = new SimpleStringProperty("null");
        surname = new SimpleStringProperty("null");
        fatherName = new SimpleStringProperty("null");
        numberRoomWhichHasKey = new SimpleIntegerProperty(0);

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
     * Initialize neme change
     *
     * @param name string teacher`s name, not null, onli letter
     */
    public void setName(String name) {
        if (name == null || !checkString(name)) {
            logger.error("Incorrect value of the variable name" + name);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Teacher Error" +
                            "Incorrect value of the variable name:" + name
            );

        } else {
            this.name.set(name);
        }
    }

    /**
     * Initialize surname change
     *
     * @param surname string teacher`s surname, not null, onli letter
     */
    public void setSurname(String surname) {
        if (surname == null || !checkString(surname)) {
            logger.error("Incorrect value of the variable surname" + surname);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Teacher Error" +
                            "Incorrect value of the variable surname:" + surname
            );

        } else {
            this.surname.set(surname);
        }
    }

    /**
     * Initialize fatherName change
     *
     * @param fatherName string teacher`s fatherName, not null, onli letter
     */
    public void setFatherName(String fatherName) {
        if (fatherName == null || !checkString(fatherName)) {
            logger.error("Incorrect value of the variable fatherName" + fatherName);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Teacher Error" +
                            "Incorrect value of the variable fatherName" + fatherName
            );

        } else {
            this.fatherName.set(fatherName);
        }
    }

    /**
     * Initialize numberRoomWhichHasKey change
     *
     * @param numberRoomWhichHasKey int number room, more than 1
     */
    public void setNumberRoomWhichHasKey(int numberRoomWhichHasKey) {
        if (numberRoomWhichHasKey < 1) {
            logger.error("Incorrect value of the variable numberRoomWhichHasKey" + numberRoomWhichHasKey);

            throw new IllegalArgumentException(
                    "Class main.java.rm.bean.Teacher Error" +
                            "Incorrect value of the variable numberRoomWhichHasKey:" + numberRoomWhichHasKey
            );

        } else {
            this.numberRoomWhichHasKey.set(numberRoomWhichHasKey);
        }
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getFatherName() {
        return fatherName.get();
    }

    public StringProperty fatherNameProperty() {
        return fatherName;
    }

    public int getNumberRoomWhichHasKey() {
        return numberRoomWhichHasKey.get();
    }

    public IntegerProperty numberRoomWhichHasKeyProperty() {
        return numberRoomWhichHasKey;
    }

    public static Logger getLogger() {
        return logger;
    }
}
