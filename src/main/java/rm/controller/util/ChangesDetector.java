package rm.controller.util;

import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ChangesDetector<T> {
    private static final Logger logger =
            Logger.getLogger(ChangesDetector.class);

    private Map<Integer, T> original;
    private Map<Integer, T> changed;
    private final TreeMap<Integer, T> added;
    private final TreeMap<Integer, T> removed;
    private final TreeMap<Integer, T> updated;

    public ChangesDetector() {
        setOriginal(new HashMap<>());
        setChanged(new HashMap<>());
        added = new TreeMap<>();
        removed = new TreeMap<>();
        updated = new TreeMap<>();
    }

    public void setOriginal(Map<Integer, T> original) {
        Assertions.isNotNull(original, "Original map for changes",
                logger);

        this.original = original;
    }

    public void setChanged(Map<Integer, T> changed) {
        Assertions.isNotNull(changed, "Changed map",
                logger);

        this.changed = changed;
    }

    public void findChanges() {
        logger.debug("Searching changes for objects");

        added.clear();
        removed.clear();
        updated.clear();

        T object;
        for(Integer key : original.keySet()) {
            object = original.get(key);
            if(changed.containsKey(key)) {
                if(object != null &&
                        !object.equals(changed.get(key))) {
                    updated.put(key, object);
                }
            } else {
                removed.put(key, object);
            }
        }
        for(Integer key : changed.keySet()) {
            object = changed.get(key);
            if(!original.containsKey(key)) {
                if(object != null &&
                        !object.equals(changed.get(key))) {
                    added.put(key, object);
                }
            }
        }
    }

    public void discardFound() {
        added.clear();
        removed.clear();
        updated.clear();
    }

    public Map<Integer, T> getOriginal() {
        return original;
    }

    public Map<Integer, T> getChanged() {
        return changed;
    }

    public Map<Integer, T> getAdded() {
        return added;
    }

    public Map<Integer, T> getRemoved() {
        return removed;
    }

    public Map<Integer, T> getUpdated() {
        return updated;
    }
}
