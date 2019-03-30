package cali.animation;

import cali.maths.CALiMatrix4f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiKeyFrame {

    private final float timeStamp;

    private final Map<String, CALiJointTransform> poses;

    public CALiKeyFrame(float timeStamp, Map<String, CALiJointTransform> poses) {
        this.timeStamp = timeStamp;
        this.poses = poses;
    }

    public CALiKeyFrame(float timeStamp) {
        this.timeStamp = timeStamp;
        this.poses = new HashMap<>();
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public Map<String, CALiJointTransform> getPoses() {
        return poses;
    }

    public static Map<String, CALiMatrix4f> interpolate(CALiKeyFrame current, CALiKeyFrame next, float progress) {
        Map<String, CALiMatrix4f> currentPose = new HashMap<String, CALiMatrix4f>();

        for(String jointName : current.getPoses().keySet()) {
            CALiJointTransform previousTransform = current.getPoses().get(jointName);
            CALiJointTransform nextTransform = next.getPoses().get(jointName);
            CALiJointTransform currentTransform = CALiJointTransform.interpolate(previousTransform, nextTransform, progress);

            currentPose.put(jointName, currentTransform.getLocalTransform());
        }
        return currentPose;
    }
}
