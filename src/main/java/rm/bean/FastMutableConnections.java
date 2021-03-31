package main.java.rm.bean;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FastMutableConnections extends ConnectionsList {
    public FastMutableConnections() {
        super(0, 0);
    }

    @Override
    protected Set<Integer> createSet() {
        return new TreeSet<>();
    }

    @Override
    protected Map<Integer, Set<Integer>> createMap(int capacity) {
        return new TreeMap<>();
    }
}
