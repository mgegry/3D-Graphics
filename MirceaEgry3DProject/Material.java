/**
 * This class stores the properties of a material
 * This class was created with the help of the materials provided in the tutorials
 * University year: 2021 - 2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */

import gmaths.*;

public class Material {

    public static final Vec3 DEFAULT_AMBIENT = new Vec3(0.2f, 0.2f, 0.2f);
    public static final Vec3 DEFAULT_DIFFUSE = new Vec3(0.8f, 0.8f, 0.8f);
    public static final Vec3 DEFAULT_SPECULAR = new Vec3(0.5f, 0.5f, 0.5f);
    public static final Vec3 DEFAULT_EMISSION = new Vec3(0.0f, 0.0f, 0.0f);
    public static final float DEFAULT_SHININESS = 32;

    private Vec3 ambient;
    private Vec3 diffuse;
    private Vec3 specular;
    private Vec3 emission;
    private float shininess;

    /**
     * Default constructor that initialises with the default values
     */
    public Material() {
        this.ambient = new Vec3(DEFAULT_AMBIENT);
        this.diffuse = new Vec3(DEFAULT_DIFFUSE);
        this.specular = new Vec3(DEFAULT_SPECULAR);
        this.emission = new Vec3(DEFAULT_EMISSION);
        this.shininess = DEFAULT_SHININESS;
    }

    /**
     * Construcotr that initialises with provided values
     * @param ambient the ambient rgb colors
     * @param diffuse the diffuse rgb colors
     * @param specular the specular rgb colors
     * @param shininess the amount of shininess
     */
    public Material(Vec3 ambient, Vec3 diffuse, Vec3 specular, float shininess) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        emission = new Vec3(DEFAULT_EMISSION);
        this.shininess = shininess;
    }

    /**
     * Set the ambient color with given red, blue, green values
     * @param red
     * @param green
     * @param blue
     */
    public void setAmbient(float red, float green, float blue) {
        ambient.x = red;
        ambient.y = green;
        ambient.z = blue;
    }

    /**
     * Set ambient color with given Vec3 rgb value
     * @param rgb
     */
    public void setAmbient(Vec3 rgb) {
        setAmbient(rgb.x, rgb.y, rgb.z);
    }
    
    /**
     * Get the ambient value
     * @return the ambient Vec3 rgb colors
     */
    public Vec3 getAmbient() {
        return new Vec3(ambient);
    }

    /**
     * Set the diffuse color with given red, green, blue values
     * @param red
     * @param green
     * @param blue
     */
    public void setDiffuse(float red, float green, float blue) {
        diffuse.x = red;
        diffuse.y = green;
        diffuse.z = blue;
    }

    /**
     * Set diffuse with given Vec3 rgb value
     * @param rgb
     */
    public void setDiffuse(Vec3 rgb) {
        setDiffuse(rgb.x, rgb.y, rgb.z);
    }

    /**
     * Get the diffuse value
     * @return the diffuse Vec3 rgb colors
     */
    public Vec3 getDiffuse() {
        return new Vec3(diffuse);
    }

    /**
     * Set specular color with given red, green, blue values
     * @param red
     * @param green
     * @param blue
     */
    public void setSpecular(float red, float green, float blue) {
        specular.x = red;
        specular.y = green;
        specular.z = blue;
    }

    /**
     * Set specular color with given Vec3 rgb value
     * @param rgb
     */
    public void setSpecular(Vec3 rgb) {
        setSpecular(rgb.x, rgb.y, rgb.z);
    }

    /**
     * Get specular color
     * @return the specular Vec3 rgb colors
     */
    public Vec3 getSpecular() {
        return new Vec3(specular);
    }

    /**
     * Set emission color with given red, green, blue values
     * @param red
     * @param green
     * @param blue
     */
    public void setEmission(float red, float green, float blue) {
        emission.x = red;
        emission.y = green;
        emission.z = blue;
    }

    /**
     * Set emission color with given Vec3 rgb value
     * @param rgb
     */
    public void setEmission(Vec3 rgb) {
        setEmission(rgb.x, rgb.y, rgb.z);
    }

    /**
     * Gen the emission color
     * @return the emission Vec3 rgb colors
     */
    public Vec3 getEmission() {
        return new Vec3(emission);
    }

    /**
     * Set the amount of shininess
     * @param shininess the amount of shininess
     */
    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    /**
     * Get the amount of shininess
     * @return float descibing the amount of shininess
     */
    public float getShininess() {
        return shininess;
    }

    /**
     * To string function for printing the class
     */
    public String toString() {
        return "a:"+ambient+", d:"+diffuse+", s:"+specular+", e:"+emission+", shininess:"+shininess;
    }
}
