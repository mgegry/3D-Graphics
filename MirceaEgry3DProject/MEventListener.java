/**
* This class is taking care of the main evnts that happen across the program.
* This class was created with the help of the online tutorials provided by the course.
* University year: 2021-2022
* @author Mircea Gelu egry
* @version 1.0
*/

import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class MEventListener implements GLEventListener {

	private static final boolean DISPLAY_SHADERS = false;
	private Camera camera;

	public MEventListener(Camera camera) {
		this.camera = camera;
		this.camera.setPosition(new Vec3(6f, 9f, 17f));
	}

	/**
	 * The initialise method of the OpenGL project
	 */
	public void init(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glFrontFace(GL.GL_CCW);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);
		initialise(gl);
		startTime = getSeconds();
	}

	/**
	 * Method that displays the scene
	 */
	public void display(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		render(gl);
	}

	/**
	 * Method that takes care of reshaping the window.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glViewport(x, y, width, height);
		float aspect = (float)width/(float)height;
		camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
	}

	/**
	 * Method that frees any resources not in need anymore.
	 */
	public void dispose(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		
		cube.dispose(gl);
		light.dispose(gl);
		light2.dispose(gl);
		nightLight.dispose(gl);
		dayLight.dispose(gl);

		wall.dispose(gl);
		wall2.dispose(gl);
		floor.dispose(gl);
		sphere.dispose(gl);
		egg.dispose(gl);
		stand.dispose(gl);
		outsideWall.dispose(gl);
		
	}


	// THE SCENE

	private Model cube;
	private Model wall;
	private Model wall2;
	private Model floor;
	private Model egg;
	private Model sphere;
	private Model outsideWall;
	private Model stand;
	private Model phone;

	private Light light;
	private Light light2;
	private Light nightLight;
	private Light dayLight;

	private Room room;
	private Robot robot;
	private Lamp lamp;

	private final float EGG_STAND_HEIGHT = 1f;
	private final float EGG_STAND_WIDTH = 3f;

	private final float EGG_HEIGHT = 3f;
	private final float EGG_WIDTH = 2f;

	private final float PHONE_STAND_HEIGHT = 1f;
	private final float PHONE_STAND_WIDTH = 3f;
	private final float PHONE_STAND_DEPTH = 1.5f;

	private final float PHONE_HEIGHT = 3f;
	private final float PHONE_WIDTH = 2f;
	private final float PHONE_DEPTH = 0.7f;

	private Mat4 eggStandMatrix;
	private Mat4 phoneStandMatrix;
	private Mat4 eggMatrix;
	private Mat4 phoneMatrix;

	/**
	 * This method initialises all object in the scene.
	 * @param gl OpenGL object
	 */
	public void initialise(GL3 gl) {

		int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/wood.jpeg");
		int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/wall_texture.jpeg");
		int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/egg_texture.jpg");
		int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/egg_specular2.jpeg");
		int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/view_texture.jpeg");

		light = new Light(gl);
		light2 = new Light(gl);
		nightLight = new Light(gl);
		dayLight = new Light(gl);

		nightLight.setMaterial(new Material(new Vec3(0.2f, 0.2f, 0.2f), new Vec3(0.2f, 0.2f, 0.2f), new Vec3(0.3f, 0.3f, 0.3f), 32.0f));
		nightLight.setCamera(camera);
		
		dayLight.setMaterial(new Material(new Vec3(0.7f, 0.7f, 0.7f), new Vec3(0.7f, 0.7f, 0.7f), new Vec3(0.7f, 0.7f, 0.7f), 32.0f));
		dayLight.setCamera(camera);

		light.setCamera(camera);
		light2.setCamera(camera);

		Mesh m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
		Shader shader = new Shader(gl, "shaders/vs_cube.txt", "shaders/fs_cube.txt");
		Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		cube = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m);

		m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
		shader = new Shader(gl, "shaders/vs_cube.txt", "shaders/fs_cube.txt");
		material = new Material(new Vec3(0.4f, 0f, 1.0f), new Vec3(0.4f, 0f, 1.0f), new Vec3(0.4f, 0.4f, 0.4f), 32.0f);
		stand = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m);

		m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
		shader = new Shader(gl, "shaders/vs_sphere.txt", "shaders/fs_sphere.txt");
		material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		egg = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m, textureId3, textureId4);

		m = new Mesh(gl, TwoTriangle.vertices.clone(), TwoTriangle.indices.clone());
		shader = new Shader(gl, "shaders/vs_tt.txt", "shaders/fs_wall.txt");
		material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		wall = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m, textureId2);

		m = new Mesh(gl, TwoTriangle2.vertices.clone(), TwoTriangle2.indices.clone());
		shader = new Shader(gl, "shaders/vs_tt.txt", "shaders/fs_tt.txt");
		material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		wall2 = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m, textureId2);

		m = new Mesh(gl, TwoTriangle.vertices.clone(), TwoTriangle.indices.clone());
		shader = new Shader(gl, "shaders/vs_tt.txt", "shaders/fs_tt.txt");
		material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		floor = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m, textureId0);

		m = new Mesh(gl, TwoTriangle2.vertices.clone(), TwoTriangle2.indices.clone());
		shader = new Shader(gl, "shaders/vs_tt.txt", "shaders/fs_tt.txt");
		material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		outsideWall = new Model(gl, camera, dayLight, shader, material, new Mat4(1), m, textureId5);

		m = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
		shader = new Shader(gl, "shaders/vs_sphere.txt", "shaders/fs_sphere.txt");
		material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
		sphere = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m);

		m = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
		shader = new Shader(gl, "shaders/vs_cube.txt", "shaders/fs_cube.txt");
		material = new Material(new Vec3(0.5f, 0.8f, 0.9f), new Vec3(0.5f, 0.5f, 0.9f), new Vec3(0.4f, 0.4f, 0.4f), 32.0f);
		phone = new Model(gl, camera, light, light2, shader, material, new Mat4(1), m);

		eggStandMatrix = getMforEggStand();
		phoneStandMatrix = getMforPhoneStand();
		eggMatrix = getMforEgg();
		phoneMatrix = getMforPhone();

		room = new Room(gl, wall, wall2, floor, outsideWall);
		robot = new Robot(gl, cube, sphere);
		lamp = new Lamp(gl, cube, sphere, stand);

	}

	/**
	 * This method renders all the object in the scence.
	 * @param gl OpenGL object
	 */
	public void render(GL3 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		light.setPosition(getFirstLightPosition());
		light2.setPosition(getSecondLightPosition());
		light.render(gl);
		light2.render(gl);

		// RENDER THE ROOM
		room.render(gl);

		// RENDER EGG AND EGG STAND
		stand.setModelMatrix(eggStandMatrix);
		stand.render(gl);

		egg.setModelMatrix(eggMatrix);
		egg.render(gl);

		// RENDER PHONE AND PHONE STAND
		stand.setModelMatrix(phoneStandMatrix);
		stand.render(gl);

		phone.setModelMatrix(phoneMatrix);
		phone.render(gl);

		// RENDER THE LAMP
		lamp.draw(gl);

		robot.draw(gl);
	}

	/**
	 * Get the model matrix for the egg stand
	 * @return the model matrix
	 */
	private Mat4 getMforEggStand() {
		Mat4 modelMatrix = new Mat4(1);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(0f, EGG_STAND_HEIGHT / 2f, 0f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.scale(EGG_STAND_WIDTH, EGG_STAND_HEIGHT, EGG_STAND_WIDTH), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the egg
	 * @return the model matrix
	 */
	private Mat4 getMforEgg() {
		Mat4 modelMatrix = new Mat4(1);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(0f, 0.8f, 0f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.scale(EGG_WIDTH, EGG_HEIGHT, EGG_WIDTH), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the phone stand
	 * @return the model matrix
	 */
	private Mat4 getMforPhoneStand() {
		Mat4 modelMatrix = new Mat4(1);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(2f, PHONE_STAND_HEIGHT / 2f, -4f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.scale(PHONE_STAND_WIDTH, PHONE_STAND_HEIGHT, PHONE_STAND_DEPTH), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the phone
	 * @return the model matrix
	 */
	private Mat4 getMforPhone() {
		Mat4 modelMatrix = new Mat4(1);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(3f, 0.8f, -8.5f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.scale(PHONE_WIDTH, PHONE_HEIGHT, PHONE_DEPTH), modelMatrix);
		return modelMatrix;
	}

	/**
	 * This method returns the first light position
	 * @return vector containing the light position
	 */
	private Vec3 getFirstLightPosition() {
		return new Vec3(5f, 10f, 0f);
	}

	/**
	 * This method returns the second light position
	 * @return vector containing the second light position
	 */
	private Vec3 getSecondLightPosition() {
		return new Vec3(-5f, 10f, 0f);
	}

	// INTERACTION

	private boolean lightSwitch = true;
	private boolean isDay = true;

	/**
	 * This method switches the light on and off
	 */
	public void switchLights() {

		if (!lightSwitch) {
			light.setMaterial(new Material( new Vec3(0f, 0f, 0f), new Vec3(0f, 0f, 0f), new Vec3(0f, 0f, 0f), 32.0f));
			light2.setMaterial(new Material( new Vec3(0f, 0f, 0f), new Vec3(0f, 0f, 0f), new Vec3(0f, 0f, 0f), 32.0f));
			lightSwitch = true;
		} else {
			light.setMaterial(new Material( new Vec3(0.3f, 0.3f, 0.3f), new Vec3(0.3f, 0.3f, 0.3f), new Vec3(1f, 1f, 1f), 32.0f));
			light2.setMaterial(new Material( new Vec3(0.3f, 0.3f, 0.3f), new Vec3(0.3f, 0.3f, 0.3f), new Vec3(1f, 1f, 1f), 32.0f));
			lightSwitch = false;
		}
	}

	/**
	 * This method moves the robot to the first pose
	 */
	public void moveRobotPose1() {
		robot.robotMoveTranslate.setTransform(Mat4Transform.translate(new Vec3(0f, 0f, -4f)));
		robot.robotMoveTranslate.update();
	}

	/**
	 * This method moves the robot to the second pose
	 */
	public void moveRobotPose2() {
		robot.robotMoveTranslate.setTransform(Mat4Transform.translate(new Vec3(5f, 0f, -3f)));
		robot.robotMoveTranslate.update();
	}

	/**
	 * This method moves the robot to the third pose
	 */
	public void moveRobotPose3() {
		robot.robotMoveTranslate.setTransform(Mat4Transform.translate(new Vec3(4f, 0f, 4f)));
		robot.robotMoveTranslate.update();
	}

	/**
	 * This method moves the robot to the fourth pose
	 */
	public void moveRobotPose4() {
		robot.robotMoveTranslate.setTransform(Mat4Transform.translate(new Vec3(0f, 0f, 5f)));
		robot.robotMoveTranslate.update();
	}

	/**
	 * This method moves the robot to the fifth pose
	 */
	public void moveRobotPose5() {
		robot.robotMoveTranslate.setTransform(Mat4Transform.translate(new Vec3(-4f, 0f, 0f)));
		robot.robotMoveTranslate.update();
	}

	/**
	 * This method sets the time of the day either day or night for the outside scenary
	 */
	public void setDayNight() {
		if (isDay) {
			isDay = false;
			outsideWall.setLight(dayLight);;

		} else {
			isDay = true;
			outsideWall.setLight(nightLight);
		}
	}
	
	// TIME

	private double startTime;

	/**
	 * This method returns the number of seconds at the current state of the program
	 * @return a double containing the current time in seconds
	 */
	private double getSeconds() {
		return System.currentTimeMillis() / 1000.0;
	}

}