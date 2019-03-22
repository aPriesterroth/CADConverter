package cad.libraries.controllers.controller.skin;

import cad.commons.Input;

/**
 * Created by Aaron on 17.11.17.
 */

public class Joints {

    private Input[] inputs;

    public Joints(Input[] inputs) {
        this.inputs = inputs;
    }

    public Input[] getInputs() {
        return inputs;
    }
}
