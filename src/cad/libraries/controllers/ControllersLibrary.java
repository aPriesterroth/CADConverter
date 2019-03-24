package cad.libraries.controllers;

import cad.commons.source.Source;
import cad.libraries.Library;
import cad.libraries.controllers.controller.Controller;
import cad.libraries.controllers.controller.skin.Joints;
import cad.libraries.controllers.controller.skin.VertexWeights;

/**
 * Created by Aaron on 17.11.17.
 */
public class ControllersLibrary implements Library {

    private Controller controller;

    public ControllersLibrary(Controller controller){
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public float[] getBindShapeMatrix() {
        if(isDataMissing()) {
            return null;
        }
        return controller.getSkin().getBindShapeMatrix();
    }

    public Source[] getSources() {
        if(isDataMissing()) {
            return null;
        }
        return controller.getSkin().getSources();
    }

    public Joints getJoints() {
        if(isDataMissing()) {
            return null;
        }
        return controller.getSkin().getJoints();
    }

    public VertexWeights getVertexWeights() {
        if(isDataMissing()) {
            return null;
        }
        return controller.getSkin().getVertexWeights();
    }

    @Override
    public boolean isDataMissing() {
        return controller == null || controller.getSkin() == null;
    }
}

