package cad.libraries.controllers;

import cad.libraries.controllers.controller.Controller;

/**
 * Created by Aaron on 17.11.17.
 */
public class ControllersLibrary {

    private Controller controller;

    public ControllersLibrary(Controller controller){
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }
}

