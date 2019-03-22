package cad.libraries.animations.animation;

import cad.commons.Input;

/**
 * Created by Aaron on 17.11.17.
 */
public class Sampler {

    private String id;

    private Input[] inputs;

    public Sampler(String id, Input[] inputs) {
        this.id = id;
        this.inputs = inputs;
    }

    public String getId() {
        return id;
    }

    public Input[] getInputs() {
        return inputs;
    }
}

