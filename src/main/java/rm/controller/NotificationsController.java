package rm.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import rm.model.Notifications;
import rm.service.Beans;

public class NotificationsController {
    @FXML
    AnchorPane space;
    @FXML
    Label notificationText;

    private final Notifications notifications;

    public NotificationsController() {
        notifications = (Notifications)
                Beans.context().get("notifications");
    }

    @FXML
    public void initialize() {
        if(notifications != null) {
            notifications.pushedProperty().addListener((
                    observableValue, aBoolean, t1) -> {
                        if(!space.isVisible()) {
                            space.setVisible(true);
                        }
                        notificationText.setText(notifications.
                                getLast());
                    });
            notifications.removedProperty().addListener((
                    observableValue, aBoolean, t1) -> {
                if(notifications.size() == 0) {
                    space.setVisible(false);
                } else {
                    notificationText.setText(notifications.getLast());
                }
            });
        }
        space.setVisible(false);
    }

    public void close() {
        notifications.removeLast();
    }

    public void closeAll() {
        notifications.clear();
    }
}
