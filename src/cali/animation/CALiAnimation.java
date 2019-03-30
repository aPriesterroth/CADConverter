package cali.animation;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiAnimation {

    private final String name;

    private final float length;

    private final CALiKeyFrame[] keyFrames;

    public CALiAnimation(String name, float length, CALiKeyFrame[] keyFrames) {
        this.name = name;
        this.length = length;
        this.keyFrames = keyFrames;
    }

    public String getName() {
        return name;
    }

    public float getLength() {
        return length;
    }

    public CALiKeyFrame[] getKeyFrames() {
        return keyFrames;
    }
}
