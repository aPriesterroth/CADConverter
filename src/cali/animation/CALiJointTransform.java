package cali.animation;

import cali.maths.CALiMatrix4f;
import cali.maths.CALiQuaternion;
import cali.maths.CALiVector3f;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiJointTransform {

    private final CALiVector3f position;
    private final CALiQuaternion rotation;

    public CALiJointTransform(CALiVector3f position, CALiQuaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public CALiVector3f getPosition() {
        return position;
    }

    public CALiQuaternion getRotation() {
        return rotation;
    }

    //TODO: Verify correct behaviour of this method (does translate() work properly, does this concatenation work
    //TODO: properly?
    public CALiMatrix4f getLocalTransform() {
        return new CALiMatrix4f().translate(position).multiply(rotation.toRotationMatrix());
    }

    public static CALiJointTransform interpolate(CALiJointTransform a, CALiJointTransform b, float progression) {
        return new CALiJointTransform(CALiVector3f.interpolate(a.getPosition(), b.getPosition(), progression),
                CALiQuaternion.interpolate(a.getRotation(), b.getRotation(), progression));
    }
}
