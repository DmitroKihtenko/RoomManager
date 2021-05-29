package rm.properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import rm.service.Assertions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeMap;

public class XmlDataHandler {
    private static final Logger logger =
            Logger.getLogger(XmlDataHandler.class);
    private static final String rootElementName = "properties";

    private final TreeMap<String, LinkedList<XmlProperty>> properties;
    public XmlDataHandler() {
        properties = new TreeMap<>();
    }

    public Iterable<String> getPaths() {
        return properties.keySet();
    }

    public void writeForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        if(properties.containsKey(path)) {
            write(path);
        } else {
            logger.error("There are no " +
                    "properties objects for writing to file " + path);

            throw new IllegalArgumentException("There are no " +
                    "properties objects for writing to file " + path);
        }
    }

    public void readForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        if(properties.containsKey(path)) {
            read(path);
        } else {
            logger.error("There are no " +
                    "properties objects for reading from file " +
                    path);

            throw new IllegalArgumentException("There are no " +
                    "properties objects for reading from file " +
                    path);
        }
    }

    public void write() {
        for(String path : properties.keySet()) {
            write(path);
        }
    }

    public void read() {
        for(String path : properties.keySet()) {
            read(path);
        }
    }

    public void propertiesForPath(String path,
                                  XmlProperty ... properties) {
        Assertions.isNotNull(path, "Xml file path", logger);
        Assertions.isNotNull(properties, "Properties list", logger);

        this.properties.remove(path);
        LinkedList<XmlProperty> propertiesList = new LinkedList<>();
        for(XmlProperty property : properties) {
            Assertions.isNotNull(property, "Property", logger);

            propertiesList.add(property);
        }
        this.properties.put(path, propertiesList);
    }

    public void discardForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        properties.remove(path);
    }

    private void read(String key) {
        File file = new File(key);
        logger.debug("Reading properties from file " +
                file.getAbsolutePath());
        if(!file.exists()) {
            logger.warn("Properties file " +
                    file.getAbsolutePath() + " does not exist");
        } else {
            if(!file.canRead()) {
                logger.error("Reading error: file exists but " +
                        "reading access denied");
            } else {
                try {
                    SAXReader saxReader = new SAXReader();
                    Document document = saxReader.read(file);
                    Element tag = document.getRootElement();
                    if(!tag.getName().equals(rootElementName)) {
                        logger.warn("Required root element with " +
                                "name " + rootElementName);
                    } else {
                        for(XmlProperty property : properties.
                                get(key)) {
                            try {
                                property.read(tag);
                            } catch (DocumentException e) {
                                logger.warn("Property reading error: "
                                        + e.getMessage());
                            } catch (NullPointerException e) {
                                logger.warn("Property reading error: "
                                        + "Expected element is not" +
                                        " exist");
                            }
                        }
                    }
                } catch (DocumentException e) {
                    logger.warn("Property reading error: " +
                            e.getMessage());
                } catch (Exception e) {
                    logger.warn("Unknown error: " + Objects.
                            requireNonNullElse(e.getMessage(),
                                    e.toString()));
                }
            }
        }
    }

    public void write(String key) {
        File file = new File(key);
        logger.debug("Writing properties to file " +
                file.getAbsolutePath());
        try {
            file.createNewFile();
            if(!file.canWrite()) {
                logger.error("Writing error: file exists but " +
                        "writing access denied");
            } else {
                Document document = DocumentHelper.
                        createDocument();
                Element root = document.
                        addElement(rootElementName);
                for(XmlProperty property : properties.get(key)) {
                    property.write(root);
                }
                OutputFormat format = OutputFormat.
                        createPrettyPrint();
                XMLWriter writer;
                try {
                    writer = new XMLWriter(new FileOutputStream(file),
                            format);
                    writer.write(document);
                } catch (IOException e) {
                    logger.warn("Unknown error while writing " +
                            "file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.warn("Creating file " +
                    file.getAbsolutePath() + "error. " +
                    e.getMessage());
        } catch (Exception e) {
            logger.warn("Unknown error: " + Objects.
                    requireNonNullElse(e.getMessage(),
                            e.toString()));
        }
    }
}
