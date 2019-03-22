package cad.libraries.controllers.controller;

import cad.libraries.controllers.controller.skin.Skin;

/**
 * Created by Aaron on 17.11.17.
 */
public class Controller {

    private Skin skin;

    public Controller(Skin skin){
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
    }
}

