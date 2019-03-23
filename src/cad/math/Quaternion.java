package cad.math;

/**
 * Created by Aaron on 23.11.17.
 */
public class Quaternion {

    private float w, x, y, z;

    public Quaternion() {
        super();
        setIdentity();
    }

    public Quaternion(Matrix4f matrix4f) {
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

    private Quaternion(float w, float x, float y, float z) {
        super();

        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;

        normalize();
    }

    public void setIdentity() {
        x = 0;
        y = 0;
        z = 0;
        w = 1;
    }

    public static Quaternion interpolate(Quaternion q1, Quaternion q2, float blend) {
        Quaternion interpol = new Quaternion(1, 0, 0, 0);

        float dot = (q1.w * q2.w) + (q1.x * q2.x) + (q1.y * q2.y) + (q1.z * q2.z);

        float invBlend = 1f - blend;

        if(dot < 0) {
            interpol.w = invBlend * q1.w + blend * -q2.w;
            interpol.x = invBlend * q1.x + blend * -q2.x;
            interpol.y = invBlend * q1.y + blend * -q2.y;
            interpol.z = invBlend * q1.z + blend * -q2.z;
        } else {
            interpol.w = invBlend * q1.w + blend * q2.w;
            interpol.x = invBlend * q1.x + blend * q2.x;
            interpol.y = invBlend * q1.y + blend * q2.y;
            interpol.z = invBlend * q1.z + blend * q2.z;
        }
        interpol.normalize();

        return interpol;
    }

    public void normalize() {
        float norm = (float) Math.sqrt((w * w) + (x * x) + (y * y) + (z * z));
        w /= norm;
        x /= norm;
        y /= norm;
        z /= norm;
    }

    public Matrix4f toRotationMatrix() {
        Matrix4f matrix = new Matrix4f();

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

    //  Old.
    /*
    public Quaternion(Matrix4f matrix) {
        float t;

        float[][] m = matrix.getComponents();

        if(m[2][2] < 0) {
            if(m[0][0] > m[1][1]) {

                t = 1 + m[0][0] - m[1][1] - m[2][2];

                this.x = t;
                this.y = m[0][1] + m[1][0];
                this.z = m[2][0] + m[0][2];
                this.w = -1 * (m[1][2] - m[2][1]); //Needs conversion from neg. to pos.?
            } else {

                t = 1 - m[0][0] + m[1][1] - m[2][2];

                this.x = m[0][1] + m[1][0];
                this.y = t;
                this.z = m[1][2] + m[2][1];
                this.w = -1 * (m[2][0] - m[0][2]); //Needs conversion from neg. to pos.?
            }
        } else {
            if(m[0][0] < -m[1][1]) {

                t = 1 - m[0][0] - m[1][1] + m[2][2];

                this.x = m[2][0] + m[0][2];
                this.y = m[1][2] + m[2][1];
                this.z = t;
                this.w = -1 * (m[0][1] - m[1][0]); //Needs conversion from neg. to pos.?
            } else {

                t = 1 + m[0][0] + m[1][1] + m[2][2];

                this.x = -1 * (m[1][2] - m[2][1]); //Needs conversion from neg. to pos?
                this.y = -1 * (m[2][0] - m[0][2]); //Needs conversion from neg. to pos?
                this.z = -1 * (m[0][1] - m[1][0]); //Needs conversion from neg. to pos?
                this.w = t;
            }
        }
        multiply(0.5f / (float) Math.sqrt(t));
    }*/

    // Very old.
    /*
    public Quaternion(Matrix4f matrix) {

        float[][] m = matrix.getComponents();

        float diagonal = m[0][0] + m[1][1] + m[2][2];

        if(diagonal > 0) {
            float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);

            this.w = w4 / 4f;
            this.x = (m[2][1] - m[1][2]) / w4;
            this.y = (m[0][2] - m[2][0]) / w4;
            this.z = (m[1][0] - m[0][1]) / w4;

        } else if((m[0][0] > m[1][1]) && (m[0][0] > m[2][2])) {
            float x4 = (float) (Math.sqrt(1f + m[0][0] - m[1][1] - m[2][2]) * 2f);

            this.w = (m[2][1] - m[1][2]) / x4;
            this.x = x4 / 4f;
            this.y = (m[0][1] + m[1][0]) / x4;
            this.z = (m[0][2] + m[2][0]) / x4;

        } else if(m[1][1] > m[2][2]) {
            float y4 = (float) (Math.sqrt(1f + m[1][1] - m[0][0] - m[2][2]) * 2f);

            this.w = (m[0][2] - m[2][0]) / y4;
            this.x = (m[0][1] + m[1][0]) / y4;
            this.y = y4 / 4f;
            this.z = (m[1][2] + m[2][1]) / y4;

        } else {
            float z4 = (float) (Math.sqrt(1f + m[2][2] - m[0][0] - m[1][1]) * 2f);

            this.w = (m[1][0] - m[0][1]) / z4;
            this.x = (m[0][2] + m[2][0]) / z4;
            this.y = (m[1][2] + m[2][1]) / z4;
            this.z = z4 / 4f;
        }
        normalize();
    }
    */
}
