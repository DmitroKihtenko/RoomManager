package main.java.rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.rm.service.Assertions;
import main.java.rm.service.StringLogic;
import org.apache.log4j.Logger;

public class TeacherInfo extends IdHolder {
    private final static Logger logger =
            Logger.getLogger(TeacherInfo.class);

    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty patronymic;

    public TeacherInfo(String name) {
        this.name = new SimpleStringProperty();
        surname = new SimpleStringProperty(null);
        patronymic = new SimpleStringProperty(null);
        setName(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Teacher name setter
     * @param name name, not null
     */
    public void setName(String name) {
        Assertions.isNotNull(name, "Teacher name", logger);
        StringLogic.isVisible(name, "Teacher name", logger);
        StringLogic.isWholeWord(name, "Teacher name", logger);
        StringLogic.containsLetter(name, "Teacher name", logger);

        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    /**
     * Teacher surname setter
     * @param surname surname, not null
     */
    public void setSurname(String surname) {
        if(surname != null) {
            StringLogic.isVisible(surname, "Teacher surname", logger);
            StringLogic.isWholeWord(surname, "Teacher surname",
                    logger);
            StringLogic.containsLetter(surname, "Teacher surname",
                    logger);
        }

        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    /**
     * Teachers fatherName setter
     * @param patronymic patronymic, not null
     */
    public void setPatronymic(String patronymic) {
        if(surname != null) {
            StringLogic.isVisible(patronymic, "Teacher patronymic",
                    logger);
            StringLogic.isWholeWord(patronymic, "Teacher patronymic",
                    logger);
            StringLogic.containsLetter(patronymic,
                    "Teacher patronymic", logger);
        }

        this.patronymic.set(patronymic);
    }
}
