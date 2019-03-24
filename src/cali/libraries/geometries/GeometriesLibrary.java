package cali.libraries.geometries;

import cali.commons.source.Source;
import cali.libraries.Library;
import cali.libraries.geometries.geometry.Geometry;
import cali.libraries.geometries.geometry.mesh.Mesh;
import cali.libraries.geometries.geometry.mesh.Polylist;
import cali.libraries.geometries.geometry.mesh.Triangles;
import cali.libraries.geometries.geometry.mesh.Vertices;

/**
 * Created by Aaron on 17.11.17.
 */
public class GeometriesLibrary implements Library {

    private Geometry geometry;

    public GeometriesLibrary(Geometry geometry){
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Source[] getSources() {
        if(isDataMissing()) {
            return null;
        }
        return geometry.getMesh().getSources();
    }

    public Vertices getVertices() {
        if(isDataMissing()) {
            return null;
        }
        return geometry.getMesh().getVertices();
    }

    public String getVerticesInputSource() {
        if(isDataMissing() || geometry.getMesh().getVertices() == null) {
            return null;
        }
        return geometry.getMesh().getVertices().getInputSource();
    }

    @Deprecated
    public Triangles[] getTriangles() {
        if(isDataMissing()) {
            return null;
        }
        return geometry.getMesh().getTriangles();
    }

    public Polylist[] getPolylists() {
        if(isDataMissing()) {
            return null;
        }
        return geometry.getMesh().getPolylists();
    }

    public Mesh.MeshType getMeshType() {
        if(isDataMissing()) {
            return null;
        }
        return geometry.getMesh().getType();
    }

    @Override
    public boolean isDataMissing() {
        return geometry == null || geometry.getMesh() == null;
    }
}

