package cali.model.animation;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiAnimation {

    private final float length;

    private final CALiKeyFrame[] keyFrames;

    public CALiAnimation(float length, CALiKeyFrame[] keyFrames) {
        this.length = length;
        this.keyFrames = keyFrames;
    }

    public float getLength() {
        return length;
    }

    public CALiKeyFrame[] getKeyFrames() {
        return keyFrames;
    }
}
