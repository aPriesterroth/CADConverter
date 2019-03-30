package cali.animation;

import java.util.ArrayList;

public class CALiKeyFrameData {

    public final float time;
    public final ArrayList<CALiJointTransformData> jointTransforms = new ArrayList<>();

    public CALiKeyFrameData(float time) {
        this.time = time;
    }

    public void addJointTransform(CALiJointTransformData transform) {
        jointTransforms.add(transform);
    }
}
