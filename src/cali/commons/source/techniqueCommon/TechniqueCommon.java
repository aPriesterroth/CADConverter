package cali.commons.source.techniqueCommon;

import cali.commons.source.techniqueCommon.accessor.Accessor;

/**
 * Implementation of a technique common consisting of an accessor. This is a direct virtual representation of a
 * technique common in a COLLADA (".dae) file.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 17.11.17.
 */
public class TechniqueCommon {

    private Accessor accessor;

    /**
     *
     * @param accessor the accessor retrieved from the ".dae" file
     */
    public TechniqueCommon(Accessor accessor){
        this.accessor = accessor;
    }

    /**
     *
     * @return the accessor of the technique common instance
     */
    public Accessor getAccessor() {
        return accessor;
    }
}
