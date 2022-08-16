/**
* This class configures the camera and also implements the movement functionality
* This class was crated with the help of online tutorials provided by the course.
* University year: 2021 - 2022
* @author Mircea Gelu Egry
* @version 1.0
*/

import gmaths.*;
import java.awt.event.*;
import com.jogamp.opengl.awt.GLCanvas;

public class Camera {

	public enum CameraType {X, Z};
	public enum Movement {NO_MOVEMENT, LEFT, RIGHT, UP, DOWN, FORWARD, BACK};

	private static final float DEFAULT_RADIUS = 25;
	public static final Vec3 DEFAULT_POSITION = new Vec3(0, 0, 25);
	public static final Vec3 DEFAULT_POSITION_2 = new Vec3(25, 0, 0);
	public static final Vec3 DEFAULT_TARGET = new Vec3(0, 0, 0);
	public static final Vec3 DEFAULT_UP = new Vec3(0, 1, 0);

	public final float YAW = -90f;
	public final float PITCH = 0f;
	public final float KEYBOARD_SPEED = 0.2f;
	public final float MOUSE_SPEED = 1.0f;

	private Vec3 position;
	private Vec3 target;
	private Vec3 up;
	private Vec3 worldUp;
	private Vec3 front;
	private Vec3 right;

	private float yaw;
	private float pitch;
	
	private Mat4 perspective;


	/**
	 * Constructor for the Camera class
	 * @param position the position of the camera
	 * @param target the target the camera is looking to
	 * @param up the up position
	 */
	public Camera(Vec3 position, Vec3 target, Vec3 up) {
		setupCamera(position, target, up);
	}

	/**
	 * This metho sets the 
	 * @param position
	 * @param target
	 * @param up
	 */
	private void setupCamera(Vec3 position, Vec3 target, Vec3 up) {
		this.position = new Vec3(position);
		this.target = new Vec3(target);
		this.up = new Vec3(up);

		front = Vec3.subtract(target, position);
		front.normalize();
		up.normalize();
		calculateYawPitch(front);
		worldUp = new Vec3(up);
		updateCameraVectors();
	}

	/**
	 * This method returns the position of the camera
	 * @return a position vector
	 */
	public Vec3 getPosition() {
		return new Vec3(position);
	}

	/**
	 * This method sets the position
	 * @param pos the position vector
	 */
	public void setPosition(Vec3 pos) {
		setupCamera(pos, target, up);
	}

	/**
	 * This methods sets the target
	 * @param tar target vector
	 */
	public void setTarget(Vec3 tar) {
		setupCamera(position, tar, up);
	}

	/**
	 * This method sets the camera
	 * @param c camera type x or y
	 */
	public void setCamera(CameraType c) {
		switch (c) {
			case X : setupCamera(DEFAULT_POSITION, DEFAULT_TARGET, DEFAULT_UP); break;
			case Z : setupCamera(DEFAULT_POSITION_2, DEFAULT_TARGET, DEFAULT_UP); break;
		}
	}

	/**
	 * This function calculates the yaw and pitch
	 * @param v
	 */
	private void calculateYawPitch(Vec3 v) {
		yaw = (float)Math.atan2(v.z, v.x);
		pitch = (float)Math.asin(v.y);
	}

	/**
	 * This function returns the view matrix
	 * @return the view matrix
	 */
	public Mat4 getViewMatrix() {
		target = Vec3.add(position, front);
		return Mat4Transform.lookAt(position, target, up);
	}

	/**
	 * This function sets the perspective matrix
	 * @param m the input matrix
	 */
	public void setPerspectiveMatrix(Mat4 m) {
		perspective = m;
	}

	/**
	 * This fucntion return the perspective matrix
	 * @return Mat4 containing the perspective matrix
	 */
	public Mat4 getPerspectiveMatrix() {
		return perspective;
	}

	/**
	 * This function moves the camera in the required direction
	 * @param movement the movement direction
	 */
	public void keyboardInput(Movement movement) {
		switch (movement) {
			case NO_MOVEMENT: break;
			case LEFT: position.add(Vec3.multiply(right, -KEYBOARD_SPEED)); break;
			case RIGHT: position.add(Vec3.multiply(right, KEYBOARD_SPEED)); break;
			case UP: position.add(Vec3.multiply(up, KEYBOARD_SPEED)); break;
			case DOWN: position.add(Vec3.multiply(up, -KEYBOARD_SPEED)); break;
			case FORWARD: position.add(Vec3.multiply(front, KEYBOARD_SPEED)); break;
			case BACK: position.add(Vec3.multiply(front, -KEYBOARD_SPEED)); break;
		}
	}

	/**
	 * This function update the yaw and the pitch
	 * @param y the yaw
	 * @param p the pitch
	 */
	public void updateYawPitch(float y, float p) {
		yaw += y;
		pitch += p;

		if (pitch > 89) pitch = 89;
		else if (pitch < -89) pitch = -89;

		updateFront();
		updateCameraVectors();
	}

	/**
	 * This method updates the front.
	 */
	private void updateFront() {
		double cy, cp, sy, sp;
		cy = Math.cos(yaw);
		cp = Math.cos(pitch);
		sy = Math.sin(yaw);
		sp = Math.sin(pitch);

		front.x = (float)(cy * cp);
		front.y = (float)(sp);
		front.z = (float)(sy * cp);

		front.normalize();
		target = Vec3.add(position, front);
	}

	/**
	 * This method updates the camera vectors.
	 */
	private void updateCameraVectors() {
		right = Vec3.crossProduct(front, worldUp);
		right.normalize();
		up = Vec3.crossProduct(right, front);
		up.normalize();
	}
}