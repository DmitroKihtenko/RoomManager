package rm.bean;

public abstract class IdHolder implements Comparable<IdHolder> {
    public static final int DEFAULT_ID = Integer.MIN_VALUE;

    private static int maximalValue = 0;
    private int id;

    /**
     * Sets initial id as {@link #DEFAULT_ID} value
     */
    public IdHolder() {
        id = DEFAULT_ID;
    }

    /**
     * Sets custom id value
     *
     * @param id id value
     */
    public void setId(int id) {
        if (id > maximalValue) {
            maximalValue = id;
        }
        this.id = id;
    }

    /**
     * Creates a unique identifier for objects of this class
     */
    public void createUniqueId() {
        if (maximalValue == Integer.MAX_VALUE) {
            throw new IllegalStateException("Can not get unique " +
                    "identifier. The identifiers of one of the " +
                    "classes are in a too scattered order");
        }
        id = ++maximalValue;
    }

    /**
     * Gets id value
     *
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

    @Override
    public String toString() {
        String result = "";

        if (String.valueOf(getId()) != null)
            result += "id - " + getId() + ".";

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IdHolder)) {
            return false;
        }

        IdHolder guest = (IdHolder) obj;
        return (id == guest.getId());
    }
}