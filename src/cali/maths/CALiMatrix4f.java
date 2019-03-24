package cali.maths;

public class CALiMatrix4f {

    private static final int MATRIX_DIMENSION = 4;

    public float m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33;

    public CALiMatrix4f() {
        super();
        setIdentity();
    }

    public CALiMatrix4f(float[] components) {
        super();
        if(components.length == (MATRIX_DIMENSION * MATRIX_DIMENSION)) {
            m00 = components[0];
            m01 = components[1];
            m02 = components[2];
            m03 = components[3];
            m10 = components[4];
            m11 = components[5];
            m12 = components[6];
            m13 = components[7];
            m20 = components[8];
            m21 = components[9];
            m22 = components[10];
            m23 = components[11];
            m30 = components[12];
            m31 = components[13];
            m32 = components[14];
            m33 = components[15];
        } else {
            System.err.println("Unable to parse float array of invalid size.");
            setIdentity();
        }
    }

    public CALiMatrix4f(CALiMatrix4f matrix) {
        super();
        m00 = matrix.m00;
        m01 = matrix.m01;
        m02 = matrix.m02;
        m03 = matrix.m03;
        m10 = matrix.m10;
        m11 = matrix.m11;
        m12 = matrix.m12;
        m13 = matrix.m13;
        m20 = matrix.m20;
        m21 = matrix.m21;
        m22 = matrix.m22;
        m23 = matrix.m23;
        m30 = matrix.m30;
        m31 = matrix.m31;
        m32 = matrix.m32;
        m33 = matrix.m33;
    }

    public void setIdentity() {
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m03 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m13 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
    }

    public CALiMatrix4f multiply(CALiMatrix4f matrix4f) {
        
        CALiMatrix4f multiplicar = new CALiMatrix4f();
        
        multiplicar.m00 = m00 * matrix4f.m00 + m10 * matrix4f.m01 + m20 * matrix4f.m02 + m30 * matrix4f.m03;
        multiplicar.m01 = m01 * matrix4f.m00 + m11 * matrix4f.m01 + m21 * matrix4f.m02 + m31 * matrix4f.m03;
        multiplicar.m02 = m02 * matrix4f.m00 + m12 * matrix4f.m01 + m22 * matrix4f.m02 + m32 * matrix4f.m03;
        multiplicar.m03 = m03 * matrix4f.m00 + m13 * matrix4f.m01 + m23 * matrix4f.m02 + m33 * matrix4f.m03;
        multiplicar.m10 = m00 * matrix4f.m10 + m10 * matrix4f.m11 + m20 * matrix4f.m12 + m30 * matrix4f.m13;
        multiplicar.m11 = m01 * matrix4f.m10 + m11 * matrix4f.m11 + m21 * matrix4f.m12 + m31 * matrix4f.m13;
        multiplicar.m12 = m02 * matrix4f.m10 + m12 * matrix4f.m11 + m22 * matrix4f.m12 + m32 * matrix4f.m13;
        multiplicar.m13 = m03 * matrix4f.m10 + m13 * matrix4f.m11 + m23 * matrix4f.m12 + m33 * matrix4f.m13;
        multiplicar.m20 = m00 * matrix4f.m20 + m10 * matrix4f.m21 + m20 * matrix4f.m22 + m30 * matrix4f.m23;
        multiplicar.m21 = m01 * matrix4f.m20 + m11 * matrix4f.m21 + m21 * matrix4f.m22 + m31 * matrix4f.m23;
        multiplicar.m22 = m02 * matrix4f.m20 + m12 * matrix4f.m21 + m22 * matrix4f.m22 + m32 * matrix4f.m23;
        multiplicar.m23 = m03 * matrix4f.m20 + m13 * matrix4f.m21 + m23 * matrix4f.m22 + m33 * matrix4f.m23;
        multiplicar.m30 = m00 * matrix4f.m30 + m10 * matrix4f.m31 + m20 * matrix4f.m32 + m30 * matrix4f.m33;
        multiplicar.m31 = m01 * matrix4f.m30 + m11 * matrix4f.m31 + m21 * matrix4f.m32 + m31 * matrix4f.m33;
        multiplicar.m32 = m02 * matrix4f.m30 + m12 * matrix4f.m31 + m22 * matrix4f.m32 + m32 * matrix4f.m33;
        multiplicar.m33 = m03 * matrix4f.m30 + m13 * matrix4f.m31 + m23 * matrix4f.m32 + m33 * matrix4f.m33;

        return multiplicar;
    }
    
