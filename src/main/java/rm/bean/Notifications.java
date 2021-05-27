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
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        notifications.push(notification);
        lock.unlock();
    }

    public void removeLast() {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        if(!notifications.empty()) {
            notifications.pop();
        }
        lock.unlock();
    }

    public String getLast() {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        String message = null;
        if(!notifications.empty()) {
            message = notifications.peek();
        }
        lock.unlock();
        return message;
    }

    public int size() {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        return notifications.size();
    }

    public void clear() {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        notifications.clear();
    }

    public Iterable<String> getAll() {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        Iterable<String> values = (Iterable<String>) notifications.clone();
        lock.unlock();
        return values;
    }

    public BooleanProperty changesProperty() {
        return changes;
    }
}
