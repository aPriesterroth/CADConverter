package cad.commons.source.techniqueCommon.accessor;

/**
 * Created by Aaron on 17.11.17.
 */
public class Accessor {

    private String source;

    private int count;
    private int stride;

    private Param[] params;

    public Accessor(String source, int count, int stride, Param[] params) {
        this.source = source;
        this.count = count;
        this.stride = stride;
        this.params = params;
    }

    public String getSource() {
        return source;
    }

    public int getCount() {
        return count;
    }

    public int getStride() {
        return stride;
    }

    public Param[] getParams() {
        return params;
    }
}
