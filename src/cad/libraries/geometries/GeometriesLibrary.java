package cad.libraries.geometries;

import cad.libraries.geometries.geometry.Geometry;

/**
 * Created by Aaron on 17.11.17.
 */
public class GeometriesLibrary {

    private Geometry geometry;

    public GeometriesLibrary(Geometry geometry){
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}

