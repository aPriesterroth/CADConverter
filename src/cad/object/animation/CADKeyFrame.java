package cad.object.animation;

import cad.math.Matrix4f;
import cad.object.animation.CADJointTransform;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 23.11.17.
 */
public class CADKeyFrame {

    private final float timeStamp;

    private final Map<String, CADJointTransform> poses;

    public CADKeyFrame(float timeStamp, Map<String, CADJointTransform> poses) {
        this.timeStamp = timeStamp;
        this.poses = poses;
    }

    public CADKeyFrame(float timeStamp) {
        this.timeStamp = timeStamp;
        this.poses = new HashMap<>();
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public Map<String, CADJointTransform> getPoses() {
        return poses;
    }

    public static Map<String, Matrix4f> interpolate(CADKeyFrame current, CADKeyFrame next, float progress) {
        Map<String, Matrix4f> currentPose = new HashMap<String, Matrix4f>();

        for(String jointName : current.getPoses().keySet()) {
            CADJointTransform previousTransform = current.getPoses().get(jointName);
            CADJointTransform nextTransform = next.getPoses().get(jointName);
            CADJointTransform currentTransform = CADJointTransform.interpolate(previousTransform, nextTransform, progress);

            currentPose.put(jointName, currentTransform.getLocalTransform());
        }
        return currentPose;
    }
}