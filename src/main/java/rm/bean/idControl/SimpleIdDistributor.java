package main.java.rm.bean.idControl;

import java.util.LinkedList;

class SimpleIdDistributor implements IdDistributor {
    private int idIterator;
    private final LinkedList<Integer> unusedIds;

    SimpleIdDistributor() {
        idIterator = Integer.MIN_VALUE;
        unusedIds = new LinkedList<>();
    }

    @Override
    public int getUniqueId() {
        if(!unusedIds.isEmpty()) {
            return unusedIds.pop();
        }
        return idIterator++;
    }

    @Override
    public void addUnusedId(int id) {
        unusedIds.add(id);
    }
}
