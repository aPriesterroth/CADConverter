package cali.animation;

import cali.maths.CALiMatrix4f;


// TODO: get rid of this class and do shit properly
public class CALiJointTransformData {

    public final String jointNameId;
    public final CALiMatrix4f jointLocalTransform;

    public CALiJointTransformData(String jointNameId, CALiMatrix4f jointLocalTransform) {
        this.jointNameId = jointNameId;
        this.jointLocalTransform = jointLocalTransform;
    }
}
