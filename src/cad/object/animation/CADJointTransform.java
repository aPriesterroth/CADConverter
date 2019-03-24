package cad.object.animation;

import cad.maths.CADMatrix4f;
import cad.maths.CADQuaternion;
import cad.maths.CADVector3f;

/**
 * Created by Aaron on 23.11.17.
 */
public class CADJointTransform {

    private final CADVector3f position;
    private final CADQuaternion rotation;

    public CADJointTransform(CADVector3f position, CADQuaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public CADVector3f getPosition() {
        return position;
    }

    public CADQuaternion getRotation() {
        return rotation;
    }

    //TODO: Verify correct behaviour of this method (does translate() work properly, does this concatenation work
    //TODO: properly?
    public CADMatrix4f getLocalTransform() {
        return new CADMatrix4f().translate(position).multiply(rotation.toRotationMatrix());
    }

    public static CADJointTransform interpolate(CADJointTransform a, CADJointTransform b, float progression) {
        return new CADJointTransform(CADVector3f.interpolate(a.getPosition(), b.getPosition(), progression),
                CADQuaternion.interpolate(a.getRotation(), b.getRotation(), progression));
    }
}
