package main.java.rm.service;

import main.java.rm.exceptions.NameAlreadyExistsException;
import main.java.rm.exceptions.NameDoesNotExistException;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class Context {
    private static final Logger logger = Logger.getLogger(Context.class);
    private static HashMap<String, Object> context;

    /**
     * Put any object into context with some name
     * @param name identifier of bean for this context, not null
     * @param object object that puts in context, not null
     * @throws NameAlreadyExistsException if context contains another bean with such name
     */
    public void set(String name, Object object) {
        Assertions.isNotNull(name, "Object name", logger);
        Assertions.isNotNull(object, "Context object", logger);

        if(context.containsKey(name)) {
            logger.error("Attempt to set object that already exists");

            throw new NameAlreadyExistsException(
                    "Bean with name " + name + " already exists"
            );
        }

        context.put(name, object);
        logger.info("Added object with name " + name);
    }

    /**
     * Adds a new bean by name or replaces an existing one with the same name
     * @param name identifier of bean to add or replace, not null
     * @param object object, not null
     */
    public void replace(String name, Object object) {
        Assertions.isNotNull(name, "Object name", logger);
        Assertions.isNotNull(object, "Context object", logger);

        context.put(name, object);
        logger.info("Added object with name " + name);
    }

    /**
     * If context contains bean removes it otherwise doing nothing
     * @param name identifier of bean to remove, not null
     */
    public void remove(String name) {
        Assertions.isNotNull(name, "Object name", logger);

        if(context.remove(name) != null) {
            logger.info("Removed object with name " + name);
        }
    }

    /**
     * Reports whether a bean with some name exists in this context
     * @param name identifier of bean, not null
     * @return true if bean exists otherwise returns false
     */
    public boolean exists(String name) {
        Assertions.isNotNull(name, "Object name", logger);

        return context.containsKey(name);
    }

    /**
     * Takes a bean from this context by name without deleting
     * @param name identifier of bean, not null
     * @return bean object that has been added before
     * @throws NameDoesNotExistException if bean does not exist in this context
     */
    public Object get(String name) {
        Assertions.isNotNull(name, "Object name", logger);

        if(!context.containsKey(name)) {
            logger.error("Attempt to get object " + name +
                    " that is not exist");

            throw new NameDoesNotExistException(
                    "Object with name " + name + " does not exist"
            );
        }
        return context.get(name);
    }
}
