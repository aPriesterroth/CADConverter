package cad.libraries.visualScenes;

import cad.libraries.Library;
import cad.libraries.visualScenes.visualScene.VisualScene;
import cad.libraries.visualScenes.visualScene.node.Node;

/**
 * Created by Aaron on 17.11.17.
 */
public class VisualScenesLibrary implements Library {

    private VisualScene visualScene;

    public VisualScenesLibrary(VisualScene visualScene){
        this.visualScene = visualScene;
    }

    public VisualScene getVisualScene() {
        return visualScene;
    }

    public Node[] getNodes() {
        if(isDataMissing()) {
            return null;
        }
        return visualScene.getNodes();
    }

    @Override
    public boolean isDataMissing() {
        return visualScene == null;
    }
}
