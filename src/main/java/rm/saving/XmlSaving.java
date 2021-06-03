package rm.saving;

import org.dom4j.DocumentException;
import org.dom4j.Element;

public interface XmlSaving {
    void read(Element element) throws DocumentException;
    void write(Element element);
}
