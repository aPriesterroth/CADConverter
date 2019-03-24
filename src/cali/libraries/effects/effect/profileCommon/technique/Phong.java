package cali.libraries.effects.effect.profileCommon.technique;

/**
 * Created by Aaron on 17.11.17.
 */
public class Phong {

    private float[] emission;
    private float[] ambient;
    private float[] specular;

    private float shininess;
    private float transparency;
    private float diffuse;

    public Phong(float[] emission, float[] ambient, float[] specular, float shininess, float transparency) {
        this.emission = emission;
        this.ambient = ambient;
        this.specular = specular;

        this.shininess = shininess;
        this.transparency = transparency;

        diffuse = 1.0f;
    }

    public float[] getEmission() {
        return emission;
    }

    public float[] getAmbient() {
        return ambient;
    }

    public float[] getSpecular() {
        return specular;
    }

    public float getShininess() {
        return shininess;
    }

    public float getTransparency() {
        return transparency;
    }

    public float getDiffuse() {
        return diffuse;
    }
}
