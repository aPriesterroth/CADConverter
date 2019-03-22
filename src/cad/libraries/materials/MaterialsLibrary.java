package cad.libraries.materials;

import cad.libraries.materials.material.Material;

/**
 * Created by Aaron on 17.11.17.
 */
public class MaterialsLibrary {

    private Material[] materials;

    public MaterialsLibrary(Material[] materials) {
        this.materials = materials;
    }

    public Material[] getMaterials() {
        return materials;
    }
}

