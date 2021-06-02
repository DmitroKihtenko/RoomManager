package rm.bean;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Notifications {
    private final ConcurrentLinkedDeque<String> notifications;
    private final BooleanProperty pushed;
    private final BooleanProperty removed;

    public Notifications() {
        notifications = new ConcurrentLinkedDeque<>();
        pushed = new SimpleBooleanProperty();
        removed = new SimpleBooleanProperty();
    }

    public void push(String notification) {
        notifications.push(notification);
        pushed.set(!pushed.get());
    }

    public void removeLast() {
        if(!notifications.isEmpty()) {
            notifications.pop();
            removed.set(!removed.get());
        }
    }

    public String getLast() {
        String message = null;
        if(!notifications.isEmpty()) {
            message = notifications.peek();
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
