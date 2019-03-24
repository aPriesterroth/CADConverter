package cali.libraries.geometries.geometry.mesh;

import cali.commons.Input;

/**
 * Created by Aaron on 17.11.17.
 */
public class Triangles {

    private int triangleCount;

    private String material;

    private Input[] inputs;

    private int[] paragraph;

    public Triangles(int triangleCount, String material, Input[] inputs, int[] paragraph) {
        this.triangleCount = triangleCount;
        this.material = material;
        this.inputs = inputs;
        this.paragraph = paragraph;
    }

    public int getTriangleCount() {
        return triangleCount;
    }

    public String getMaterial() {
        return material;
    }

    public Input[] getInputs() {
        return inputs;
    }

    public int[] getParagraph() {
        return paragraph;
    }
}
