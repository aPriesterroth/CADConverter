package cali.commons.source;

/**
 * Created by Aaron on 17.11.17.
 */
public class FloatArray extends BasicArray{

    private float[] floats;

    public FloatArray(String id, int count, float[] floats){
        super(id, count, BasicArrayType.FLOAT_ARRAY);
        this.floats = floats;
    }

    public float[] getFloats() {
        return floats;
    }
}
