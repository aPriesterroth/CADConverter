package cad.commons;

/**
 * Created by Aaron on 17.11.17.
 */
public class Input {

    private enum InputType {
        SEMANTIC_SOURCE, SEMANTIC_SOURCE_OFFSET, SEMANTIC_SOURCE_OFFSET_SET
    }

    private String semantic;
    private String source;

    private int offset;
    private int set;

    private InputType type;

    public Input(String semantic, String source){
        this.semantic = semantic;
        this.source = source;

        this.offset = Integer.MIN_VALUE;
        this.set = Integer.MIN_VALUE;

        this.type = InputType.SEMANTIC_SOURCE;
    }

    public Input(String semantic, String source, int offset){
        this.semantic = semantic;
        this.source = source;
        this.offset = offset;

        this.set = Integer.MIN_VALUE;

        this.type = InputType.SEMANTIC_SOURCE_OFFSET;
    }

    public Input(String semantic, String source, int offset, int set){
        this.semantic = semantic;
        this.source = source;
        this.offset = offset;
        this.set = set;

        this.type = InputType.SEMANTIC_SOURCE_OFFSET_SET;
    }

    public String getSemantic() {
        return semantic;
    }

    public String getSource() {
        return source;
    }

    public int getOffset() {
        return offset;
    }

    public int getSet() {
        return set;
    }

    public InputType getType() {
        return type;
    }
}

