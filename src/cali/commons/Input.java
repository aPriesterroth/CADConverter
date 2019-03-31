package cali.commons;

import cali.interfaces.Identifiable;

/**
 * Implementation of an input consisting of a semantic and a source string, an offset and a set and a type. An input
 * can be of three types: SEMANTIC_SOURCE, SEMANTIC_SOURCE_OFFSET and SEMANTIC_SOURCE_OFFSET_SET, indicating
 * exactly what information the Input consists of.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class Input implements Identifiable {

    private enum InputType {
        SEMANTIC_SOURCE, SEMANTIC_SOURCE_OFFSET, SEMANTIC_SOURCE_OFFSET_SET
    }

    private String semantic;
    private String source;

    private int offset;
    private int set;

    private InputType type;

    /**
     *
     * @param semantic the semantic string of the input retrieved from the ".dae" file
     * @param source the source string of the input retrieved from the ".dae" file
     */
    public Input(String semantic, String source){
        this.semantic = semantic;
        this.source = source;

        this.offset = Integer.MIN_VALUE;
        this.set = Integer.MIN_VALUE;

        this.type = InputType.SEMANTIC_SOURCE;
    }

    /**
     *
     * @param semantic the semantic string of the input retrieved from the ".dae" file
     * @param source the source string of the input retrieved from the ".dae" file
     * @param offset the offset of the input retrieved from the ".dae" file
     */
    public Input(String semantic, String source, int offset){
        this.semantic = semantic;
        this.source = source;
        this.offset = offset;

        this.set = Integer.MIN_VALUE;

        this.type = InputType.SEMANTIC_SOURCE_OFFSET;
    }

    /**
     *
     * @param semantic the semantic string of the input retrieved from the ".dae" file
     * @param source the source string of the input retrieved from the ".dae" file
     * @param offset the offset of the input retrieved from the ".dae" file
     * @param set the set of the input retrieved from the ".dae" file
     */
    public Input(String semantic, String source, int offset, int set){
        this.semantic = semantic;
        this.source = source;
        this.offset = offset;
        this.set = set;

        this.type = InputType.SEMANTIC_SOURCE_OFFSET_SET;
    }

    /**
     * Implementation of the getId() method every identifiable object has. Returning "Identifiable.NO_ID" indicates
     * that there is no natural id present.
     *
     * @return the id of the identifiable object
     */
    @Override
    public String getId() {
        return Identifiable.NO_ID;
    }

    /**
     *
     * @return the semantic string of the input instance
     */
    public String getSemantic() {
        return semantic;
    }

    /**
     *
     * @return the source string of the input instance
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @return the offset of the input instance
     */
    public int getOffset() {
        return offset;
    }

    /**
     *
     * @return the set of the input instance
     */
    public int getSet() {
        return set;
    }

    /**
     *
     * @return the type of the input instance
     */
    public InputType getType() {
        return type;
    }
}

