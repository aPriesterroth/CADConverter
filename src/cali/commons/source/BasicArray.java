package cali.commons.source;

/**
 * Created by Aaron on 17.11.17.
 */
public abstract class BasicArray {

    public enum BasicArrayType {
        FLOAT_ARRAY, NAME_ARRAY
    }

    private String id;

    private int count;

    private BasicArrayType type;

    BasicArray(String id, int count, BasicArrayType type){
        this.id = id;
        this.count = count;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public BasicArrayType getType() {
        return type;
    }
}
