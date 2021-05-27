package rm.bean;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.log4j.Logger;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class Notifications {
    private static final Logger logger =
            Logger.getLogger(Notifications.class);
    private final Stack<String> notifications;
    private final BooleanProperty changes;
    private final ReentrantLock lock;

    public Notifications() {
        notifications = new Stack<>();
        changes = new SimpleBooleanProperty();
        lock = new ReentrantLock();
    }

    public void push(String notification) {

    }

    public void removeLast() {

    }

    public String getLast() {
        return null;
    }

    public int size() {
        return 0;
    }

    public void clear() {
    }

    public Iterable<String> getAll() {
        return notifications;
    }

    public BooleanProperty changesProperty() {
        return changes;
    }
}
