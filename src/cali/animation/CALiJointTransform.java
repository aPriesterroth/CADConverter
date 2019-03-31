package cali.animation;

import cali.maths.CALiMatrix4f;
import cali.maths.CALiQuaternion;
import cali.maths.CALiVector3f;

/**
 * Implementation of a joint transform represented by a position and a rotation. Joint transform play an essential role
 * for animations as they represent a the position and rotation of a specific joint at a specific time stamp
 * within the animation.
 *
 * @author Aaron Priesterroth
 *
 * Created by Aaron on 23.11.17.
 */
public class CALiJointTransform {

    private final CALiVector3f position;
    private final CALiQuaternion rotation;

    /**
     *
     * @param position the position of the joint
     * @param rotation the rotation of the joint
     */
    public CALiJointTransform(CALiVector3f position, CALiQuaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    /**
     *
     * @return the position of the joint
     */
    public CALiVector3f getPosition() {
        return position;
    }

    /**
     *
     * @return the rotation of the joint
     */
    public CALiQuaternion getRotation() {
        return rotation;
    }

    /**
     * Returns the local transform of the joint as a CALiMatrix4f by translating the position vector into a
     * CALiMatrix4f and multiplying the matrix by the matrix constructed from the rotation.
     *
     * @return the local transform of the joint as a CALiMatrix4f
     */
    public CALiMatrix4f getLocalTransform() {
        return new CALiMatrix4f().translate(position).multiply(rotation.toRotationMatrix());
    }

    /**
     * Interpolates two joint transforms based on a specific value of progress between 0 and 1. The interpolation is
     * done by interpolating the positions and rotations of the two joint transforms.
     *
     * @param a the first joint transform (i.e., the transform the joint currently/last has/had)
     * @param b the second joint transform (i.e., the transform the joint will have in the future)
     * @param progression the progress of the interpolation between the two joint transforms (i.e., 0.5 would be the
     *                    halve way between the two)
     * @return the interpolated joint transform of the two specified joint transforms based on the progression
     * value
     */
    public static CALiJointTransform interpolate(CALiJointTransform a, CALiJointTransform b, float progression) {
        return new CALiJointTransform(CALiVector3f.interpolate(a.getPosition(), b.getPosition(), progression),
                CALiQuaternion.interpolate(a.getRotation(), b.getRotation(), progression));
    }
}
