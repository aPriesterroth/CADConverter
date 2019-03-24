package cad.maths;

public class CADVector4f extends CADVector3f {

    public float w;

    public CADVector4f(CADVector4f vector4f) {
        super(vector4f.x, vector4f.y, vector4f.z);
        this.w = vector4f.w;
    }

    public CADVector4f(float x, float y, float z, float w) {
        super(x, y, z);
        this.w = w;
    }

    @Override
    public float getLength() {
        return (float) Math.sqrt((double) getLengthSquared());
    }

    @Override
    public float getLengthSquared() {
        return super.getLengthSquared() + w * w;
    }

    @Override
    public String toString() {
        return "(" + x + "/" + y + "/" + z + "/" + w + ")";
    }

    public CADVector4f transform(CADMatrix4f matrix) {
        CADVector4f vector = new CADVector4f(this);

        vector.x = matrix.m00 * x + matrix.m10 * y + matrix.m20 * z + matrix.m30 * w;
        vector.y = matrix.m01 * x + matrix.m11 * y + matrix.m21 * z + matrix.m31 * w;
        vector.z = matrix.m02 * x + matrix.m12 * y + matrix.m22 * z + matrix.m32 * w;
        vector.w = matrix.m03 * x + matrix.m13 * y + matrix.m23 * z + matrix.m33 * w;

        return vector;
    }
}
