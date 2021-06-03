package rm.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

import java.util.Objects;

/**
 * Class that contains data about housing in university
 */
public class HousingInfo extends IdHolder implements Cloneable {
    private static final Logger logger =
            Logger.getLogger(HousingInfo.class);

    private final StringProperty name;

    /**
     * Constructor that sets housing name as default
     * @param name housing name
     */
    public HousingInfo(String name) {
        this.name = new SimpleStringProperty();
        setName(name);
    }

    /**
     * Getter for housing name
     * @return housing name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Used to track name property changes
     * @return string property for java fx mvc
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Setter for housing name
     * @param name string value of name, not null
     */
    public void setName(String name) {
        Assertions.isNotNull(name, "Housing name", logger);
        StringLogic.isVisible(name, "Housing name", logger);
        StringLogic.isWholeWord(name, "Housing name", logger);

        this.name.set(name);
    }

    @Override
    public void replicate(Replicable object) {
        HousingInfo housing = (HousingInfo) object;
        housing.setName(getName());
        super.replicate(housing);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HousingInfo object = new HousingInfo("Name");
        replicate(object);
        return object;
    }

    @Override
    public String toString() {
        String result =  "Name: " + getName();
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
        if (!(obj instanceof HousingInfo)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }

        HousingInfo guest = (HousingInfo) obj;
        return getName().equals(guest.getName());
    }
}
