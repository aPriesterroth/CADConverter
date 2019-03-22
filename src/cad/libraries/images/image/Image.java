package cad.libraries.images.image;

/**
 * Created by Aaron on 17.11.17.
 */
public class Image {

    private String id;
    private String name;

    private String initFrom;

    public Image(String id, String name, String initFrom){
        this.id = id;
        this.name = name;
        this.initFrom = initFrom;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInitFrom() {
        return initFrom;
    }
}

