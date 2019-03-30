package cali.commons.source;

import cali.commons.source.techniqueCommon.TechniqueCommon;
import cali.interfaces.CALiDataStructure;

/**
 * Created by Aaron on 17.11.17.
 */
public class Source implements CALiDataStructure {

    private String id;

    private BasicArray array;

    private TechniqueCommon techniqueCommon;

    public Source(String id, BasicArray array, TechniqueCommon techniqueCommon){
        this.id = id;
        this.array = array;
        this.techniqueCommon = techniqueCommon;
    }

    public String getId() {
        return id;
    }

    public BasicArray getArray() {
        return array;
    }

    public TechniqueCommon getTechniqueCommon() {
        return techniqueCommon;
    }

    @Override
    public String getSemantic() {
        return CALiDataStructure.NO_SEMANTIC;
    }
}
