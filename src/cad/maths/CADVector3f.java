package cad.maths;

/**
 * Created by Aaron on 23.11.17.
 */
public class CADVector3f extends CADVector2f {

    public float z;

    public CADVector3f(CADVector3f vector3f) {
        super(vector3f.x, vector3f.y);
        this.z = vector3f.z;
    }

    public CADVector3f(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public float getLength() {
        return (float) Math.sqrt((double) getLengthSquared());
    }

    @Override
    public float getLengthSquared() {
        return super.getLengthSquared() + z * z;
    }

    @Override
    public String toString() {
        return "(" + x + "/" + y + "/" + z + ")";
    }

    public static CADVector3f interpolate(CADVector3f a, CADVector3f b, float progression) {
        float x = a.x + (b.x - a.x) * progression;
        float y = a.y + (b.y - a.y) * progression;
        float z = a.z + (b.z - a.z) * progression;
        return new CADVector3f(x, y, z);
    }
}
