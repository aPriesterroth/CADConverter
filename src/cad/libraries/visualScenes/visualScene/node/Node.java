package cad.libraries.visualScenes.visualScene.node;

import cad.libraries.visualScenes.visualScene.node.instanceController.InstanceController;

/**
 * Created by Aaron on 17.11.17.
 */
public class Node {

    private String id;
    private String name;
    private String type;
    private String sid;

    private float[] matrix;

    private Node parent;
    private Node[] children;

    private InstanceController instanceController;

    public Node(String id, String name, String type, String sid, float[] matrix, Node parent, Node[] children) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sid = sid;
        this.matrix = matrix;
        this.parent = parent;
        this.children = children;

        this.instanceController = null;
    }

    public Node(String id, String name, String type, String sid, float[] matrix, Node parent, Node[] children,
                InstanceController instanceController) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sid = sid;
        this.matrix = matrix;
        this.parent = parent;
        this.children = children;
        this.instanceController = instanceController;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSid() {
        return sid;
    }

    public float[] getMatrix() {
        return matrix;
    }

    public Node getParent() {
        return parent;
    }

    public Node[] getChildren() {
        return children;
    }

    public InstanceController getInstanceController() {
        return instanceController;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
