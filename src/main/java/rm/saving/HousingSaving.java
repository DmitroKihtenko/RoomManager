package rm.saving;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Class used to save selected housing id
 */
public class HousingSaving implements XmlSaving {
    private static final Logger logger = Logger.getLogger(HousingSaving.class);
    private Integer selectedHousing;

    /**
     * Default constructor, sets as selected id null value
     */
    public HousingSaving() {
        selectedHousing = null;
    }

    /**
     * Setter for selected housing id
     * @param housingId selected housing id, can be null
     */
    public void setSelectedHousing(Integer housingId) {
        selectedHousing = housingId;
    }

    /**
     * Getter for selected housing id
     * @return selected housing id, can be null
     */
    public Integer getSelectedHousing() {
        return selectedHousing;
    }

    /**
     * Reads selected housing id from xml document element
     * @param element xml document element
     * @throws DocumentException if any error occurred while reading data from element
     */
    @Override
    public void read(Element element) throws DocumentException {
        logger.debug("Reading selected housing info");

        try {
            Element housingTag = element.element("housing-id");
            String value = housingTag.getText();
            if(value.equals("none")) {
                selectedHousing = null;
            } else {
                selectedHousing = Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            throw new DocumentException("Housing id value must be number");
        }
    }

    /**
     * Writes selected housing id to xml document element
     * @param element xml document element
     * */
    @Override
    public void write(Element element) {
        logger.debug("Writing selected housing info");

        String value = "none";
        if(selectedHousing != null) {
            value = String.valueOf(selectedHousing);
        }
        element = element.addElement("housing-id");
        element.addText(value);
    }
}
