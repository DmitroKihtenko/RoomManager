package main.java.rm.bean;

import main.java.rm.exceptions.ConnectionDoesNotExistException;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public abstract class ConnectionsList {
    private static final Logger logger =
            Logger.getLogger(ConnectionsList.class);

    private final Map<Integer, Set<Integer>> firstConnections;
    private final Map<Integer, Set<Integer>> secondConnections;

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
     * Creates class and initializes start capacity for map containers
     * @param firstAmount initial capacity for map of first ids, positive value
     * @param secondAmount initial capacity for map of second ids, positive value
     */
    public ConnectionsList(int firstAmount, int secondAmount) {
        if(firstAmount <= 0) {
            logger.error("Initial amount of first objects has " +
                    "non-positive value");

            throw new IllegalArgumentException(
                    "Initial amount of first objects has " +
                            "non-positive value"
            );
        }
        firstConnections = createMap(firstAmount);
        if(secondAmount <= 0) {
            logger.error("Initial amount of second objects has " +
                    "non-positive value");

            throw new IllegalArgumentException(
                    "Initial amount of second objects has " +
                            "non-positive value"
            );
        }
        secondConnections = createMap(secondAmount);
    }

    /**
     * Creates bilateral connection between id of first and second objects. Does not create duplicates
     * @param firstId id of first object
     * @param secondId id of second object
     */
    void setConnection(int firstId, int secondId) {
        if(!firstConnections.containsKey(firstId)) {
            firstConnections.put(firstId, createSet());
        }
        firstConnections.get(firstId).add(secondId);

        if(!secondConnections.containsKey(secondId)) {
            secondConnections.put(secondId, createSet());
        }
        secondConnections.get(secondId).add(firstId);
    }

    /**
     * Removes bilateral connection between id of first and second objects
     * @param firstId id of first object
     * @param secondId id of second object
     * @throws ConnectionDoesNotExistException if this list does not contain such connection
     */
    void removeConnection(int firstId, int secondId) throws ConnectionDoesNotExistException {
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
    }

    /**
     * Returns stream of second type objects ids connected with first type object id
     * @param firstId id of first type object
     * @return stream of connected ids to id of first type object
     */
    Stream<Integer> getFirstConnections(int firstId) {
        if(!firstConnections.containsKey(firstId)) {
            logger.error("Attempt to get connections for id " +
                    firstId + " that does not exist");

            throw new IllegalArgumentException(
                    "Attempt to get connections for id " + firstId +
                            " that does not exist"
            );
        }
        return firstConnections.get(firstId).stream();
    }

    /**
     * Returns stream of first type objects ids connected with second type object id
     * @param secondId id of second type object
     * @return stream of connected ids to id of second type object
     */
    Stream<Integer> getSecondConnections(int secondId) {
        if(!secondConnections.containsKey(secondId)) {
            logger.error("Attempt to get connections for id " +
                    secondId + " that does not exist");

            throw new IllegalArgumentException(
                    "Attempt to get connections for id " + secondId +
                            " that does not exist"
            );
        }

        return secondConnections.get(secondId).stream();
    }
}
