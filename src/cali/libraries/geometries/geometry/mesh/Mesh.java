package cali.libraries.geometries.geometry.mesh;

import cali.commons.source.Source;

/**
 * Created by Aaron on 17.11.17.
 */
public class Mesh {

    public enum MeshType {
        POLYLIST, TRIANGLES
    }

    private Source[] sources;

    private Vertices vertices;

    private Triangles triangles;

    private Polylist polylist;

    private MeshType type;

    public Mesh(Source[] sources, Vertices vertices, Polylist polylist) {
        this.sources = sources;
        this.vertices = vertices;
        this.polylist = polylist;

        this.type = MeshType.POLYLIST;
    }

    public Mesh(Source[] sources, Vertices vertices, Triangles triangles) {
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

    public Triangles getTriangles() {
        return triangles;
    }

    public Polylist getPolylist() {
        return polylist;
    }

    public MeshType getType() {
        return type;
    }
}

