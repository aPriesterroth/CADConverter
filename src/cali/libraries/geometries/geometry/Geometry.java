package cali.libraries.geometries.geometry;

import cali.libraries.geometries.geometry.mesh.Mesh;

/**
 * Created by Aaron on 17.11.17.
 */
public class Geometry {

    private String id;
    private String name;

    private Mesh mesh;

    public Geometry(String id, String name, Mesh mesh){
        this.id = id;
        this.name = name;
        this.mesh = mesh;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Mesh getMesh() {
        return mesh;
    }
}

