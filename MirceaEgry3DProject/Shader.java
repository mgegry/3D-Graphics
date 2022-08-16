/**
* This class is a basic shader class that reads from file the vertex shader code
* and the fragment shader code generates the shader program to be used by other classes
* This class was created with the help of the online tutorials provided by the course. 
* University year: 2021 - 2022
* @author Mircea Gelu Egry
*/

import gmaths.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.glsl.*;

public class Shader {

	private static final boolean DISPLAY_SHADERS = false;

	private int ID;
	private String vertexShaderSource;
	private String fragmentShaderSource;

	/**
	 * This is the constructor of the class
	 * @param gl OpenGL object
	 * @param vertexPath the path to the vertex shader file
	 * @param fragmentPath the path to the fragment shader file
	 */
	public Shader(GL3 gl, String vertexPath, String fragmentPath) {
		try {
			vertexShaderSource = new String(Files.readAllBytes(Paths.get(vertexPath)), Charset.defaultCharset());
			fragmentShaderSource = new String(Files.readAllBytes(Paths.get(fragmentPath)), Charset.defaultCharset());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if (DISPLAY_SHADERS) {
			display();
		}
		ID = compileAndLink(gl);
	}

	/**
	 * Get the id of the shader program
	 * @return the id of the shader program
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Use a shader program
	 * @param gl OpenGl object
	 */
	public void use(GL3 gl) {
		gl.glUseProgram(ID);
	}

	/**
	 * Set int uniform in the shader
	 * @param gl OpenGL object
	 * @param name name of the uniform
	 * @param value value of the uniform
	 */
  	public void setInt(GL3 gl, String name, int value) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniform1i(location, value);
  	}

	/**
	 * Set float uniform int the shader
	 * @param gl
	 * @param name
	 * @param value
	 */
  	public void setFloat(GL3 gl, String name, float value) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniform1f(location, value);
  	}
  
	/**
	 * Set two float uniforms in the shader
	 * @param gl
	 * @param name
	 * @param f1
	 * @param f2
	 */
  	public void setFloat(GL3 gl, String name, float f1, float f2) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniform2f(location, f1, f2);
  	}
  
	/**
	 * Set three float uniforms in the shader
	 * @param gl
	 * @param name
	 * @param f1
	 * @param f2
	 * @param f3
	 */
  	public void setFloat(GL3 gl, String name, float f1, float f2, float f3) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniform3f(location, f1, f2, f3);
  	}
  
	/**
	 * Set four float uniforms in the shader
	 * @param gl
	 * @param name
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param f4
	 */
  	public void setFloat(GL3 gl, String name, float f1, float f2, float f3, float f4) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniform4f(location, f1, f2, f3, f4);
  	}
  
	/**
	 * Set array of floats uniform in the shader
	 * @param gl
	 * @param name
	 * @param f
	 */
  	public void setFloatArray(GL3 gl, String name, float[] f) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniformMatrix4fv(location, 1, false, f, 0);
  	}
  
	/**
	 * Set vec3 uniform in the shader.
	 * @param gl
	 * @param name
	 * @param v
	 */
  	public void setVec3(GL3 gl, String name, Vec3 v) {
    	int location = gl.glGetUniformLocation(ID, name);
    	gl.glUniform3f(location, v.x, v.y, v.z);
  	}	

	/**
	 * Display the vertex and fragment shader sources.
	 */
	private void display() {
		System.out.println("***Vertex shader***");
	    System.out.println(vertexShaderSource);
	    System.out.println("\n***Fragment shader***");
	    System.out.println(fragmentShaderSource);
	}

	/**
	 * This method compiles and links the shader program so it can be later used
	 * @param gl OpenGL object
	 * @return int representing the shader program id
	 */
	private int compileAndLink(GL3 gl) {
		String[][] sources = new String[1][1];
		sources[0] = new String[]{ vertexShaderSource };
		ShaderCode vertexShaderCode = new ShaderCode(GL3.GL_VERTEX_SHADER, sources.length, sources);
		boolean compiled = vertexShaderCode.compile(gl, System.err);
		if (!compiled) 
			System.err.println("[error]: Unable to compile vertex shader.");

		sources[0] = new String[]{ fragmentShaderSource };
		ShaderCode fragmentShaderCode = new ShaderCode(GL3.GL_FRAGMENT_SHADER, sources.length, sources);
		compiled = fragmentShaderCode.compile(gl, System.err);
		if (!compiled)
			System.err.println("[error]: Unable to compile fragment shader.");

		ShaderProgram program = new ShaderProgram();
		program.init(gl);
		program.add(vertexShaderCode);
		program.add(fragmentShaderCode);
		program.link(gl, System.out);

		if (!program.validateProgram(gl, System.out))
			System.err.println("[error]: Unable to link program");

		return program.program();
	}

}