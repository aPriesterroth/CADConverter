package cad.object.animation;

/**
 * Created by Aaron on 23.11.17.
 */
public class CADAnimation {

    private final float length;

    private final CADKeyFrame[] keyFrames;

    public CADAnimation(float length, CADKeyFrame[] keyFrames) {
        this.length = length;
        this.keyFrames = keyFrames;
    }

    public float getLength() {
        return length;
    }

    public CADKeyFrame[] getKeyFrames() {
        return keyFrames;
    }
}
