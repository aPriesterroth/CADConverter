package cad.libraries.images;

import cad.libraries.images.image.Image;

/**
 * Created by Aaron on 17.11.17.
 */
public class ImagesLibrary {

    private Image image;

    public ImagesLibrary(Image image){
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}