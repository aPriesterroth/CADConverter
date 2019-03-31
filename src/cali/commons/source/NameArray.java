package cali.commons.source;

/**
 * Implementation of a name array based on the abstraction of the basic array consisting of an array of strings. This
 * is a direct virtual representation of a names array in a COLLADA (".dae") file.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class NameArray extends BasicArray {

    private String[] names;

    /**
     *
     * @param id the id string of the name array retrieved from the ".dae" file
     * @param count the count of the name array retrieved from the ".dae" file
     * @param names the array of strings of the name array retrieved from the ".dae" file
     */
    public NameArray(String id, int count, String[] names){
        super(id, count, BasicArrayType.NAME_ARRAY);
        this.names = names;
    }

    /**
     *
     * @return the array of strings of the name array instance
     */
    public String[] getNames() {
        return names;
    }
}