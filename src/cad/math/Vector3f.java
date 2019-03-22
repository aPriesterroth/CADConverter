package cad.math;

/**
 * Created by Aaron on 23.11.17.
 */
public class Vector3f {

    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public static Vector3f interpolate(Vector3f a, Vector3f b, float progression) {
        float x = a.getX() + (b.getX() - a.getX()) * progression;
        float y = a.getY() + (b.getY() - a.getY()) * progression;
        float z = a.getZ() + (b.getZ() - a.getZ()) * progression;
        return new Vector3f(x, y, z);
    }
}
