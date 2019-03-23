package cad.math;

/**
 * Created by Aaron on 23.11.17.
 */
public class Vector3f {

    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f interpolate(Vector3f a, Vector3f b, float progression) {
        float x = a.x + (b.x - a.x) * progression;
        float y = a.y + (b.y - a.y) * progression;
        float z = a.z + (b.z - a.z) * progression;
        return new Vector3f(x, y, z);
    }
}
