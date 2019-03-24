package cad.animator;

import cad.maths.CADMatrix4f;
import cad.object.animation.CADAnimation;
import cad.object.CADJoint;
import cad.object.animation.CADKeyFrame;

import java.util.Map;

public class CADAnimator {

    private final CADJoint rootJoint;

    private CADAnimation animation;
    private float animationTime;

    /**
     * The constructor requires a "CADJoint" root joint object representing an entity that is being animated.
     *
     * @param rootJoint - the "CADJoint" root joint object of the entity to animate
     */
    public CADAnimator(CADJoint rootJoint) {
        this.rootJoint = rootJoint;
    }

    /**
     * Sets the animation the "CADAnimator" is currently animating and resets the animation time.
     *
     * @param animation - the animation to animate
     */
    public void animate(CADAnimation animation) {
        this.animation = animation;
        this.animationTime = 0;
    }

    /**
     * Updates the time of the current animation and progresses the pose of the animation the entity is currently in.
     *
     * @param displayManagerFrameTime - TODO: discover what the displayManagerFrameTime even is
     */
    public void update(float displayManagerFrameTime) {
        if(animation == null) {
            return;
        }

        if((animationTime += displayManagerFrameTime) > animation.getLength()) {
            animationTime %= animation.getLength();
        }

        CADKeyFrame current;
        CADKeyFrame next;

        if((current = getFrameAtOffset(0)) == null || (next = getFrameAtOffset(1)) == null) {
            System.err.print("Unable to retrieve current/next CADKeyFrame.");
            return;
        }

        float progress = ((animationTime - current.getTimeStamp())
                / (next.getTimeStamp() - current.getTimeStamp()));

        updatePose(CADKeyFrame.interpolate(current, next, progress), rootJoint, new CADMatrix4f());
    }

    /**
     * Recursively updates the pose of an entity by the parent transform to a "CADJoint" object and all of its
     * children likewise.
     *
     * @param currentPose - the pose the animation is currently in
     * @param currentJoint - the currently updating joint
     * @param parentTransform - the transform of the parent of the current joint
     */
    private void updatePose(Map<String, CADMatrix4f> currentPose, CADJoint currentJoint, CADMatrix4f parentTransform) {
        CADMatrix4f currentTransformLocal = currentPose.get(currentJoint.getName());
        CADMatrix4f currentTransform = parentTransform.multiply(currentTransformLocal);

        for(CADJoint child : currentJoint.getChildren()) {
            updatePose(currentPose, child, currentTransform);
        }

        currentJoint.setAnimatedTransform(currentTransform.multiply(currentJoint.getInverseBindTransform()));
    }

    /**
     * Returns a "CADKeyFrame" object at a specific distance relative to the current frame the animation is in. The
     * offset may not be a value below 0 as an animation is never interpolated backwards, only forwards.
     *
     * @param offset - a distance above 0 within the bounds of the animation frames relative to the current frame
     *
     * @return - the frame at the specified distance relative to the current frame
     */
    private CADKeyFrame getFrameAtOffset(int offset) {
        if(offset < 0) {
            System.err.println("CADKeyFrame offset negative.");
            return null;
        }

        CADKeyFrame[] frames = animation.getKeyFrames();

        int frameIndex = 0;

        for(int i = 0; i < frames.length; i++) {
            if(frames[i].getTimeStamp() > animationTime) {
                frameIndex = i -1;
            }
        }

        if((frameIndex + offset) > frames.length) {
            System.err.println("The offset of a CADKeyFrame cannot be negative.");
            return null;
        }

        return frames[frameIndex + offset];
    }
}

