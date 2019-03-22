package cad.libraries.geometries.geometry.mesh;

import cad.commons.Input;

/**
 * Created by Aaron on 17.11.17.
 */
public class Polylist {

    private int polyCount;

    private String material;

    private Input[] inputs;

    private int[] vCount;
    private int[] paragraph;

    public Polylist(int polyCount, String material, Input[] inputs, int[] vCount, int[] paragraph) {
        this.polyCount = polyCount;
        this.material = material;
        this.inputs = inputs;
        this.vCount = vCount;
        this.paragraph = paragraph;
    }

    public int getPolyCount() {
        return polyCount;
    }

    public String getMaterial() {
        return material;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public int[] getvCount() {
        return vCount;
    }

    public int[] getParagraph() {
        return paragraph;
    }
}
