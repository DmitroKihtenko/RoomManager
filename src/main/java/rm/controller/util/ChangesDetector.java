package rm.controller.util;

import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.util.HashMap;

public class ChangesDetector<T> {
    private static final Logger logger =
            Logger.getLogger(ChangesDetector.class);
    private HashMap<Integer, T> original;

    public ChangesDetector(HashMap<Integer, T> original) {
        setOriginal(original);
    }

    public void setOriginal(HashMap<Integer, T> original) {
        Assertions.isNotNull(original, "Original map for changes",
                logger);

        this.original = original;
    }

    public void findChanges(HashMap<Integer, T> changed,
                            HashMap<Integer, T> removed,
                            HashMap<Integer, T> added) {
        logger.debug("Searching changes for objects");

        Assertions.isNotNull(original, "Changed map for changes",
                logger);
        Assertions.isNotNull(original, "Added elements for changes",
                logger);
        Assertions.isNotNull(original, "Removed elements for changes",
                logger);

        T object;
        for(Integer key : original.keySet()) {
            object = original.get(key);
            if(changed.containsKey(key)) {
                if(object != null &&
                        !object.equals(changed.get(key))) {
                    removed.put(key, object);
                    added.put(key, object);
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
}
