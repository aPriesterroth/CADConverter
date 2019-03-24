package cad.libraries.images;

import cad.libraries.Library;
import cad.libraries.images.image.Image;

/**
 * Created by Aaron on 17.11.17.
 */
public class ImagesLibrary implements Library {

    private Image image;

    public ImagesLibrary(Image image){
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String getId() {
        if(isDataMissing()) {
            return null;
        }
        return image.getId();
    }

    public String getName() {
        if(isDataMissing()) {
            return null;
        }
        return image.getName();
    }

    public String getInitFrom() {
        if(isDataMissing()) {
            return null;
        }
        return image.getInitFrom();
    }

    @Override
    public boolean isDataMissing() {
        return image == null;
    }
}