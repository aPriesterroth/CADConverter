package cad.object;

public class CADObject {

    private float[] vertices;
    private float[] textures;
    private float[] normals;
    private float[] weights;

    private int[] indices;
    private int[] jointIds;

    private int jointCount;
    private CADJoint rootJoint;

    public CADObject(CADRawObject rawObject) {

    }
}
