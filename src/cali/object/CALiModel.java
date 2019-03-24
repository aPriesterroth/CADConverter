package cali.object;

public class CALiModel {

    private float[] vertices;
    private float[] textures;
    private float[] normals;
    private float[] weights;

    private int[] indices;
    private int[] jointIds;

    private int jointCount;
    private CALiJoint rootJoint;

    public CALiModel(CALiRawModel rawObject) {

    }
}
