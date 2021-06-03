package rm.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

import java.util.Objects;

/**
 * Class that contains info about teacher
 */
public class TeacherInfo extends IdHolder implements Cloneable {
    private final static Logger logger =
            Logger.getLogger(TeacherInfo.class);

    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty patronymic;

    /**
     * Constructor, sets default value of teacher surname
     * @param surname teacher surname
     */
    public TeacherInfo(String surname) {
        name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty(null);
        patronymic = new SimpleStringProperty(null);
        setSurname(surname);
    }

    /**
     * Getter for teacher name
     * @return teacher name or null if name is not specified
     */
    public String getName() {
        return name.get();
    }

    /**
     * Used to track teacher name property
     * @return string property for java fx mvc
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Teacher name setter
     * @param name name, can be null
     */
    public void setName(String name) {
        if (name != null) {
            StringLogic.isVisible(name, "Teacher name", logger);
            StringLogic.isWholeWord(name, "Teacher name", logger);
            StringLogic.containsLetter(name, "Teacher name", logger);
        }

        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    /**
     * Used to track teacher surname property
     * @return string property for java fx mvc
     */
    public StringProperty surnameProperty() {
        return surname;
    }

    /**
     * Teacher surname setter
     * @param surname surname, not null
     */
    public void setSurname(String surname) {
        Assertions.isNotNull(surname, "Teacher surname", logger);
        StringLogic.isVisible(surname, "Teacher surname", logger);
        StringLogic.isWholeWord(surname, "Teacher surname",
                logger);
        StringLogic.containsLetter(surname, "Teacher surname",
                logger);

        this.surname.set(surname);
    }

    /**
     * Getter for teacher patronymic
     * @return teacher patronymic, can be null
     */
    public String getPatronymic() {
        return patronymic.get();
    }

    /**
     * Used to track teacher patronymic property
     * @return string property for java fx mvc
     */
    public StringProperty patronymicProperty() {
        return patronymic;
    }

    /**
     * Teachers patronymic setter
     * @param patronymic patronymic, can be null
     */
    public void setPatronymic(String patronymic) {
        if (patronymic != null) {
            StringLogic.isVisible(patronymic, "Teacher patronymic",
                    logger);
            StringLogic.isWholeWord(patronymic, "Teacher patronymic",
                    logger);
            StringLogic.containsLetter(patronymic,
                    "Teacher patronymic", logger);
        }

        this.patronymic.set(patronymic);
    }

    @Override
    public void replicate(Replicable object) {
        TeacherInfo newTeacherInfo = (TeacherInfo) object;
        newTeacherInfo.setName(getName());
        newTeacherInfo.setSurname(getSurname());
        newTeacherInfo.setPatronymic(getPatronymic());
        super.replicate(newTeacherInfo);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TeacherInfo object = new TeacherInfo("teacher");
        replicate(object);
        return object;
    }

    @Override
    public String toString() {
        String result = "Name: " + getName() + ", ";
        result += "Surname: " + getSurname() + ", ";
        result += "Patronymic: " + getPatronymic();
        return super.toString() + ", " + result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TeacherInfo)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }

        TeacherInfo guest = (TeacherInfo) obj;
        Object s1 = Objects.requireNonNullElse(
                getName(), "");
        Object s2 = Objects.requireNonNullElse(guest.
                getName(), "");
        if (!s1.equals(s2)) {
            return false;
        }
        s1 = Objects.requireNonNullElse(
                getPatronymic(), "");
        s2 = Objects.requireNonNullElse(guest.
                getPatronymic(), "");
        if(!s1.equals(s2)) {
            return false;
        }
        return getSurname().equals(guest.getSurname());
    }
}
