package rm.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class that contains deque of errors and other notifications in program
 */
public class Notifications {
    private static final Logger logger =
            Logger.getLogger(Notifications.class);
    private final ConcurrentLinkedDeque<String> notifications;
    private final BooleanProperty pushed;
    private final BooleanProperty removed;
    private int limit;

    /**
     * Default constructor. Sets limit of messages to 10
     */
    public Notifications() {
        notifications = new ConcurrentLinkedDeque<>();
        pushed = new SimpleBooleanProperty();
        removed = new SimpleBooleanProperty();
        limit = 10;
    }

    /**
     * Getter for value of maximal errors amount
     * @return Maximal amount of errors that can be contained
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Setter for value of maximal errors amount
     * @param limit maximal amount of errors that can be contained
     */
    public void setLimit(int limit) {
        Assertions.isPositive(limit, "Notifications limit", logger);

        this.limit = limit;
    }

    /**
     * Pushes any error at the end of errors deque, deletes first if limit reached
     * @param notification string line that describes error
     */
    public void push(String notification) {
        Assertions.isNotNull(notification, "Notification message",
                logger);
        notifications.addLast(notification);
        pushed.set(!pushed.get());
        if(notifications.size() > limit) {
            notifications.removeFirst();
        }
    }

    /**
     * Removes last error from errors deque
     */
    public void removeLast() {
        if(!notifications.isEmpty()) {
            notifications.removeLast();
            removed.set(!removed.get());
        }
    }

    /**
     * Returns last error from errors deque
     * @return last error line or null value if deque if empty
     */
    public String getLast() {
        String message = null;
        if(!notifications.isEmpty()) {
            message = notifications.getLast();
        }
        return message;
    }

    /**
     * Returns amount of contained errors
     * @return amount value
     */
    public int size() {
        return notifications.size();
    }

    /**
     * Deleted all contained errors
     */
    public void clear() {
        notifications.clear();
        removed.set(!removed.get());
    }

    /**
     * Returns all errors in order from first to last
     */
    public Iterable<String> getAll() {
        return new ArrayList<>(notifications);
    }

    /**
     * Used to track that any error was pushed
     * @return string property for java fx mvc
     */
    public BooleanProperty pushedProperty() {
        return pushed;
    }

    /**
     * Used to track that any error was removed
     * @return string property for java fx mvc
     */
    public BooleanProperty removedProperty() {
        return removed;
    }
}
