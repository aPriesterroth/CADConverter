package cali.maths;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiVector3f extends CALiVector2f {

    public float z;

    public CALiVector3f(CALiVector3f vector3f) {
        super(vector3f.x, vector3f.y);
        this.z = vector3f.z;
    }

    public CALiVector3f(float x, float y, float z) {
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

    public static CALiVector3f interpolate(CALiVector3f a, CALiVector3f b, float progression) {
        float x = a.x + (b.x - a.x) * progression;
        float y = a.y + (b.y - a.y) * progression;
        float z = a.z + (b.z - a.z) * progression;
        return new CALiVector3f(x, y, z);
    }
}
