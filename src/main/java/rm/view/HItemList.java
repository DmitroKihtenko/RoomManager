package main.java.rm.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class HItemList extends HBox implements ItemList {
    private final static Logger logger =
            Logger.getLogger(HItemList.class);

    private final ObjectProperty<SelectItem> onSelected;
    private String selectedStyle;

    public HItemList() {
        onSelected = new SimpleObjectProperty<>(null);
    }

    protected void manageSelection(SelectItem item) {
        SelectItem lastSelected = onSelected.get();
        ObservableList<String> itemStyles = null;
        if(item != null) {
            itemStyles = item.getStylesheets();
        }
        ObservableList<String> selectedStyles;

        if(lastSelected != null && selectedStyle != null) {
            selectedStyles = lastSelected.getStylesheets();
            if (!selectedStyles.isEmpty()) {
                selectedStyles.remove(selectedStyle);
            }
        }

        onSelected.set(item);

        if(item != null && selectedStyle != null) {
            itemStyles.add(selectedStyle);
        }
    }

    @Override
    public String getSelectedStylesheet() {
        return selectedStyle;
    }

    @Override
    public void setSelectedStylesheet(String selectedStyle) {
        if(selectedStyle == null) {
            logger.error("Selected stylesheet parameter has " +
                    "null value");

            throw new IllegalArgumentException(
                    "Selected stylesheet parameter has null value"
            );
        }
        this.selectedStyle = selectedStyle;
    }

    @Override
    public void pushNewItem(SelectItem item) {
        this.getChildren().add(item);
    }

    @Override
    public boolean removeItem(Integer selectableId) {
        SelectItem item;
        Iterator<Node> nodeIterator = this.getChildren().iterator();

        while(nodeIterator.hasNext()) {
            item = (SelectItem) nodeIterator.next();
            if(item.getSelectableId().equals(selectableId)) {
                if(onSelected.get() != null &&
                        onSelected.get().getSelectableId().
                                equals(selectableId)) {
                    setNoSelected();
                }
                nodeIterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer contains(Integer selectableId) {
        SelectItem item;
        int counter = 0;
        for(Node node : this.getChildren()) {
            item = (SelectItem) node;
            if(item.getSelectableId().equals(selectableId)) {
                return counter;
            }
            counter++;
        }
        return null;
    }

    @Override
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
            item.onMouseClickedProperty().set(mouseEvent -> manageSelection(item));
            children.add(item);
        }
    }

    @Override
    public boolean setSelectedById(Integer selectableId) {
        ObservableList<Node> items = this.getChildren();
        SelectItem item;

        for(Node node : items) {
            item = (SelectItem) node;
            if(item.getSelectableId().equals(selectableId)) {
                onSelected.set(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setSelectedByIndex(int index) {
        ObservableList<Node> items = this.getChildren();
        if(index < 0 || index >= items.size()) {
            return false;
        }
        SelectItem item;
        int counter = 0;

        for(Node node : items) {
            if(counter == index) {
                item = (SelectItem) node;
                onSelected.set(item);
                return true;
            }
            counter++;
        }
        return false;
    }

    @Override
    public boolean setNoSelected() {
        if(onSelected.get() == null) {
            return false;
        }
        onSelected.set(null);
        return true;
    }

    @Override
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

    @Override
    public SelectItem getItemByIndex(int index) {
        return (SelectItem) this.getChildren().get(index);
    }

    @Override
    public ObjectProperty<SelectItem> onSelectedProperty() {
        return this.onSelected;
    }

    @Override
    public SelectItem getSelectedItem() {
        return this.onSelected.get();
    }

    @Override
    public int getSize() {
        return this.getChildren().size();
    }
}
