package cad.commons.source.techniqueCommon;

import cad.commons.source.techniqueCommon.accessor.Accessor;

/**
 * Created by Aaron on 17.11.17.
 */
public class TechniqueCommon {

    private Accessor accessor;

    public TechniqueCommon(Accessor accessor){
        this.accessor = accessor;
    }

    public Accessor getAccessor() {
        return accessor;
    }
}
