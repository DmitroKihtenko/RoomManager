package main.java.rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.rm.service.NameLogic;
import org.apache.log4j.Logger;

public class TeacherInfo extends IdHolder {
    private final static Logger logger =
            Logger.getLogger(TeacherInfo.class);

    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty patronymic;

    public TeacherInfo(String name) {
        checkNameWord(name);
        this.name = new SimpleStringProperty(name);
        surname = new SimpleStringProperty("");
        patronymic = new SimpleStringProperty("");
    }

    /**
     * Checks name word for null value, for visibility on screen, containing whole word and at least one letter
     * @param nameWord name word string
     */
    protected void checkNameWord(String nameWord) {
        if(nameWord == null) {
            logger.error("Teacher name word parameter has null value");

            throw new IllegalArgumentException(
                    "Teacher name word parameter has null value"
            );
        }
        if(!NameLogic.isVisibleName(nameWord)) {
            logger.error("Teacher name word must contains " +
                    "visible symbols");

            throw new IllegalArgumentException(
                    "Teacher name word must contains visible symbols"
            );
        }
        if(!NameLogic.isWholeWord(nameWord)) {
            logger.error("Teacher name word must be one whole word");

            throw new IllegalArgumentException(
                    "Teacher name word must be one whole word"
            );
        }
        if(!NameLogic.containsLetter(nameWord)) {
            logger.error("Teacher name word must have at " +
                    "least one letter");

            throw new IllegalArgumentException(
                    "Teacher name word must have at " +
                            "least one letter"
            );
        }
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Teacher name setter
     * @param name name, checks in method {@link #checkNameWord(String)}
     */
    public void setName(String name) {
        checkNameWord(name);
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
     * @param surname surname, checks in method {@link #checkNameWord(String)}
     */
    public void setSurname(String surname) {
        if("".equals(surname)) {
            this.surname.set("");
        } else {
            checkNameWord(surname);
            this.surname.set(surname);
        }
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    /**
     * Teachers fatherName setter
     * @param patronymic patronymic, checks in method {@link #checkNameWord(String)}
     */
    public void setPatronymic(String patronymic) {
        if("".equals(patronymic)) {
            this.patronymic.set("");
        } else {
            checkNameWord(patronymic);
            this.patronymic.set(patronymic);
        }
    }
}
