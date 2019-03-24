package cali.libraries.effects.effect.profileCommon;

import cali.libraries.effects.effect.profileCommon.technique.Technique;

/**
 * Created by Aaron on 17.11.17.
 */
public class ProfileCOMMON {

    private Technique technique;

    public ProfileCOMMON(Technique technique){
        this.technique = technique;
    }

    public Technique getTechnique() {
        return technique;
    }
}

