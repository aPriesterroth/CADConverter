package cad.object;

import cad.maths.CADVector3f;

public class CADVertex {

    private CADVector3f position;

    private int index;

    private int textureIndex = -1;
    private int normalIndex = -1;

    private float length;

    private CADVertex duplicate;

    private CADVertexSkinData weightsData;

    public CADVertex(int index, CADVector3f position, CADVertexSkinData weightsData) {
        this.index = index;
        this.position = position;
        this.length = position.getLength();
        this.weightsData = weightsData;
    }

    public boolean hasTextureIndex() {
        return textureIndex != -1;
    }

    public boolean hasNormalIndex() {
        return normalIndex != -1;
    }

    public boolean hasIndiciesSet() {
        return hasTextureIndex() && hasNormalIndex();
    }

    public boolean textureIndexEquals(int textureIndex) {
        return this.textureIndex == textureIndex;
    }

    public boolean normalIndexEquals(int normalIndex) {
        return this.normalIndex == normalIndex;
    }

    public boolean indicesEquals(int textureIndex, int normalIndex) {
        return textureIndexEquals(textureIndex) && normalIndexEquals(normalIndex);
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public void setNormalIndex(int normalIndex) {
        this.normalIndex = normalIndex;
    }

    public int getIndex() {
        return index;
    }

    public CADVertex getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(CADVertex duplicate) {
        this.duplicate = duplicate;
    }

    public CADVector3f getPosition() {
        return position;
    }

    public CADVertexSkinData getWeightsData() {
        return weightsData;
    }

    public float getLength() {
        return length;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public int getNormalIndex() {
        return normalIndex;
    }
}
