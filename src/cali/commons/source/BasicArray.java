package cali.commons.source;

/**
 * Implementation of an abstraction of a so-called basic array consisting of an id string, a count and its type. A basic
 * array can be of two types: FLOAT_ARRAY and NAME_ARRAY.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public abstract class BasicArray {

    public enum BasicArrayType {
        FLOAT_ARRAY, NAME_ARRAY
    }

    private String id;

    private int count;

    private BasicArrayType type;

    /**
     *
     * @param id the id string of the basic array retrieved from the ".dae" file
     * @param count the count of the basic array retrieved from the ".dae" file
     * @param type the type of the basic array retrieved from the ".dae" file
     */
    BasicArray(String id, int count, BasicArrayType type){
        this.id = id;
        this.count = count;
        this.type = type;
    }

    /**
     *
     * @return the id string of the basic array instance
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the count of the basic array instance
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return the type of the basic array instance
     */
    public BasicArrayType getType() {
        return type;
    }
}
