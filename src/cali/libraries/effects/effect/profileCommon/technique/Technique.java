package cali.libraries.effects.effect.profileCommon.technique;

/**
 * Created by Aaron on 17.11.17.
 */
public class Technique {

    private String sid;

    private Phong phong;

    public Technique(String sid, Phong phong){
        this.sid = sid;
        this.phong = phong;
    }

    public String getSid() {
        return sid;
    }

    public Phong getPhong() {
        return phong;
    }
}

