package cali.animation;

/**
 * Implementation of an animation represented by a name, the length of the animation and an array of CALiKeyframes.
 * For every keyframe, the array saves the time stamp of the keyframe as well as a map of joint names and their
 * corresponding CALiJointTransform containing a position represented by a vector and a rotation represented by a
 * quaternion at that specific keyframe.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 23.11.17.
 */
public class CALiAnimation {

    private final String name;

    private final float length;

    private final CALiKeyFrame[] keyFrames;

    /**
     *
     * @param name the name of the animation
     * @param length the total length (duration) of the animation in seconds
     * @param keyFrames the array of keyframes the animation consists of
     */
    public CALiAnimation(String name, float length, CALiKeyFrame[] keyFrames) {
        this.name = name;
        this.length = length;
        this.keyFrames = keyFrames;
    }

    /**
     *
     * @return the name of the current animation instance
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the length of the animation instance
     */
    public float getLength() {
        return length;
    }

    /**
     *
     * @return the array of CALiKeyFrames of the animation instance
     */
    public CALiKeyFrame[] getKeyFrames() {
        return keyFrames;
    }
}
