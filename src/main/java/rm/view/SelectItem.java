package rm.view;

import javafx.scene.layout.BorderPane;

public class SelectItem extends BorderPane {
    private Integer selectableId;

    public SelectItem(Integer selectableId) {
        setSelectableId(selectableId);
    }

    public void setSelectableId(Integer selectableId) {
        this.selectableId = selectableId;
    }

    public Integer getSelectableId() {
        return selectableId;
    }
}
