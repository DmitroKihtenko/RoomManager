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

    protected abstract Set<Integer> createSet();
    protected abstract Map<Integer, Set<Integer>> createMap(int capacity);

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

    Stream<Integer> getFirstConnections(int firstId) {
        if(!firstConnections.containsKey(firstId)) {
            logger.error("Attempt to get connections for id " +
                    firstId + "that does not exist");

            throw new IllegalArgumentException(
                    "Attempt to get connections for id " + firstId +
                            "that does not exist"
            );
        }
        return firstConnections.get(firstId).stream();
    }

    Stream<Integer> getSecondConnections(int secondId) {
        if(!secondConnections.containsKey(secondId)) {
            logger.error("Attempt to get connections for id " +
                    secondId + "that does not exist");

            throw new IllegalArgumentException(
                    "Attempt to get connections for id " + secondId +
                            "that does not exist"
            );
        }

        return secondConnections.get(secondId).stream();
    }
}
