package rm.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import rm.exceptions.ConnectionDoesNotExistException;
import rm.service.Assertions;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class used for describing logic of logical connections between two types of objects
 */
public abstract class ConnectionsList implements Replicable {
    private static final Logger logger =
            Logger.getLogger(ConnectionsList.class);

    private Map<Integer, Set<Integer>> firstConnections;
    private Map<Integer, Set<Integer>> secondConnections;
    private BooleanProperty changed;

    /**
     * Abstract method that uses to create set that contains list of connected ids
     * @return One of the implementations of the abstract class Set
     */
    protected abstract Set<Integer> createSet();

    /**
     * Abstract method that uses to create map that contains id and set of connected ids
     * @param capacity Optional parameter that can be used to set initial capacity of map
     * @return One of the implementations of the abstract class Map
     */
    protected abstract Map<Integer, Set<Integer>> createMap(int capacity);

    /**
     * Creates class and initializes containers with start capacity 1. Does not limit the quantity of objects
     */
    public ConnectionsList() {
        initCapacity(1, 1);
    }

    /**
     * Cleared list and initializes it with new capacity of objects. Does not limit the quantity of objects
     * @param firstAmount capacity of first objects
     * @param secondAmount capacity of second objects
     */
    public void initCapacity(int firstAmount, int secondAmount) {
        Assertions.isPositive(firstAmount,
                "Initial amount of first objects", logger);
        Assertions.isPositive(secondAmount,
                "Initial amount of second objects", logger);

        firstConnections = createMap(firstAmount);
        secondConnections = createMap(secondAmount);
        if(changed == null) {
            changed = new SimpleBooleanProperty(true);
        } else {
            changed.set(!changed.get());
        }
    }

    /**
     * Creates bilateral connection between id of first and second objects. Does not create duplicates
     * @param firstId id of first object
     * @param secondId id of second object
     */
   public void setConnection(int firstId, int secondId) {
        if(!firstConnections.containsKey(firstId)) {
            firstConnections.put(firstId, createSet());
        }
        firstConnections.get(firstId).add(secondId);

        if(!secondConnections.containsKey(secondId)) {
            secondConnections.put(secondId, createSet());
        }
        secondConnections.get(secondId).add(firstId);

        changed.set(!changed.get());
   }

    /**
     * Removes bilateral connection between id of first and second objects
     * @param firstId id of first object
     * @param secondId id of second object
     * @throws ConnectionDoesNotExistException if this list does not contain such connection
     */
    public void removeConnection(int firstId, int secondId) {
        if(!firstConnections.containsKey(firstId)) {
            logger.error("Attempt to remove connections that does" +
                    "not exist");

            throw new ConnectionDoesNotExistException(
                    "No connections for ids " + firstId +
                            " and " + secondId
            );
        }
        firstConnections.get(firstId).remove(secondId);
        secondConnections.get(secondId).remove(firstId);

        changed.set(!changed.get());
    }

    /**
     * Removes all connections for specified id of first object
     * @param firstId id of first object
     */
    public void removeFirstConnections(int firstId) {
        if(firstConnections.containsKey(firstId)) {
            firstConnections.remove(firstId);
            for(Integer secondId : secondConnections.keySet()) {
                secondConnections.get(secondId).remove(firstId);
            }
        }
    }

    /**
     * Removes all connections for specified id of second object
     * @param secondId id of first object
     */
    public void removeSecondConnections(int secondId) {
        if(firstConnections.containsKey(secondId)) {
            secondConnections.remove(secondId);
            for(Integer firstId : firstConnections.keySet()) {
                firstConnections.get(firstId).remove(secondId);
            }
        }
    }

    /**
     * indicates whether a connection exists between the specified identifiers
     * @param firstId id of first object
     * @param secondId id of second object
     * @return true if connection exists otherwise returns false
     */
    public boolean existsConnection(int firstId, int secondId) {
        if(!firstConnections.containsKey(firstId)) {
            return false;
        }
        return firstConnections.get(firstId).contains(secondId);
    }

    /**
     * Gets all first ids contained in list
     * @return contained first ids
     */
    public Iterable<Integer> getFirstIds() {
        return firstConnections.keySet();
    }

    /**
     * Gets all second ids contained in list
     * @return contained seconds ids
     */
    public Iterable<Integer> getSecondIds() {
        return secondConnections.keySet();
    }

    /**
     * Returns stream of second type objects ids connected with first type object id
     * @param firstId id of first type object
     * @return list of connected ids to id of first type object
     */
    public Iterable<Integer> getFirstConnections(int firstId) {
        if(!firstConnections.containsKey(firstId)) {
            logger.error("Attempt to get connections for id " +
                    firstId + " that does not exist");

            throw new IllegalArgumentException(
                    "Attempt to get connections for id " + firstId +
                            " that does not exist"
            );
        }
        return firstConnections.get(firstId);
    }

    /**
     * Returns stream of first type objects ids connected with second type object id
     * @param secondId id of second type object
     * @return list of connected ids to id of second type object
     */
    public Iterable<Integer> getSecondConnections(int secondId) {
        if(!secondConnections.containsKey(secondId)) {
            logger.error("Attempt to get connections for id " +
                    secondId + " that does not exist");

            throw new IllegalArgumentException(
                    "Attempt to get connections for id " + secondId +
                            " that does not exist"
            );
        }

        return secondConnections.get(secondId);
    }

    /**
     * Used to track any changes in connections list
     * @return boolean property for java fx mvc
     */
    public BooleanProperty changedProperty() {
        return changed;
    }

    public void differences(ConnectionsList another,
                            ConnectionsList added,
                            ConnectionsList removed) {
        Assertions.isNotNull(another, "Another connections", logger);
        Assertions.isNotNull(another, "Added connections", logger);
        Assertions.isNotNull(another, "Removed connections", logger);

        for(Integer firstId : getFirstIds()) {
            for(Integer secondsId : getFirstConnections(firstId)) {
                if(!another.existsConnection(firstId, secondsId)) {
                    removed.setConnection(firstId, secondsId);
                }
            }
        }
        for(Integer firstId : another.getFirstIds()) {
            for(Integer secondsId : another.
                    getFirstConnections(firstId)) {
                if(!existsConnection(firstId, secondsId)) {
                    added.setConnection(firstId, secondsId);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringValue = new StringBuilder();
        for(Integer key : firstConnections.keySet()) {
            stringValue.append(key).append(":").
                    append(firstConnections.get(key).toString());
        }
        return stringValue.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionsList)) {
            return false;
        }
        ConnectionsList that = (ConnectionsList) o;
        return Objects.equals(firstConnections, that.firstConnections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstConnections);
    }

    @Override
    public void replicate(Replicable object) {
        ConnectionsList value = (ConnectionsList) object;
        for(Integer firstId : getFirstIds()) {
            for(Integer secondId : getFirstConnections(firstId)) {
                value.setConnection(firstId, secondId);
            }
        }
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;
}
