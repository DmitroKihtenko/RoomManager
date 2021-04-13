package main.java.rm.view;

import com.sun.javafx.scene.EventHandlerProperties;
import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

public class ItemList extends VBox {
    private final static Logger logger =
            Logger.getLogger(ItemList.class);

    private final ObjectProperty<SelectItem> onSelected;
    private String selectedStyle;

    public ItemList() {
        onSelected = new SimpleObjectProperty<>(null);
    }

    protected void manageSelection(SelectItem item) {
        SelectItem lastSelected = onSelected.get();
        ObservableList<String> itemStyles =
                item.getStylesheets();
        ObservableList<String> selectedStyles;

        if(lastSelected != null && selectedStyle != null) {
            selectedStyles = lastSelected.getStylesheets();
            if (!selectedStyles.isEmpty()) {
                selectedStyles.remove(selectedStyle);
            }
        }

        onSelected.set(item);

        if(selectedStyle != null) {
            itemStyles.add(selectedStyle);
        }
    }

    public String getSelectedStyle() {
        return selectedStyle;
    }

    public void setSelectedStyle(String selectedStyle) {
        if(selectedStyle == null) {
            logger.error("Selected style parameter has null value");

            throw new IllegalArgumentException(
                    "Selected style parameter has null value"
            );
        }
        this.selectedStyle = selectedStyle;
    }

    public void setItems(Iterable<SelectItem> itemsList) {
        if(itemsList == null) {
            logger.error("Items list parameter has null value");

            throw new IllegalArgumentException(
                    "Items list parameter has null value"
            );
        }
        ObservableList<Node> children = this.getChildren();
        String parentStylesheet = null;
        if(this.getStylesheets().size() != 0) {
            parentStylesheet = getStylesheets().
                    get(getStylesheets().size() - 1);
        }

        for(SelectItem item : itemsList) {
            if(parentStylesheet != null && item.getStylesheets().
                    isEmpty()) {
                item.getStylesheets().add(parentStylesheet);
            }
            item.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    manageSelection(item);
                }
            });
            children.add(item);
        }
    }

    public SelectItem getItemById(Integer selectableId) {
        ObservableList<Node> children = this.getChildren();
        SelectItem item;
        for(Node node : children) {
            item = (SelectItem) node;
            if(item.getSelectableId().equals(selectableId)) {
                return item;
            }
        }
        return null;
    }

    public SelectItem getItemByIndex(int index) {
        return (SelectItem) this.getChildren().get(index);
    }

    public ObjectProperty<SelectItem> onSelectedProperty() {
        return this.onSelected;
    }

    public SelectItem getSelectedItem() {
        return this.onSelected.get();
    }
}
