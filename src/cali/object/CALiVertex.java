package cali.object;

import cali.maths.CALiVector3f;

public class CALiVertex {

    private CALiVector3f position;

    private int index;

    private int textureIndex = -1;
    private int normalIndex = -1;

    private float length;

    private CALiVertex duplicate;

    private CALiVertexSkinData weightsData;

    public CALiVertex(int index, CALiVector3f position, CALiVertexSkinData weightsData) {
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

    public CALiVertex getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(CALiVertex duplicate) {
        this.duplicate = duplicate;
    }

    public CALiVector3f getPosition() {
        return position;
    }

    public CALiVertexSkinData getWeightsData() {
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
