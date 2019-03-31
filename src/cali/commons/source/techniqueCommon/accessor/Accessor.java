package cali.commons.source.techniqueCommon.accessor;

/**
 * Implementation of an accessor consisting of a source string, a count and a stride value as well as an
 * array of {@link Param} objects. This is a direct virtual representation of an accessor in a COLLADA (".dae) file.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class Accessor {

    private String source;

    private int count;
    private int stride;

    private Param[] params;

    /**
     *
     * @param source the source string of the accessor retrieved from the ".dae" file
     * @param count the count of the accessor retrieved from the ".dae" file
     * @param stride the stride of the accessor retrieved from the ".dae" file
     * @param params the array of {@link Param} objects retrieved from the ".dae" file
     */
    public Accessor(String source, int count, int stride, Param[] params) {
        this.source = source;
        this.count = count;
        this.stride = stride;
        this.params = params;
    }

    /**
     *
     * @return the source string of the accessor instance
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @return the count of the accessor instance
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return the stride of the accessor instance
     */
    public int getStride() {
        return stride;
    }

    /**
     *
     * @return the array of params of the accessor instance
     */
    public Param[] getParams() {
        return params;
    }
}
