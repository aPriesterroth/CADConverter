package cad.libraries.effects.effect;

import cad.libraries.effects.effect.profileCommon.ProfileCOMMON;

/**
 * Created by Aaron on 17.11.17.
 */
public class Effect {

    private String id;
    private String name;

    private ProfileCOMMON profileCOMMON;

    public Effect(String id, String name, ProfileCOMMON profileCOMMON){
        this.id = id;
        this.name = name;
        this.profileCOMMON = profileCOMMON;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProfileCOMMON getProfileCOMMON() {
        return profileCOMMON;
    }
}