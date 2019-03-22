package cad.math;

import java.util.Arrays;

/**
 * Created by Aaron on 17.11.17.
 */
public class Matrix4f {

    private static final int DIMENSION = 4;

    private float[][] components;

    public Matrix4f(Matrix4f matrix) {
        this.components = matrix.getComponents();
    }

    public Matrix4f(float[] components){
        this.components = new float[DIMENSION][DIMENSION];

        int index = 0;

        for(int i = 0; i < DIMENSION; i++)
            for(int j = 0; j < DIMENSION; j++)
                this.components[i][j] = components[index++];
    }

    public Matrix4f(){
        this.components = new float[DIMENSION][DIMENSION];

        this.components[0][0] = 1;
        this.components[1][1] = 1;
        this.components[2][2] = 1;
        this.components[3][3] = 1;
    }

    private Matrix4f(float[][] components){
        this.components = components;
    }

    public float[][] getComponents() {
        return components;
    }

    public Matrix4f multiply(Matrix4f matrix) {

        Matrix4f multiplicar = new Matrix4f();

        for (int i = 0; i < DIMENSION; i++)
            for (int j = 0; j < DIMENSION; j++)
                for (int k = 0; k < DIMENSION; k++)
                    multiplicar.components[i][j] += components[i][k] * matrix.components[k][j];

        return multiplicar;
    }

    public void multiply(float constant){

        for(int i = 0; i < DIMENSION; i++)
            for(int j = 0; j < DIMENSION; j++)
                components[i][j] *= constant;
    }

    private Matrix4f trim(int dimension, int row, int column){
        float[][] trimmed = new float[dimension - 1][dimension - 1];

        int rIndex = 0, cIndex = 0;

        for(int i = 0; i < dimension; i++){
            if(i == row)
                continue;

            for(int j = 0; j < dimension; j++){
                if(j == column)
                    continue;

                trimmed[rIndex][cIndex++] = components[i][j];
            }
            cIndex = 0;
            rIndex++;
        }
        return new Matrix4f(trimmed);
    }

    private float determinant(){
        return determinant(this);
    }

    private float determinant(Matrix4f matrix){

        int dim = matrix.components.length;

        if(dim == 1)
            return matrix.components[0][0];

        else if(dim == 2)
            return (matrix.components[0][0] * matrix.components[1][1]) -
                    (matrix.components[0][1] * matrix.components[1][0]);

        else {
            float sum = 0.0f;

            for(int i = 0; i < matrix.components.length; i++)
                sum += switchSign(i) * matrix.components[0][i] * determinant(matrix.trim(matrix.dimension(), 0, i));

            return sum;
        }
    }

    private int dimension(){
        return this.components.length;
    }

    private int switchSign(int i){
        return i % 2 == 0 ? 1 : -1;
    }

    private Matrix4f transpose(){
        return transpose(this);
    }

    private Matrix4f transpose(Matrix4f matrix){

        Matrix4f transpose = new Matrix4f();

        for(int i = 0; i < DIMENSION; i++)
            for(int j = 0; j < DIMENSION; j++)
                transpose.components[j][i] = matrix.components[i][j];

        return transpose;
    }

    private Matrix4f cofactor(){
        return cofactor(this);
    }

    private Matrix4f cofactor(Matrix4f matrix){

        Matrix4f cofactor = new Matrix4f();

        for(int i = 0; i < DIMENSION; i++)
            for (int j = 0; j < DIMENSION; j++)
                cofactor.components[i][j] = switchSign(i) * switchSign(j) * determinant(matrix.trim(matrix.dimension(), i, j));

        return cofactor;
    }

    public Matrix4f inverse(){
        return inverse(this);
    }

    private Matrix4f inverse(Matrix4f matrix){

        if(matrix.determinant() == 0)
            throw new UnsupportedOperationException("The Matrix\n " + matrix + " does not have an inverse! ");

        Matrix4f inverse = ((matrix.cofactor()).transpose());

        inverse.multiply(1.0f / matrix.determinant());

        return inverse;
    }

    public Matrix4f translate(Vector3f vector) {
        // 1,1 - 1
        // 2,2 - 2
        // 3,3 - 3
        components[3][0] += components[0][0] * vector.getX() + components[1][0] * vector.getY() + components[2][0] * vector.getZ();
        components[3][1] += components[0][1] * vector.getX() + components[1][1] * vector.getY() + components[2][1] * vector.getZ();
        components[3][2] += components[0][2] * vector.getX() + components[1][2] * vector.getY() + components[2][2] * vector.getZ();
        components[3][3] += components[0][3] * vector.getX() + components[1][3] * vector.getY() + components[2][3] * vector.getZ();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(float[] fa : components) {
            builder.append(Arrays.toString(fa));
            builder.append("\n");
        }
        return builder.toString();
    }
}

