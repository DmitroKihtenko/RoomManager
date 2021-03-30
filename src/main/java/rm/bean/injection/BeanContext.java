package main.java.rm.bean.injection;

import main.java.rm.exceptions.NameAlreadyExistsException;
import main.java.rm.exceptions.NameDoesNotExistException;

public interface BeanContext {
    /**
     * Put any object into context with some name
     * @param name identifier of bean for this context, not null
     * @param bean bean object that puts in context, not null
     * @throws NameAlreadyExistsException if context contains another bean with such name
     */
    void set(String name, Object bean) throws NameAlreadyExistsException;

    /**
     * Adds a new bean by name or replaces an existing one with the same name
     * @param name identifier of bean to add or replace, not null
     * @param bean bean object, not null
     */
    void replace(String name, Object bean);

    /**
     * If context contains bean removes it otherwise doing nothing
     * @param name identifier of bean to remove, not null
     */
    void remove(String name);

    /**
     * Reports whether a bean with some name exists in this context
     * @param name identifier of bean, not null
     * @return true if bean exists otherwise returns false
     */
    boolean exists(String name);

    /**
     * Takes a bean from this context by name without deleting
     * @param name identifier of bean, not null
     * @return bean object that has been added before
     * @throws NameDoesNotExistException if bean does not exist in this context
     */
    Object get(String name) throws NameDoesNotExistException;
}
