package cali.commons.source;

/**
 * Implementation of a float array based on the abstraction of the basic array consisting of an array of floats. This is
 * a direct virtual representation of a float array in a COLLADA (".dae) file.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class FloatArray extends BasicArray {

    private float[] floats;

    /**
     *
     * @param id the id string of the float array retrieved from the ".dae" file
     * @param count the count of the float array retrieved from the ".dae" file
     * @param floats the array of floats of the float array retrieved from the ".dae" file
     */
    public FloatArray(String id, int count, float[] floats){
        super(id, count, BasicArrayType.FLOAT_ARRAY);
        this.floats = floats;
    }

    /**
     *
     * @return the array of floats of the float array instance
     */
    public float[] getFloats() {
        return floats;
    }
}
