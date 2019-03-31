package cali.animation;

import java.util.Map;

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

    /**
     *
     * @return the animation in a human-readable format for debugging purposes represented by a string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Name: ").append(name).append("\n");
        builder.append("Length: ").append(length).append("\n");

        for(CALiKeyFrame frame : keyFrames) {
            builder.append("    - ").append("Timestamp: ").append(frame.getTimeStamp()).append("\n");

            for(Map.Entry<String, CALiJointTransform> entry : frame.getPoses().entrySet()) {
                String key = entry.getKey();
                CALiJointTransform value = entry.getValue();
                builder.append("    - ").append(key).append("\n");
                builder.append("        - ").append("pos=").append(value.getPosition().toString()).append("\n");
                builder.append("        - ").append("rot=").append(value.getRotation().toString()).append("\n");
            }
            builder.append("--------------------------------------------------------------------").append("\n");
        }
        return builder.toString();
    }
}
