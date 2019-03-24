package cali.object;

import cali.libraries.animations.AnimationsLibrary;
import cali.libraries.controllers.ControllersLibrary;
import cali.libraries.effects.EffectsLibrary;
import cali.libraries.geometries.GeometriesLibrary;
import cali.libraries.images.ImagesLibrary;
import cali.libraries.materials.MaterialsLibrary;
import cali.libraries.visualScenes.VisualScenesLibrary;
import cali.parser.CALiParser;

public class CALiObject {

    protected final String filepath;

    protected final boolean correctBlenderCoordinates;

    protected final AnimationsLibrary animationsLibrary;

    protected final ControllersLibrary controllersLibrary;

    protected final EffectsLibrary effectsLibrary;

    protected final GeometriesLibrary geometriesLibrary;

    protected final ImagesLibrary imagesLibrary;

    protected final MaterialsLibrary materialsLibrary;

    protected final VisualScenesLibrary visualScenesLibrary;

    public CALiObject(String filepath, boolean correctBlenderCoordinates) {
        this.filepath = filepath;
        this.correctBlenderCoordinates = correctBlenderCoordinates;

        CALiParser parser = new CALiParser(filepath);

        animationsLibrary = parser.parseAnimationsLibrary();

        controllersLibrary = parser.parseControllersLibrary();

        effectsLibrary = parser.parseEffectsLibrary();

        visualScenesLibrary = parser.parseVisualScenesLibrary();

        geometriesLibrary = parser.parseGeometriesLibrary_polylist();

        imagesLibrary = parser.parseImagesLibrary();

        materialsLibrary = parser.parseMaterialsLibrary();
    }

    public String getFilepath() {
        return filepath;
    }

    public boolean isCorrectBlenderCoordinates() {
        return correctBlenderCoordinates;
    }

    public AnimationsLibrary getAnimationsLibrary() {
        return animationsLibrary;
    }

    public ControllersLibrary getControllersLibrary() {
        return controllersLibrary;
    }

    public EffectsLibrary getEffectsLibrary() {
        return effectsLibrary;
    }

    public GeometriesLibrary getGeometriesLibrary() {
        return geometriesLibrary;
    }

    public ImagesLibrary getImagesLibrary() {
        return imagesLibrary;
    }

    public MaterialsLibrary getMaterialsLibrary() {
        return materialsLibrary;
    }

    public VisualScenesLibrary getVisualScenesLibrary() {
        return visualScenesLibrary;
    }

    /**
     * Returns whether or not correction of blender coordinates is desired.
     *
     * @return - true if correction is desired, else false
     */
    public boolean correctBlenderCoordinates() {
        return correctBlenderCoordinates;
    }
}
