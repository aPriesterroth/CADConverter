package cad.libraries.visualScenes;

import cad.libraries.visualScenes.visualScene.VisualScene;

/**
 * Created by Aaron on 17.11.17.
 */
public class VisualScenesLibrary {

    private VisualScene visualScene;

    public VisualScenesLibrary(VisualScene visualScene){
        this.visualScene = visualScene;
    }

    public VisualScene getVisualScene() {
        return visualScene;
    }
}
