package main.java.rm.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Successor class for ConnectionsList class that realizes fast methods of getting connections
 */
public class FastAccessConnections extends ConnectionsList {
    public FastAccessConnections(int firstAmount, int secondAmount) {
        super(firstAmount, secondAmount);
    }

    @Override
    protected Set<Integer> createSet() {
        return new HashSet<>();
    }

    @Override
    protected Map<Integer, Set<Integer>> createMap(int capacity) {
        return new HashMap<>(capacity);
    }
}
