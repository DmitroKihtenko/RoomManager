package rm.saving;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.model.Datasource;
import rm.service.Assertions;

public class DatasourceSaving implements XmlSaving {
    private final static Logger logger =
            Logger.getLogger(DatasourceSaving.class);

    private Datasource datasource;

    public DatasourceSaving() {
        datasource = new Datasource();
    }

    public DatasourceSaving(Datasource datasource) {
        setDatasource(datasource);
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        Assertions.isNotNull(datasource, "Datasource", logger);

        this.datasource = datasource;
    }

    @Override
    public void read(Element element) throws DocumentException {
        logger.debug("Reading datasource info");

        Element sourceTag = element.element("datasource");
        Element currentTag;
        for(Object current : sourceTag.elements()) {
            currentTag = (Element) current;
            if(current != null) {
                switch (currentTag.getName()) {
                    case "source":
                        datasource.setSource(currentTag.getText());
                        break;
                    case "url":
                        datasource.setUrl(currentTag.getText());
                        break;
                    case "port":
                        datasource.setPort(currentTag.getText());
                        break;
                    case "database-name":
                        datasource.setDatabaseName(currentTag.
                                getText());
                        break;
                    default:
                        throw new DocumentException("Invalid tag" +
                                currentTag.getName() +
                                " in datasource property");
                }
            }
        }
    }

    @Override
    public void write(Element element) {
        logger.debug("Writing datasource info");

        Element sourceTag = element.addElement("datasource");
        Element current = sourceTag.addElement("source");
        current.addText(datasource.getSource());
        current = sourceTag.addElement("url");
        current.addText(datasource.getUrl());
        current = sourceTag.addElement("port");
        current.addText(datasource.getPort());
        if(datasource.getDatabaseName() != null) {
            current = sourceTag.addElement("database-name");
            current.addText(datasource.getDatabaseName());
        }
    }
}
