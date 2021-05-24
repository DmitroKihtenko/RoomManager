package rm.bean;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Successor class for ConnectionsList class that realizes fast methods of creating and removing connections
 */
public class FastMutableConnections extends ConnectionsList {
    @Override
    protected Set<Integer> createSet() {
        return new TreeSet<>();
    }

    @Override
    protected Map<Integer, Set<Integer>> createMap(int capacity) {
        return new TreeMap<>();
    }
}
