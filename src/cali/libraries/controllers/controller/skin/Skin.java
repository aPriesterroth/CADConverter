package cali.libraries.controllers.controller.skin;

import cali.commons.source.Source;

/**
 * Created by Aaron on 17.11.17.
 */

public class Skin {

    private float[] bindShapeMatrix;

    private Source[] sources;

    private Joints joints;

    private VertexWeights vertexWeights;

    public Skin(float[] bindShapeMatrix, Source[] sources, Joints joints, VertexWeights vertexWeights) {
        this.bindShapeMatrix = bindShapeMatrix;
        this.sources = sources;
        this.joints = joints;
        this.vertexWeights = vertexWeights;
    }

    public float[] getBindShapeMatrix() {
        return bindShapeMatrix;
    }

    public Source[] getSources() {
        return sources;
    }

    public Joints getJoints() {
        return joints;
    }

    public VertexWeights getVertexWeights() {
        return vertexWeights;
    }
}
