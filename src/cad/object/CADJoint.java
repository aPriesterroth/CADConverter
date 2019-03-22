package cad.object;

import cad.math.Matrix4f;

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

    private Matrix4f animatedTransform;

    private Matrix4f localBindTransform;
    private Matrix4f inverseBindTransform;

    private CADJoint parent;

    private CADJoint[] children;

    CADJoint(String id, String name, Matrix4f localBindTransform, CADJoint parent, CADJoint[] children) {
        this.id = id;
        this.name = name;
        this.localBindTransform = localBindTransform;
        this.parent = parent;
        this.children = children;

        this.type = CADBoneType.CADBone_NODE;
    }

    CADJoint(String id, String name, String sid, Matrix4f localBindTransform, CADJoint parent, CADJoint[] children) {
        this.id = id;
        this.name = name;
        this.sid = sid;
        this.localBindTransform = localBindTransform;
        this.parent = parent;
        this.children = children;

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

    public Matrix4f getAnimatedTransform() {
        return animatedTransform;
    }

    public Matrix4f getLocalBindTransform() {
        return localBindTransform;
    }

    public Matrix4f getInverseBindTransform() {
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

    public void setInverseBindTransform(Matrix4f inverseBindTransform) {
        this.inverseBindTransform = inverseBindTransform;
    }

    public void setAnimatedTransform(Matrix4f animatedTransform) {
        this.animatedTransform = animatedTransform;
    }

    public void calculateInverseBindTransform(Matrix4f parentBindTransform) {
        Matrix4f bindTransform = parentBindTransform.multiply(localBindTransform);

        inverseBindTransform = bindTransform.inverse();
        
        for(int i = 0; i < children.length; i++) {
            if(children[i] == null) {
                System.err.println("Child is null during inverse bind transform calculation!");
                return;
            }
            children[i].calculateInverseBindTransform(bindTransform);
        }
    }
}
