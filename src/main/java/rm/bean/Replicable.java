package rm.bean;

public interface Replicable extends Cloneable {
    Replicable replicate(Replicable object);
}
