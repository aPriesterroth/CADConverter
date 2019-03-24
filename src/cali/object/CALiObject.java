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

    private final String filepath;

    private final boolean correctBlenderCoordinates;

    private final AnimationsLibrary animationsLibrary;

    private final ControllersLibrary controllersLibrary;

    private final EffectsLibrary effectsLibrary;

    private final GeometriesLibrary geometriesLibrary;

    private final ImagesLibrary imagesLibrary;

    private final MaterialsLibrary materialsLibrary;

    private final VisualScenesLibrary visualScenesLibrary;

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
