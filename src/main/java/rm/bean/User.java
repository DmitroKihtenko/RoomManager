package rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

import java.util.Objects;

public class User {
    private static final Logger logger =
            Logger.getLogger(Datasource.class);

    private final StringProperty name;
    private String password;

    public User() {
        name = new SimpleStringProperty();
        setName("unidentified");
        password = "password";
    }

    public void setName(String name) {
        Assertions.isNotNull(name, "User name", logger);
        StringLogic.isWholeWord(name, "User name", logger);
        StringLogic.isVisible(name, "User name", logger);

        this.name.set(name);
    }

    public void setPassword(String password) {
        Assertions.isNotNull(password, "User password", logger);
        StringLogic.isVisible(password, "User password", logger);
        StringLogic.isWholeWord(password, "User password", logger);

        this.password = password;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        String result = "";

        if (getName() != null)
            result += "name - " + getName() + ", ";
        if (getPassword() != null)
            result += "password - " + getPassword() + ".";

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }

        User guest = (User) obj;
        Object s1 = Objects.requireNonNullElse(
                getName(), "");
        Object s2 = Objects.requireNonNullElse(guest.
                getName(), "");

        if (!s1.equals(s2)) {
            return false;
        }

        s1 = Objects.requireNonNullElse(
                getPassword(), "");
        s2 = Objects.requireNonNullElse(guest.
                getPassword(), "");

        if (!s1.equals(s2)) {
            return false;
        }

        return true;
    }
}