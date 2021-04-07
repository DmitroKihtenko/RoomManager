package main.java.rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.rm.service.NameLogic;
import org.apache.log4j.Logger;

public class HousingInfo extends IdHolder {
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
        if(name == null) {
            logger.error("Housing name parameter has null value");

            throw new IllegalArgumentException(
                    "Housing name parameter has null value"
            );
        }
        if(!NameLogic.isVisibleName(name)) {
            logger.error("Housing name must contains visible symbols");

            throw new IllegalArgumentException(
                    "Housing name must contains visible symbols"
            );
        }
        if(!NameLogic.isWholeWord(name)) {
            logger.error("Housing name must be whole word");

            throw new IllegalArgumentException(
                    "Housing name must be whole word"
            );
        }

        this.name.set(name);
    }
}
