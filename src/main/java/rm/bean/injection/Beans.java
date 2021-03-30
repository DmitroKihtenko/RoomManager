package main.java.rm.bean.injection;

import main.java.rm.exceptions.NameDoesNotExistException;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class Beans {
    private static final Logger logger = Logger.getLogger(Beans.class);

    private static HashMap<String, BeanContext> contexts;

    static {
        contexts = new HashMap<>();
    }

    /**
     * Initializes new context with start capacity value. Removes all context values
     * @param capacity start capacity for new contexts container
     */
    public static void initializeNew(int capacity) {
        if(capacity <= 0) {
            logger.error("Beans contexts map capacity " +
                    "has non-positive value");

            throw new IllegalArgumentException(
                    "Beans contexts map capacity has " +
                            "non-positive value"
            );
        }

        contexts = new HashMap<>(capacity);
    }

    /**
     * Puts new context object by name. If such name already exists replaces it
     * @param name string identifier of context, not null
     * @param context context object, not null
     */
    public static void setContext(String name, BeanContext context) {
        if(name == null) {
            logger.error("Context name has null value");

            throw new IllegalArgumentException(
                    "Context name has null value"
            );
        }
        if(context == null) {
            logger.error("Context object has null value");

            throw new IllegalArgumentException(
                    "Context object has null value"
            );
        }

        contexts.put(name, context);

        logger.info("Added bean context with name " + name);
    }

    /**
     * Takes a context object by name without deleting
     * @param name identifier of context object, not null
     * @return context object
     * @throws NameDoesNotExistException if context with such name doesn't exist
     */
    public static BeanContext getContext(String name) throws
            NameDoesNotExistException {
        if(name == null) {
            logger.error("Context name has null value");

            throw new IllegalArgumentException(
                    "Context name has null value"
            );
        }

        if(!contexts.containsKey(name)) {
            logger.error("Context with name " + name +
                    "does not exists");

            throw new NameDoesNotExistException(
                    "Context with name " + name +
                            "does not exists"
            );
        }

        return contexts.get(name);
    }
}