    public CALiMatrix4f multiply(float factor) {
        CALiMatrix4f multiplicar = new CALiMatrix4f();

        multiplicar.m00 = m00 * factor;
        multiplicar.m11 = m11 * factor;
        multiplicar.m22 = m22 * factor;
        multiplicar.m33 = m33 * factor;
        multiplicar.m01 = m10 * factor;
        multiplicar.m10 = m01 * factor;
        multiplicar.m20 = m02 * factor;
        multiplicar.m02 = m20 * factor;
        multiplicar.m12 = m21 * factor;
        multiplicar.m21 = m12 * factor;
        multiplicar.m03 = m30 * factor;
        multiplicar.m30 = m03 * factor;
        multiplicar.m13 = m31 * factor;
        multiplicar.m31 = m13 * factor;
        multiplicar.m32 = m23 * factor;
        multiplicar.m23 = m32 * factor;

        return multiplicar;
    }

    public float determinant() {
        return determinant4x4(m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33);
    }
    
    public static float determinant4x4(float m00, float m01, float m02, float m03,
                                       float m10, float m11, float m12, float m13,
                                       float m20, float m21, float m22, float m23,
                                       float m30, float m31, float m32, float m33) {
        float det = m00 * ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)
                - m13 * m22 * m31
                - m11 * m23 * m32
                - m12 * m21 * m33);
        det -= m01 * ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)
                - m13 * m22 * m30
                - m10 * m23 * m32
                - m12 * m20 * m33);
        det += m02 * ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)
                - m13 * m21 * m30
                - m10 * m23 * m31
                - m11 * m20 * m33);
        det -= m03 * ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)
                - m12 * m21 * m30
                - m10 * m22 * m31
                - m11 * m20 * m32);
        return det;
    }
    
    private static float determinant3x3(float m00, float m01, float m02,
                                        float m10, float m11, float m12,
                                        float m20, float m21, float m22) {
        return m00 * (m11 * m22 - m12 * m21) 
                + m01 * (m12 * m20 - m10 * m22) 
                + m02 * (m10 * m21 - m11 * m20);
    }

    public CALiMatrix4f transpose() {
        CALiMatrix4f matrix4f = new CALiMatrix4f(this);
        
        matrix4f.m00 = m00;
        matrix4f.m01 = m10;
        matrix4f.m02 = m20;
        matrix4f.m03 = m30;
        matrix4f.m10 = m01;
        matrix4f.m11 = m11;
        matrix4f.m12 = m21;
        matrix4f.m13 = m31;
        matrix4f.m20 = m02;
        matrix4f.m21 = m12;
        matrix4f.m22 = m22;
        matrix4f.m23 = m32;
        matrix4f.m30 = m03;
        matrix4f.m31 = m13;
        matrix4f.m32 = m23;
        matrix4f.m33 = m33;

        return matrix4f;
    }

    public CALiMatrix4f invert() {
        float determinant = determinant();

        if(determinant == 0) {
            return null;
        }

        CALiMatrix4f matrix4f = new CALiMatrix4f();

        // first row
        matrix4f.m00 = determinant3x3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
        matrix4f.m01 = -determinant3x3(m10, m12, m13, m20, m22, m23, m30, m32, m33);
        matrix4f.m02 = determinant3x3(m10, m11, m13, m20, m21, m23, m30, m31, m33);
        matrix4f.m03 = -determinant3x3(m10, m11, m12, m20, m21, m22, m30, m31, m32);
        // second row
        matrix4f.m10 = -determinant3x3(m01, m02, m03, m21, m22, m23, m31, m32, m33);
        matrix4f.m11 = determinant3x3(m00, m02, m03, m20, m22, m23, m30, m32, m33);
        matrix4f.m12 = -determinant3x3(m00, m01, m03, m20, m21, m23, m30, m31, m33);
        matrix4f.m13 = determinant3x3(m00, m01, m02, m20, m21, m22, m30, m31, m32);
        // third row
        matrix4f.m20 = determinant3x3(m01, m02, m03, m11, m12, m13, m31, m32, m33);
        matrix4f.m21 = -determinant3x3(m00, m02, m03, m10, m12, m13, m30, m32, m33);
        matrix4f.m22 = determinant3x3(m00, m01, m03, m10, m11, m13, m30, m31, m33);
        matrix4f.m23 = -determinant3x3(m00, m01, m02, m10, m11, m12, m30, m31, m32);
        // fourth row
        matrix4f.m30 = -determinant3x3(m01, m02, m03, m11, m12, m13, m21, m22, m23);
        matrix4f.m31 = determinant3x3(m00, m02, m03, m10, m12, m13, m20, m22, m23);
        matrix4f.m32 = -determinant3x3(m00, m01, m03, m10, m11, m13, m20, m21, m23);
        matrix4f.m33 = determinant3x3(m00, m01, m02, m10, m11, m12, m20, m21, m22);

        return matrix4f.multiply(1.0f/determinant);
    }

    public CALiMatrix4f translate(CALiVector3f vector3f) {
        CALiMatrix4f matrix4f = new CALiMatrix4f();

        matrix4f.m30 += m00 * vector3f.x + m10 * vector3f.y + m20 * vector3f.z;
        matrix4f.m31 += m01 * vector3f.x + m11 * vector3f.y + m21 * vector3f.z;
        matrix4f.m32 += m02 * vector3f.x + m12 * vector3f.y + m22 * vector3f.z;
        matrix4f.m33 += m03 * vector3f.x + m13 * vector3f.y + m23 * vector3f.z;

        return matrix4f;
    }

    public CALiMatrix4f rotate(float angle, CALiVector3f axis) {
        CALiMatrix4f matrix4f = new CALiMatrix4f();

        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);

        float f00 = axis.x * axis.x * (1.0f - c) + c;
        float f01 = (axis.x * axis.y) * (1.0f - c) + (axis.z * s);
        float f02 = (axis.x * axis.z) * (1.0f - c) - (axis.y * s);
        float f10 = (axis.x * axis.y) * (1.0f - c) - (axis.z * s);
        float f11 = axis.y * axis.y * (1.0f - c) + c;
        float f12 = (axis.y * axis.z) * (1.0f - c) + (axis.x * s);
        float f20 = (axis.x * axis.z) * (1.0f - c) + (axis.y * s);
        float f21 = (axis.y * axis.z) * (1.0f - c) - (axis.x * s);
        float f22 = axis.z * axis.z * (1.0f - c) + c;

        matrix4f.m20 = m00 * f20 + m10 * f21 + m20 * f22;
        matrix4f.m21 = m01 * f20 + m11 * f21 + m21 * f22;
        matrix4f.m22 = m02 * f20 + m12 * f21 + m22 * f22;
        matrix4f.m23 = m03 * f20 + m13 * f21 + m23 * f22;
        matrix4f.m00 = m00 * f00 + m10 * f01 + m20 * f02;
        matrix4f.m01 = m01 * f00 + m11 * f01 + m21 * f02;
        matrix4f.m02 = m02 * f00 + m12 * f01 + m22 * f02;
        matrix4f.m03 = m03 * f00 + m13 * f01 + m23 * f02;
        matrix4f.m10 = m00 * f10 + m10 * f11 + m20 * f12;
        matrix4f.m11 = m01 * f10 + m11 * f11 + m21 * f12;
        matrix4f.m12 = m02 * f10 + m12 * f11 + m22 * f12;
        matrix4f.m13 = m03 * f10 + m13 * f11 + m23 * f12;

        return matrix4f;
    }
}
