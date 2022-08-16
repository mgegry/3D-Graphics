/**
* This class handles a model type and all its properties.
* This class was created with the help of online tutorials provided by the course.
* University year: 2021-2022
* @author Mircea Gelu Egry
* @version 1.0
*/

import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;

public class Model {
    
    private Mesh mesh;
    private Material material;
    private Shader shader;
    private Camera camera;
    private Mat4 modelMatrix;
    private Light light;
    private Light light2;
    private int[] textureId1;
    private int[] textureId2;

    /**
     * This is the class constructor
     * @param gl OpengGL object
     * @param camera the camera object
     * @param light the light object
     * @param shader the shader object
     * @param material the material object
     * @param modelMatrix the model matrix
     * @param mesh the mesh object
     */
    public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh) {
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.material = material;
        this.modelMatrix = modelMatrix;
        this.mesh = mesh;
    }

    /**
     * This is the class constructor
     * @param gl OpengGL object
     * @param camera the camera object
     * @param light the light object
     * @param light2 the second light object
     * @param shader the shader object
     * @param material the material object
     * @param modelMatrix the model matrix
     * @param mesh the mesh object
     */
    public Model(GL3 gl, Camera camera, Light light, Light light2, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh) {
        this.camera = camera;
        this.shader = shader;
        this.light = light;
        this.light2 = light2;
        this.modelMatrix = modelMatrix;
        this.material = material;
        this.mesh = mesh;
    }

    /**
     * This is the class constructor
     * @param gl OpengGL object
     * @param camera the camera object
     * @param light the light object
     * @param light2 the second light object
     * @param shader the shader object
     * @param material the material object
     * @param modelMatrix the model matrix
     * @param mesh the mesh object
     * @param textureId1 the first texture id
     */
    public Model(GL3 gl, Camera camera, Light light, Light light2, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1) {
        this.camera = camera;
        this.shader = shader;
        this.light = light;
        this.light2 = light2;
        this.modelMatrix = modelMatrix;
        this.material = material;
        this.mesh = mesh;
        this.textureId1 = textureId1;
    }

    /**
     * This is the class constructor
     * @param gl OpengGL object
     * @param camera the camera object
     * @param light the light object
     * @param light2 the second light object
     * @param shader the shader object
     * @param material the material object
     * @param modelMatrix the model matrix
     * @param mesh the mesh object
     * @param textureId1 the first texture id
     * @param textureId2 the second texture id
     */
    public Model(GL3 gl, Camera camera, Light light, Light light2, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1, int[] textureId2) {
        this.camera = camera;
        this.shader = shader;
        this.light = light;
        this.light2 = light2;
        this.modelMatrix = modelMatrix;
        this.material = material;
        this.mesh = mesh;
        this.textureId1 = textureId1;
        this.textureId2 = textureId2;
    }

    /**
     * This is the class constructor
     * @param gl OpengGL object
     * @param camera the camera object
     * @param light the light object
     * @param shader the shader object
     * @param material the material object
     * @param modelMatrix the model matrix
     * @param mesh the mesh object
     * @param textureId1 the texture id
     */
    public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1) {
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.material = material;
        this.modelMatrix = modelMatrix;
        this.mesh = mesh;
        this.textureId1 = textureId1;
    }

    /**
     * This is the class constructor
     * @param gl OpengGL object
     * @param camera the camera object
     * @param light the light object
     * @param shader the shader object
     * @param material the material object
     * @param modelMatrix the model matrix
     * @param mesh the mesh object
     * @param textureId1 the first texture id
     * @param textureId2 the second texture id
     */
    public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1, int[] textureId2) {
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.material = material;
        this.modelMatrix = modelMatrix;
        this.mesh = mesh;
        this.textureId1 = textureId1;
        this.textureId2 = textureId2;
    }

    /**
     * This method is a setter for the model matrix.
     * @param m the model matrix.
     */
    public void setModelMatrix(Mat4 m) {
        modelMatrix = m;
    }

    /**
     * This method is a setter for the camera.
     * @param c the camera object.
     */
    public void setCamera(Camera c) {
        camera = c;
    }

    /**
     * This method is a setter for the light.
     * @param l a light object
     */
    public void setLight(Light l) {
        light = l;
    }

    /**
     * This method renders the object with the given textures and sets the shaders uniforms.
     * @param gl OpenGL object
     * @param modelMatrix the object model matrix
     */
    public void render(GL3 gl, Mat4 modelMatrix) {
        Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));
        
        shader.use(gl);
        shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
        shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

        shader.setVec3(gl, "viewPos", camera.getPosition());

        if (light != null) {
            shader.setVec3(gl, "light.position", light.getPosition());
            shader.setVec3(gl, "light.ambient", light.getMaterial().getAmbient());
            shader.setVec3(gl, "light.diffuse", light.getMaterial().getDiffuse());
            shader.setVec3(gl, "light.specular", light.getMaterial().getSpecular());
        }

        if (light2 != null) {
            shader.setVec3(gl, "light2.position", light2.getPosition());
            shader.setVec3(gl, "light2.ambient", light2.getMaterial().getAmbient());
            shader.setVec3(gl, "light2.diffuse", light2.getMaterial().getDiffuse());
            shader.setVec3(gl, "light2.specular", light2.getMaterial().getSpecular());
        }

        shader.setVec3(gl, "material.ambient", material.getAmbient());
        shader.setVec3(gl, "material.diffuse", material.getDiffuse());
        shader.setVec3(gl, "material.specular", material.getSpecular());
        shader.setFloat(gl, "material.shininess", material.getShininess());

        if (textureId1 != null) {
            shader.setInt(gl, "first_texture", 0);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
            gl.glActiveTexture(GL.GL_TEXTURE0);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
        }

        if (textureId2 != null) {
            shader.setInt(gl, "second_texture", 1);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
            gl.glActiveTexture(GL.GL_TEXTURE1);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
        }

        mesh.render(gl);
    }

    /**
     * This method renders the object
     * @param gl OpenGL object
     */
    public void render(GL3 gl) {
        render(gl, modelMatrix);
    }

    /**
     * This method takes care of disposing any textures that are not used anymore
     * @param gl OpenGL object
     */
    public void dispose(GL3 gl) {
        mesh.dispose(gl);
        if (textureId1 != null) gl.glDeleteBuffers(1, textureId1, 0);
        if (textureId2 != null) gl.glDeleteBuffers(1, textureId2, 0);
    }
}