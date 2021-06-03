package rm.model;

/**
 * Interface that describes logic of coping data of any object to
 * other and creating clone of object with independent memory
 */
public interface Replicable extends Cloneable {
    /**
     * Copied data from this object to specified object
     * @param object object for coping data
     */
    void replicate(Replicable object);

    /**
     * Creates clone with independent memory of this object with type of this class
     * @return cloned object with independent memory
     * @throws CloneNotSupportedException if class is not supporting cloning of objects
     */
    Object clone() throws CloneNotSupportedException;
}
