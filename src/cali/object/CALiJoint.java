package cali.object;

import cali.maths.CALiMatrix4f;

/**
 * Created by Aaron on 18.11.17.
 */
public class CALiJoint {

    public enum CADBoneType {
        CADBone_JOINT, CADBone_NODE
    }

    private int index;

    private String id;
    private String name;
    private String sid;

    private CADBoneType type;

    private CALiMatrix4f animatedTransform;

    private CALiMatrix4f localBindTransform;
    private CALiMatrix4f inverseBindTransform;

    private CALiJoint parent;

    private CALiJoint[] children;

    CALiJoint(String id, String name, CALiMatrix4f localBindTransform, CALiJoint parent) {
        this.id = id;
        this.name = name;
        this.localBindTransform = localBindTransform;
        this.parent = parent;

        this.type = CADBoneType.CADBone_NODE;
    }

    CALiJoint(String id, String name, String sid, CALiMatrix4f localBindTransform, CALiJoint parent) {
        this.id = id;
        this.name = name;
        this.sid = sid;
        this.localBindTransform = localBindTransform;
        this.parent = parent;

        this.type = CADBoneType.CADBone_JOINT;
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSid() {
        return sid;
    }

    public CADBoneType getType() {
        return type;
    }

    public CALiMatrix4f getAnimatedTransform() {
        return animatedTransform;
    }

    public CALiMatrix4f getLocalBindTransform() {
        return localBindTransform;
    }

    public CALiMatrix4f getInverseBindTransform() {
        return inverseBindTransform;
    }

    public CALiJoint getParent() {
        return parent;
    }

    public CALiJoint[] getChildren() {
        return children;
    }

    public void setChildren(CALiJoint[] children) {
        this.children = children;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setInverseBindTransform(CALiMatrix4f inverseBindTransform) {
        this.inverseBindTransform = inverseBindTransform;
    }

    public void setAnimatedTransform(CALiMatrix4f animatedTransform) {
        this.animatedTransform = animatedTransform;
    }

    public void calculateInverseBindTransform(CALiMatrix4f parentBindTransform) {
        CALiMatrix4f bindTransform = parentBindTransform.multiply(localBindTransform);

        inverseBindTransform = bindTransform.invert();
        
        for(int i = 0; i < children.length; i++) {
            if(children[i] == null) {
                System.err.println("Child is null during inverse bind transform calculation!");
                return;
            }
            children[i].calculateInverseBindTransform(bindTransform);
        }
    }
}
