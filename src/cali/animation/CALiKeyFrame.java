package cali.animation;

import cali.maths.CALiMatrix4f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiKeyFrame {

    private final float timeStamp;

    /**
     * A map of joint transforms for every joint represented by its name
     */
    private final Map<String, CALiJointTransform> poses;

    /**
     *
     * @param timeStamp the time stamp the key frame is based on (i.e., the time of the animation this specific
     *                  key frame is occurring in)
     */
    public CALiKeyFrame(float timeStamp) {
        this.timeStamp = timeStamp;
        this.poses = new HashMap<>();
    }

    /**
     *
     * @return the time stamp of the key frame instance
     */
    public float getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @return the map of joint transforms for every joint
     */
    public Map<String, CALiJointTransform> getPoses() {
        return poses;
    }

    /**
     * Interpolates two key frames based on a specific value of progress between 0 and 1. The interpolation is done
     * by interpolating the transforms for every joint from the first key frame with the transforms from the
     * second key frame.
     *
     * @param a the first key frame (i.e., the current/last key frame)
     * @param b the second key frame (i.e., the next key frame)
     * @param progress the progress of the interpolation between the two key frames (i.e., 0.5 would be the
     *                 halve way between the two)
     *
     * @return the interpolated key frame between the two specified key frames based on the specified progression value
     */
    public static Map<String, CALiMatrix4f> interpolate(CALiKeyFrame a, CALiKeyFrame b, float progress) {
        Map<String, CALiMatrix4f> currentPose = new HashMap<String, CALiMatrix4f>();

        for(String jointName : a.getPoses().keySet()) {
            CALiJointTransform previousTransform = a.getPoses().get(jointName);
            CALiJointTransform nextTransform = b.getPoses().get(jointName);
            CALiJointTransform currentTransform = CALiJointTransform.interpolate(previousTransform, nextTransform, progress);

            currentPose.put(jointName, currentTransform.getLocalTransform());
        }
        return currentPose;
    }
}
