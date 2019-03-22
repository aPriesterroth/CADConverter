package cad.libraries.geometries.geometry.mesh;

/**
 * Created by Aaron on 17.11.17.
 */
public class Vertices {

    private String id;
    private String inputSemantic;
    private String inputSource;

    public Vertices(String id, String inputSemantic, String inputSource){
        this.id = id;
        this.inputSemantic = inputSemantic;
        this.inputSource = inputSource;
    }

    public String getId() {
        return id;
    }

    public String getInputSemantic() {
        return inputSemantic;
    }

    public String getInputSource() {
        return inputSource;
    }
}

