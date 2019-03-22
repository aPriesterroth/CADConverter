package cad.libraries.geometries.geometry.mesh;

import cad.commons.source.Source;

/**
 * Created by Aaron on 17.11.17.
 */
public class Mesh {

    public enum MeshType {
        POLYLISTS, TRIANGLES
    }

    private Source[] sources;

    private Vertices vertices;

    private Triangles[] triangles;

    private Polylist[] polylists;

    private MeshType type;

    public Mesh(Source[] sources, Vertices vertices, Polylist[] polylists) {
        this.sources = sources;
        this.vertices = vertices;
        this.polylists = polylists;

        this.type = MeshType.POLYLISTS;
    }

    public Mesh(Source[] sources, Vertices vertices, Triangles[] triangles) {
        this.sources = sources;
        this.vertices = vertices;
        this.triangles = triangles;

        this.type = MeshType.TRIANGLES;
    }

    public Source[] getSources() {
        return sources;
    }

    public Vertices getVertices() {
        return vertices;
    }

    public Triangles[] getTriangles() {
        return triangles;
    }

    public Polylist[] getPolylists() {
        return polylists;
    }

    public MeshType getType() {
        return type;
    }
}

