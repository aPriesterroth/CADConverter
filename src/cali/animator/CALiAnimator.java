package cali.animator;

import cali.maths.CALiMatrix4f;
import cali.model.CALiJoint;
import cali.model.animation.CALiAnimation;
import cali.model.animation.CALiKeyFrame;

import java.util.Map;

public class CALiAnimator {

    private final CALiJoint rootJoint;

    private CALiAnimation animation;
    private float animationTime;

    /**
     * The constructor requires a "CALiJoint" root joint object representing an entity that is being animated.
     *
     * @param rootJoint - the "CALiJoint" root joint object of the entity to animate
     */
    public CALiAnimator(CALiJoint rootJoint) {
        this.rootJoint = rootJoint;
    }

    /**
     * Sets the animation the "CALiAnimator" is currently animating and resets the animation time.
     *
     * @param animation - the animation to animate
     */
    public void animate(CALiAnimation animation) {
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

        CALiKeyFrame current;
        CALiKeyFrame next;

        if((current = getFrameAtOffset(0)) == null || (next = getFrameAtOffset(1)) == null) {
            System.err.print("Unable to retrieve current/next CALiKeyFrame.");
            return;
        }

        float progress = ((animationTime - current.getTimeStamp())
                / (next.getTimeStamp() - current.getTimeStamp()));

        updatePose(CALiKeyFrame.interpolate(current, next, progress), rootJoint, new CALiMatrix4f());
    }

    /**
     * Recursively updates the pose of an entity by the parent transform to a "CALiJoint" object and all of its
     * children likewise.
     *
     * @param currentPose - the pose the animation is currently in
     * @param currentJoint - the currently updating joint
     * @param parentTransform - the transform of the parent of the current joint
     */
    private void updatePose(Map<String, CALiMatrix4f> currentPose, CALiJoint currentJoint, CALiMatrix4f parentTransform) {
        CALiMatrix4f currentTransformLocal = currentPose.get(currentJoint.getName());
        CALiMatrix4f currentTransform = parentTransform.multiply(currentTransformLocal);

        for(CALiJoint child : currentJoint.getChildren()) {
            updatePose(currentPose, child, currentTransform);
        }

        currentJoint.setAnimatedTransform(currentTransform.multiply(currentJoint.getInverseBindTransform()));
    }

    /**
     * Returns a "CALiKeyFrame" object at a specific distance relative to the current frame the animation is in. The
     * offset may not be a value below 0 as an animation is never interpolated backwards, only forwards.
     *
     * @param offset - a distance above 0 within the bounds of the animation frames relative to the current frame
     *
     * @return - the frame at the specified distance relative to the current frame
     */
    private CALiKeyFrame getFrameAtOffset(int offset) {
        if(offset < 0) {
            System.err.println("CALiKeyFrame offset negative.");
            return null;
        }

        CALiKeyFrame[] frames = animation.getKeyFrames();

        int frameIndex = 0;

        for(int i = 0; i < frames.length; i++) {
            if(frames[i].getTimeStamp() > animationTime) {
                frameIndex = i -1;
            }
        }

        if((frameIndex + offset) > frames.length) {
            System.err.println("The offset of a CALiKeyFrame cannot be negative.");
            return null;
        }

        return frames[frameIndex + offset];
    }
}

