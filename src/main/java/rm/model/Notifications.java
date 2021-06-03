package rm.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Notifications {
    private static final Logger logger =
            Logger.getLogger(Notifications.class);
    private final ConcurrentLinkedDeque<String> notifications;
    private final BooleanProperty pushed;
    private final BooleanProperty removed;
    private int limit;

    public Notifications() {
        notifications = new ConcurrentLinkedDeque<>();
        pushed = new SimpleBooleanProperty();
        removed = new SimpleBooleanProperty();
        limit = 10;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        Assertions.isPositive(limit, "Notifications limit", logger);

        this.limit = limit;
    }

    public void push(String notification) {
        Assertions.isNotNull(notification, "Notification message",
                logger);
        notifications.addLast(notification);
        pushed.set(!pushed.get());
        if(notifications.size() > limit) {
            notifications.removeFirst();
        }
    }

    public void removeLast() {
        if(!notifications.isEmpty()) {
            notifications.removeLast();
            removed.set(!removed.get());
        }
    }

    public String getLast() {
        String message = null;
        if(!notifications.isEmpty()) {
            message = notifications.getLast();
        }
        return message;
    }

    public int size() {
        return notifications.size();
    }

    public void clear() {
        notifications.clear();
        removed.set(!removed.get());
    }

    public Iterable<String> getAll() {
        return new ArrayList<>(notifications);
    }

    public BooleanProperty pushedProperty() {
        return pushed;
    }

    public BooleanProperty removedProperty() {
        return removed;
    }
}
