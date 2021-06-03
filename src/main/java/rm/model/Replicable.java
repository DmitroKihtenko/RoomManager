package rm.model;

public interface Replicable extends Cloneable {
    Replicable replicate(Replicable object);
    Object clone() throws CloneNotSupportedException;
}
