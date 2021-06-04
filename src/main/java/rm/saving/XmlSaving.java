package rm.saving;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Interface that describes abstraction of objects that saves data about some other object in xml format
 */
public interface XmlSaving {
    /**
     * Reads any data from specified xml document element
     * @param element xml document element
     * @throws DocumentException if any error occurred while reading data from element
     */
    void read(Element element) throws DocumentException;

    /**
     * Writes any data to specified xml document element
     * @param element xml document element
     */
    void write(Element element);
}
