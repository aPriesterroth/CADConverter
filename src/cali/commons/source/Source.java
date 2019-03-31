package cali.commons.source;

import cali.commons.source.techniqueCommon.TechniqueCommon;
import cali.interfaces.CALiDataStructure;

/**
 * Implementation of a source consisting of an id string, a basic array object and a technique common. This is a direct
 * virtual representation of a source within a COLLADA (".dae") file.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class Source implements CALiDataStructure {

    private String id;

    private BasicArray array;

    private TechniqueCommon techniqueCommon;

    /**
     *
     * @param id the id string of the source retrieved from the ".dae" file
     * @param array the basic array object of the source retrieved from the ".dae" file
     * @param techniqueCommon the technique common of the source retrieved from the ".dae" file
     */
    public Source(String id, BasicArray array, TechniqueCommon techniqueCommon){
        this.id = id;
        this.array = array;
        this.techniqueCommon = techniqueCommon;
    }

    /**
     * Implementation of the getId() method every identifiable object has.
     *
     * @return the id string of the source instance
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the basic array object of the source instance
     */
    public BasicArray getArray() {
        return array;
    }

    /**
     *
     * @return the technique common of the source instance
     */
    public TechniqueCommon getTechniqueCommon() {
        return techniqueCommon;
    }

    /**
     * Implementation of the getSemantic() method every identifiable object has. Returning
     * "CALiDataStructure.NO_SEMANTIC" identifies that there is no natural semantic present for this object.
     *
     * @return the semantic of the identifiable object
     */
    @Override
    public String getSemantic() {
        return CALiDataStructure.NO_SEMANTIC;
    }
}
