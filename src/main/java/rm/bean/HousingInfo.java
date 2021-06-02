package rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

import java.util.Objects;

public class HousingInfo extends IdHolder implements Cloneable {
    private static final Logger logger =
            Logger.getLogger(HousingInfo.class);

    private final StringProperty name;

    public HousingInfo(String name) {
        this.name = new SimpleStringProperty();
        setName(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        Assertions.isNotNull(name, "Housing name", logger);
        StringLogic.isVisible(name, "Housing name", logger);
        StringLogic.isWholeWord(name, "Housing name", logger);

        this.name.set(name);
    }

    @Override
    public Replicable replicate(Replicable object) {
        HousingInfo housing = (HousingInfo) object;
        housing.setName(getName());
        super.replicate(housing);
        return housing;
    }

    @Override
    public Object clone() {
        return replicate(new HousingInfo("Name"));
    }

    @Override
    public String toString() {
        String result = "";

        if (getName() != null) {
            result += "name - " + getName() + ". ";
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
        if (!(obj instanceof HousingInfo)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }

        HousingInfo guest = (HousingInfo) obj;
        Object s1 = Objects.requireNonNullElse(
                getName(), "");
        Object s2 = Objects.requireNonNullElse(guest.
                getName(), "");

        if (!s1.equals(s2)) {
            return false;
        }

        return true;
    }
}
