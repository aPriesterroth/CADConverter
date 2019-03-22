package cad.commons.source;

/**
 * Created by Aaron on 17.11.17.
 */
public class NameArray extends BasicArray {

    private String[] names;

    public NameArray(String id, int count, String[] names){
        super(id, count, BasicArrayType.NAME_ARRAY);
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }
}