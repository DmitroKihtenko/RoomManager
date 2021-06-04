package rm.controller.util;

import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class used to find changes between two classes of type {@link Map} with parameters Integer and T
 * @param <T> second parameter in class {@link Map}
 */
public class ChangesDetector<T> {
    private static final Logger logger =
            Logger.getLogger(ChangesDetector.class);

    private Map<Integer, T> original;
    private Map<Integer, T> changed;
    private final TreeMap<Integer, T> added;
    private final TreeMap<Integer, T> removed;
    private final TreeMap<Integer, T> updated;

    /**
     * Default constructor creates all maps as empty
     */
    public ChangesDetector() {
        setOriginal(new HashMap<>());
        setChanged(new HashMap<>());
        added = new TreeMap<>();
        removed = new TreeMap<>();
        updated = new TreeMap<>();
    }

    /**
     * Setter for original map where changes must be found
     * @param original original map
     */
    public void setOriginal(Map<Integer, T> original) {
        Assertions.isNotNull(original, "Original map for changes",
                logger);

        this.original = original;
    }

    /**
     * Setter for changed map that used to compare with original map
     * @param changed changed map
     */
    public void setChanged(Map<Integer, T> changed) {
        Assertions.isNotNull(changed, "Changed map",
                logger);

        this.changed = changed;
    }

    /**
     * Finds changes between original and changed map
     */
    public void findChanges() {
        logger.debug("Searching changes for objects");

        added.clear();
        removed.clear();
        updated.clear();

        T object1;
        T object2;
        for(Integer key : original.keySet()) {
            object1 = original.get(key);
            if(changed.containsKey(key)) {
                object2 = changed.get(key);
                if(object1 != null &&
                        !object1.equals(object2)) {
                    updated.put(key, object2);
                }
            } else {
                removed.put(key, object1);
            }
        }
        for(Integer key : changed.keySet()) {
            object1 = changed.get(key);
            if(!original.containsKey(key)) {
                added.put(key, object1);
            }
        }
    }

    /**
     * Clears map of added, updated and removed objects
     */
    public void discardFound() {
        added.clear();
        removed.clear();
        updated.clear();
    }

    /**
     * Getter for original map
     * @return original map
     */
    public Map<Integer, T> getOriginal() {
        return original;
    }

    /**
     * Getter for changed map
     * @return changed map
     */
    public Map<Integer, T> getChanged() {
        return changed;
    }

    /**
     * Getter for map of added objects
     * @return map of added objects
     */
    public Map<Integer, T> getAdded() {
        return added;
    }

    /**
     * Getter for map of removed objects
     * @return map of removed objects
     */
    public Map<Integer, T> getRemoved() {
        return removed;
    }

    /**
     * Getter for map of updated objects
     * @return map of updated objects
     */
    public Map<Integer, T> getUpdated() {
        return updated;
    }
}
