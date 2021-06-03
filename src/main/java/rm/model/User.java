package rm.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

/**
 * Class that contains information about user for connecting to database
 */
public class User {
    private static final Logger logger =
            Logger.getLogger(Datasource.class);

    private final StringProperty name;
    private String password;

    /**
     * Default constructor that sets default username as root
     */
    public User() {
        name = new SimpleStringProperty();
        setName("root");
        password = null;
    }

    /**
     * Setter for username
     * @param name username value, not null
     */
    public void setName(String name) {
        Assertions.isNotNull(name, "User name", logger);
        StringLogic.isWholeWord(name, "User name", logger);
        StringLogic.isVisible(name, "User name", logger);

        this.name.set(name);
    }

    /**
     * Setter for user password
     * @param password user password value, can be null
     */
    public void setPassword(String password) {
        if(password != null) {
            StringLogic.isVisible(password, "User password", logger);
            StringLogic.isWholeWord(password, "User password", logger);
        }

        this.password = password;
    }

    /**
     * Getter for username
     * @return username value, not null
     */
    public String getName() {
        return name.get();
    }

    /**
     * Used to track username property changes
     * @return string property for java fx mvc
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Getter for user password
     * @return user password value, can be null
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        String result = "Name: " + getName() + ", ";
        result += "Password: " + getPassword();
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
        if (!(obj instanceof User)) {
            return false;
        }
        User guest = (User) obj;
        return getName().equals(guest.getName()) &&
                getPassword().equals(guest.getPassword());
    }
}