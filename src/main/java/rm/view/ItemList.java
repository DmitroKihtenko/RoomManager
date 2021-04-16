package main.java.rm.view;

import javafx.beans.property.ObjectProperty;

public interface ItemList {
    /**
     * Puts items of list in container
     * @param itemsList list of items, not null
     */
    void setItems(Iterable<SelectItem> itemsList);

    /**
     * Sets as selected item by selectable id
     * @param selectableId selectable id of item
     * @return true if item was successfully selected
     */
    boolean setSelectedById(Integer selectableId);

    /**
     * Sets as selected item by index in list
     * @param index index of item in list
     * @return true if item was successfully selected
     */
    boolean setSelectedByIndex(int index);

    /**
     * Sets as selected null value
     * @return true if selected value was changed
     */
    boolean setNoSelected();

    /**
     * Sets the stylesheet applied to the selected list item
     * @param selectionStylesheet Stylesheet string in external form
     */
    void setSelectionStylesheet(String selectionStylesheet);

    /**
     * Adds a new item to the end of list
     * @param item new item, not null
     */
    void pushNewItem(SelectItem item);

    /**
     * Removes first match of item by its id
     * @param selectableId id of item to delete
     * @return true if item was deleted
     */
    boolean removeItem(Integer selectableId);

    /**
     * Indicates whether an item with specified id is contained in the list
     * @param selectableId id of item
     * @return index of item if it exists, otherwise return null
     */
    Integer contains(Integer selectableId);

    /**
     * Returns stylesheet string if it has been set earlier
     * @return Stylesheet string in external form, can be null
     */
    String getSelectionStylesheet();

    /**
     * Finds and returns first match item in item list by selectable id
     * @param selectableId selectable id of item to find
     * @return item by id if it was found, otherwise returns null
     */
    SelectItem getItemById(Integer selectableId);

    /**
     * Finds and returns item in item list by order index
     * @param index index of item to find
     * @return item by index if it was found, otherwise returns null
     */
    SelectItem getItemByIndex(int index);

    /**
     * Returns property of selected item, that changes if another element is selected
     * @return Object property of selected item
     */
    ObjectProperty<SelectItem> onSelectedProperty();

    /**
     * Returns the currently selected item
     * @return Currently selected item if it was selected, otherwise returns null
     */
    SelectItem getSelectedItem();

    /**
     * Returns size of current items list
     * @return size of current items list
     */
    int getSize();
}
