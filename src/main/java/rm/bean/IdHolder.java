package main.java.rm.bean;

public abstract class IdHolder implements Comparable<IdHolder> {
    private static final IdHandler idHandler = new IdHandler();

    private int id;

    /**
     * Sets default id value from IdHandler class
     */
    public IdHolder() {
        id = IdHandler.DEFAULT_ID;
    }

    /**
     * Sets custom id value
     * @param id id value
     */
    public void setId(int id) {
        idHandler.setUsedId(id);
        this.id = id;
    }

    /**
     * Creates a unique identifier for objects of this class
     */
    public void createId() {
        this.id = idHandler.getUniqueId();
    }

    /**
     * Gets id value
     * @return id value
     */
    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(IdHolder o) {
        return Integer.compare(this.id, o.id);
    }
}
