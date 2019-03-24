package cali.libraries.visualScenes.visualScene.node.instanceController;

/**
 * Created by Aaron on 17.11.17.
 */
public class InstanceController {

    private String url;

    private String[] skeletons;

    public InstanceController(String url, String[] skeletons) {
        this.url = url;
        this.skeletons = skeletons;
    }

    public String getUrl() {
        return url;
    }

    public String[] getSkeletons() {
        return skeletons;
    }
}

