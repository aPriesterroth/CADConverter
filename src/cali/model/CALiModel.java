package cali.model;

public class CALiModel extends CALiRawModel {

    public CALiModel(String filepath, String modelName, String animationName, boolean correctBlenderCoordinates) {
        super(filepath, modelName, animationName, correctBlenderCoordinates);
    }

    private CALiModel(CALiModel model) {
        super(model);
    }

    public CALiModel extractMinimalModel() {
        return new CALiModel(this);
    }
}
