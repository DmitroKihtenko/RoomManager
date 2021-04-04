package main.java.rm.bean;

public abstract class IdHolder implements Comparable<IdHolder> {
    private int id;

    /**
     * Sets default id value 0
     */
    public IdHolder() {
        id = 0;
    }

    /**
     * Sets custom id value
     * @param id id value
     */
    public void setId(int id) {
        this.id = id;
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
