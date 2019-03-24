package cali.libraries.animations.animation;

/**
 * Created by Aaron on 17.11.17.
 */
public class Channel {

    private String source;
    private String target;

    public Channel(String source, String target){
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
}

