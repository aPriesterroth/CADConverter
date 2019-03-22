package cad.commons.source.techniqueCommon.accessor;

/**
 * Created by Aaron on 17.11.17.
 */
public class Param {

    private String name;
    private String type;

    public Param(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
