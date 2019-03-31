package cali.commons.source.techniqueCommon.accessor;

/**
 * Implementation of a param consisting of a name and a type string. This is a direct virtual representation of a
 * param in a COLLADA (".dae) file.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class Param {

    private String name;
    private String type;

    /**
     *
     * @param name the name retrieved from the ".dae" file
     * @param type the type retrieved from the ".dae" file
     */
    public Param(String name, String type){
        this.name = name;
        this.type = type;
    }

    /**
     *
     * @return the name of the param instance
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the type of the param instance
     */
    public String getType() {
        return type;
    }
}
