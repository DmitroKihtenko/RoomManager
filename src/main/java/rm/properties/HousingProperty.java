package rm.properties;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.service.Assertions;

public class HousingProperty implements XmlProperty {
    private static final Logger logger = Logger.getLogger(HousingProperty.class);
    private final IntegerProperty selectedHousing;

    public HousingProperty() {
        selectedHousing = new SimpleIntegerProperty(Integer.MIN_VALUE);
    }

    public void setSelectedHousing(Integer housingId) {
        Assertions.isNotNull(housingId, "Housing id", logger);

        selectedHousing.set(housingId);
    }

    public IntegerProperty selectedHousingProperty() {
        return selectedHousing;
    }

    public Integer getSelectedHousing() {
        if(selectedHousing.get() == Integer.MIN_VALUE) {
            return null;
        }
        return selectedHousing.get();
    }

    @Override
    public void read(Element element) throws DocumentException {
        logger.debug("Reading selected housing info");

        try {
            Element housingTag = element.element("housing-id");
            String value = housingTag.getText();
            if(value.equals("none")) {
                selectedHousing.set(Integer.MIN_VALUE);
            } else {
                selectedHousing.set(Integer.parseInt(value));
            }
        } catch (NumberFormatException e) {
            throw new DocumentException("Housing id value must be number");
        }
    }

    @Override
    public void write(Element element) {
        logger.debug("Writing selected housing info");

        String value = "none";
        if(selectedHousing.get() != Integer.MIN_VALUE) {
            value = String.valueOf(selectedHousing.get());
        }
        element = element.addElement("housing-id");
        element.addText(value);
    }
}
