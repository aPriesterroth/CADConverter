package cad.maths;

public class CADVector2f implements CADVector {

    public float x, y;

    public CADVector2f(CADVector2f vector2f) {
        this.x = vector2f.x;
        this.y = vector2f.y;
    }

    public CADVector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getLength() {
        return (float) Math.sqrt((double) getLengthSquared());
    }

    @Override
    public float getLengthSquared() {
        return x * x + y * y;
    }

    @Override
    public String toString() {
        return "(" + x + "/" + y + ")";
    }
}
