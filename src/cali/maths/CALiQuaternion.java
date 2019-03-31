package cali.maths;

/**
 * Created by Aaron on 23.11.17.
 */
public class CALiQuaternion {

    private float w, x, y, z;

    public CALiQuaternion(CALiMatrix4f matrix4f) {
        super();

        float s;
        float tr = matrix4f.m00 + matrix4f.m11 + matrix4f.m22;

        if (tr >= 0.0) {
            s = (float) Math.sqrt(tr + 1.0);
            w = s * 0.5f;
            s = 0.5f / s;
            x = (matrix4f.m21 - matrix4f.m12) * s;
            y = (matrix4f.m02 - matrix4f.m20) * s;
            z = (matrix4f.m10 - matrix4f.m01) * s;
        } else {
            float max = Math.max(Math.max(matrix4f.m00, matrix4f.m11), matrix4f.m22);
            if (max == matrix4f.m00) {
                s = (float) Math.sqrt(matrix4f.m00 - (matrix4f.m11 + matrix4f.m22) + 1.0);
                x = s * 0.5f;
                s = 0.5f / s;
                y = (matrix4f.m01 + matrix4f.m10) * s;
                z = (matrix4f.m20 + matrix4f.m02) * s;
                w = (matrix4f.m21 - matrix4f.m12) * s;
            } else if (max == matrix4f.m11) {
                s = (float) Math.sqrt(matrix4f.m11 - (matrix4f.m22 + matrix4f.m00) + 1.0);
                y = s * 0.5f;
                s = 0.5f / s;
                z = (matrix4f.m12 + matrix4f.m21) * s;
                x = (matrix4f.m01 + matrix4f.m10) * s;
                w = (matrix4f.m02 - matrix4f.m20) * s;
            } else {
                s = (float) Math.sqrt(matrix4f.m22 - (matrix4f.m00 + matrix4f.m11) + 1.0);
                z = s * 0.5f;
                s = 0.5f / s;
                x = (matrix4f.m20 + matrix4f.m02) * s;
                y = (matrix4f.m12 + matrix4f.m21) * s;
                w = (matrix4f.m10 - matrix4f.m01) * s;
            }
        }
    }

    private CALiQuaternion(float w, float x, float y, float z) {
        super();

        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;

        normalize();
    }

    public void normalize() {
        float norm = (float) Math.sqrt((w * w) + (x * x) + (y * y) + (z * z));
        w /= norm;
        x /= norm;
        y /= norm;
        z /= norm;
    }

    public CALiMatrix4f toRotationMatrix() {
        CALiMatrix4f matrix = new CALiMatrix4f();

        float xSquare = x * x, ySquare = y * y, zSquare = z * z;

        //top row
        matrix.m00 = 1 - 2 * (ySquare + zSquare);
        matrix.m01 = 2 * (x*y - z*w);
        matrix.m02 = 2 * (x*z + y*w);

        //middle row
        matrix.m10 = 2 * (x*y + z*w);
        matrix.m11 = 1 - 2 * (xSquare + zSquare);
        matrix.m12 = 2 * (y*z - x*w);

        //bottom row
        matrix.m20 = 2 * (x*z - y*w);
        matrix.m21 = 2 * (y*z + x*w);
        matrix.m22 = 1 - 2 * (xSquare + ySquare);

        return matrix;
    }

    public static CALiQuaternion interpolate(CALiQuaternion quaternion2, CALiQuaternion quaternion1, float blend) {
        CALiQuaternion interpol = new CALiQuaternion(1, 0, 0, 0);

        float dot = (quaternion2.w * quaternion1.w)
                + (quaternion2.x * quaternion1.x)
                + (quaternion2.y * quaternion1.y)
                + (quaternion2.z * quaternion1.z);

        float invBlend = 1f - blend;

        if(dot < 0) {
            interpol.w = invBlend * quaternion2.w + blend * -quaternion1.w;
            interpol.x = invBlend * quaternion2.x + blend * -quaternion1.x;
            interpol.y = invBlend * quaternion2.y + blend * -quaternion1.y;
            interpol.z = invBlend * quaternion2.z + blend * -quaternion1.z;
        } else {
            interpol.w = invBlend * quaternion2.w + blend * quaternion1.w;
            interpol.x = invBlend * quaternion2.x + blend * quaternion1.x;
            interpol.y = invBlend * quaternion2.y + blend * quaternion1.y;
            interpol.z = invBlend * quaternion2.z + blend * quaternion1.z;
        }
        interpol.normalize();

        return interpol;
    }

    @Override
    public String toString() {
        return w + (x >= 0 ? " +" : " ") + x + (y >= 0 ? "i +" : "i ") + y + (z >= 0 ? "j +" : "j ") + z + "k";
    }
}
