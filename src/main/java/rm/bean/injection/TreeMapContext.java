package main.java.rm.bean.injection;

import main.java.rm.exceptions.NameAlreadyExistsException;
import main.java.rm.exceptions.NameDoesNotExistException;
import org.apache.log4j.Logger;

import java.util.TreeMap;

public class TreeMapContext implements BeanContext {
    private static final Logger logger =
            Logger.getLogger(TreeMapContext.class);

    private final TreeMap<String, Object> beans;

    /**
     * Checks name of bean for null value
     * @param name name of bean
     * @throws IllegalArgumentException if name of bean has null value
     */
    protected void checkName(String name) {
        if(name == null) {
            logger.error("Bean name has null value");

            throw new IllegalArgumentException(
                    "Bean name has null value"
            );
        }
    }

    /**
     * Checks bean object for null value
     * @param bean object of bean
     * @throws IllegalArgumentException if object has null value
     */
    protected void checkBean(Object bean) {
        if(bean == null) {
            logger.error("Bean object has null value");

            throw new IllegalArgumentException(
                    "Bean object has null value"
            );
        }
    }

    public TreeMapContext() {
        beans = new TreeMap<>();
    }

    @Override
    public void set(String name, Object bean) throws NameAlreadyExistsException {
        checkName(name);
        checkBean(bean);

        if(beans.containsKey(name)) {
            logger.error("Attempt to set bean that already exists");

            throw new NameAlreadyExistsException(
                    "Bean with name " + name + " already exists"
            );
        }

        beans.put(name, bean);

        logger.info("Added bean with name " + name);
    }

    @Override
    public void replace(String name, Object bean) {
        checkName(name);
        checkBean(bean);

        beans.put(name, bean);

        logger.info("Added bean with name " + name);
    }

    @Override
    public void remove(String name) {
        checkName(name);

        if(beans.remove(name) != null) {
            logger.info("Removed bean with name " + name);
        }
    }

    @Override
    public boolean exists(String name) {
        checkName(name);

        return beans.containsKey(name);
    }

    @Override
    public Object get(String name) throws NameDoesNotExistException {
        checkName(name);

        if(!beans.containsKey(name)) {
            logger.error("Attempt to get bean " + name +
                    " that is not exist");

            throw new NameDoesNotExistException(
                    "Bean with name " + name + " does not exist"
            );
        }

        return beans.get(name);
    }
}
