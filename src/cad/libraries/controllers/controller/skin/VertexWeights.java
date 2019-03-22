package cad.libraries.controllers.controller.skin;

import cad.commons.Input;

/**
 * Created by Aaron on 17.11.17.
 */
public class VertexWeights {

    private Input[] inputs;

    private int[] vCount;
    private int[] v;

    public VertexWeights(Input[] inputs, int[] vCount, int[] v) {
        this.inputs = inputs;
        this.vCount = vCount;
        this.v = v;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public int[] getVCount() {
        return vCount;
    }

    public int[] getV() {
        return v;
    }
}