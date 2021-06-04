package rm.saving;

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

/**
 * Class used to manage any xml parameters saving in different xml files
 */
public class XmlSavingsHandler {
    private static final Logger logger =
            Logger.getLogger(XmlSavingsHandler.class);
    private static final String rootElementName = "properties";

    private final TreeMap<String, LinkedList<XmlSaving>> properties;

    /**
     * Default constructor, as default does not contain any files to save data
     */
    public XmlSavingsHandler() {
        properties = new TreeMap<>();
    }

    /**
     * Returns all file paths that can be used to write or save data
     * @return list of string xml file paths
     */
    public Iterable<String> getPaths() {
        return properties.keySet();
    }

    /**
     * Writes all data to specified xml file path if this path was registered earlier
     * @param path string value of file path
     * @throws IllegalArgumentException if path was not registered in this object
     */
    public void writeForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        if(properties.containsKey(path)) {
            write(path);
        } else {
            logger.error("There are no " +
                    "savings objects for writing to file " + path);

            throw new IllegalArgumentException("There are no " +
                    "savings objects for writing to file " + path);
        }
    }

    /**
     * Reads all data from specified xml file path if this path was registered earlier
     * @param path string value of file path
     * @throws IllegalArgumentException if path was not registered in this object
     */
    public void readForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        if(properties.containsKey(path)) {
            read(path);
        } else {
            logger.error("There are no " +
                    "savings objects for reading from file " +
                    path);

            throw new IllegalArgumentException("There are no " +
                    "savings objects for reading from file " +
                    path);
        }
    }

    /**
     * Writes all data for all registered xml files
     */
    public void write() {
        for(String path : properties.keySet()) {
            write(path);
        }
    }

    /**
     * Reads all data from all registered xml files
     */
    public void read() {
        for(String path : properties.keySet()) {
            read(path);
        }
    }

    /**
     * Registers xml file path and all xml savings to this file
     * @param path xml file path
     * @param savings list of xml savings
     */
    public void propertiesForPath(String path,
                                  XmlSaving... savings) {
        Assertions.isNotNull(path, "Xml file path", logger);
        Assertions.isNotNull(savings, "Savings list", logger);

        this.properties.remove(path);
        LinkedList<XmlSaving> propertiesList = new LinkedList<>();
        for(XmlSaving property : savings) {
            Assertions.isNotNull(property, "Saving", logger);

            propertiesList.add(property);
        }
        this.properties.put(path, propertiesList);
    }

    /**
     * Removes all savings for specified xml path or doing nothing if path is not registered
     * @param path xml file path
     */
    public void discardForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        properties.remove(path);
    }

    public boolean existsForPath(String path) {
        Assertions.isNotNull(path, "Xml file path", logger);

        if(properties.containsKey(path)) {
            File file = new File(path);
            try {
                return file.exists();
            } catch (SecurityException e) {
                logger.warn("File system access error when trying " +
                        "to find out whether the file " +
                        file.getAbsolutePath() + " exists");
                return false;
            }
        } else {
            logger.error("There are no " +
                    "savings objects for reading from file " +
                    path);

            throw new IllegalArgumentException("There are no " +
                    "savings objects for reading from file " +
                    path);
        }
    }

    /**
     * Method that reads data from some specified path without check of file registration
     * @param key xml file path
     */
    private void read(String key) {
        File file = new File(key);
        logger.debug("Reading savings from file " +
                file.getAbsolutePath());
        if(!file.exists()) {
            logger.warn("Savings file " +
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
                        for(XmlSaving property : properties.
                                get(key)) {
                            try {
                                property.read(tag);
                            } catch (DocumentException e) {
                                logger.warn("Saving reading error: "
                                        + e.getMessage());
                            } catch (NullPointerException e) {
                                logger.warn("Saving reading error: "
                                        + "Expected element is not" +
                                        " exist");
                            }
                        }
                    }
                } catch (DocumentException e) {
                    logger.warn("Saving reading error: " +
                            e.getMessage());
                } catch (Exception e) {
                    logger.warn("Unknown error: " + Objects.
                            requireNonNullElse(e.getMessage(),
                                    e.toString()));
                }
            }
        }
    }

    /**
     * Method that writes data for some specified path without check of file registration
     * @param key xml file path
     */
    public void write(String key) {
        File file = new File(key);
        logger.debug("Writing savings to file " +
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
                for(XmlSaving property : properties.get(key)) {
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
