package cad.object.animation;

import cad.math.Matrix4f;
import cad.math.Quaternion;
import cad.math.Vector3f;

/**
 * Created by Aaron on 23.11.17.
 */
public class CADJointTransform {

    private final Vector3f position;
    private final Quaternion rotation;

    public CADJointTransform(Vector3f position, Quaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    //TODO: Verify correct behaviour of this method (does translate() work properly, does this concatenation work
    //TODO: properly?
    public Matrix4f getLocalTransform() {
        return new Matrix4f().translate(position).multiply(rotation.toRotationMatrix());
    }

    public static CADJointTransform interpolate(CADJointTransform a, CADJointTransform b, float progression) {
        return new CADJointTransform(Vector3f.interpolate(a.getPosition(), b.getPosition(), progression),
                Quaternion.interpolate(a.getRotation(), b.getRotation(), progression));
    }
}
