package cad.libraries.visualScenes.visualScene;

import cad.libraries.visualScenes.visualScene.node.Node;

/**
 * Created by Aaron on 17.11.17.
 */
public class VisualScene {

    private Node[] nodes;

    public VisualScene(Node[] nodes) {
        this.nodes = nodes;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
