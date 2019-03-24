package cali.model;

public class CALiModel extends CALiRawModel {

    public CALiModel(String filepath, boolean correctBlenderCoordinates) {
        super(filepath, correctBlenderCoordinates);
    }

    private CALiModel(CALiModel model) {
        super(model);
    }

    public CALiModel extractMinimalModel() {
        return new CALiModel(this);
    }
}
