package rm.properties;

import org.dom4j.DocumentException;
import org.dom4j.Element;

public interface XmlProperty {
    void read(Element element) throws DocumentException;
    void write(Element element);
}
