package cad.object;

import cad.maths.CADMatrix4f;

/**
 * Created by Aaron on 18.11.17.
 */
public class CADJoint {

    public enum CADBoneType {
        CADBone_JOINT, CADBone_NODE
    }

    private int index;

    private String id;
    private String name;
    private String sid;

    private CADBoneType type;

    private CADMatrix4f animatedTransform;

    private CADMatrix4f localBindTransform;
    private CADMatrix4f inverseBindTransform;

    private CADJoint parent;

    private CADJoint[] children;

    CADJoint(String id, String name, CADMatrix4f localBindTransform, CADJoint parent) {
        this.id = id;
        this.name = name;
        this.localBindTransform = localBindTransform;
        this.parent = parent;

        this.type = CADBoneType.CADBone_NODE;
    }

    CADJoint(String id, String name, String sid, CADMatrix4f localBindTransform, CADJoint parent) {
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

    public CADMatrix4f getAnimatedTransform() {
        return animatedTransform;
    }

    public CADMatrix4f getLocalBindTransform() {
        return localBindTransform;
    }

    public CADMatrix4f getInverseBindTransform() {
        return inverseBindTransform;
    }

    public CADJoint getParent() {
        return parent;
    }

    public CADJoint[] getChildren() {
        return children;
    }

    public void setChildren(CADJoint[] children) {
        this.children = children;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setInverseBindTransform(CADMatrix4f inverseBindTransform) {
        this.inverseBindTransform = inverseBindTransform;
    }

    public void setAnimatedTransform(CADMatrix4f animatedTransform) {
        this.animatedTransform = animatedTransform;
    }

    public void calculateInverseBindTransform(CADMatrix4f parentBindTransform) {
        CADMatrix4f bindTransform = parentBindTransform.multiply(localBindTransform);

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
