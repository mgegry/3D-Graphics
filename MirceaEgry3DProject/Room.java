/**
 * This class manages the room and holds all information about it.
 * University year: 2021-2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */

import gmaths.*;
import com.jogamp.opengl.*;

public class Room {

    private final float FLOOR_SIZE = 16f;
	private final float WALL_HEIGHT = 10f;
	private final float WALL_WIDTH = 16f;
    
    private Model wall1;
    private Model wall2;
    private Model floor;
	private Model outsideWall;

    private Mat4 floorMatrix;
	private Mat4 backWallMatrix;
	private Mat4 rbwMatrix;
	private Mat4 mbwMatrix;
	private Mat4 lbwMatrix;
	private Mat4 rmwMatrix;
	private Mat4 lmwMatrix;
	private Mat4 rtwMatrix;
	private Mat4 mtwMatrix;
	private Mat4 ltwMatrix;

	/**
	 * This the the room constructor
	 * @param gl OpenGL object
	 * @param wall1 a wall model type
	 * @param wall2 another wall model type
	 * @param floor the floor model
	 * @param outsideWall the ouside wall model
	 */
    public Room(GL3 gl, Model wall1, Model wall2, Model floor, Model outsideWall) {
        this.wall1 = wall1;
        this.wall2 = wall2;
        this.floor = floor;
		this.outsideWall = outsideWall;
        initialise(gl);
    }

	/**
	 * This method initialises the matrices for the objects
	 * @param gl OpenGL object
	 */
    public void initialise(GL3 gl) {
        floorMatrix = getMforFloor();
		backWallMatrix = getMforBackWall();
		rbwMatrix = getMforRBW();
		mbwMatrix = getMforMBW();
		lbwMatrix = getMforLBW();
		rmwMatrix = getMforRMW();
		lmwMatrix = getMforLMW();
		rtwMatrix = getMforRTW();
		mtwMatrix = getMforMTW();
		ltwMatrix = getMforLTW();
    }

	/**
	 * This method renders the objects.
	 * @param gl OpenGL object
	 */
    public void render(GL3 gl) {
        // RENDER FLOOR
		floor.setModelMatrix(floorMatrix);
		floor.render(gl);

		// REDNER WALLS
		wall1.setModelMatrix(backWallMatrix);
		wall1.render(gl);

		wall2.setModelMatrix(rbwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(lbwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(mbwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(rmwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(lmwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(ltwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(mtwMatrix);
		wall2.render(gl);

		wall2.setModelMatrix(rtwMatrix);
		wall2.render(gl);

		outsideWall.setModelMatrix(getMforOutsideWall());
		outsideWall.render(gl);
    }

	/**
	 * Get the model matrix for the oustide wall.
	 * @return the model matrix
	 */
	private Mat4 getMforOutsideWall() {
		Mat4 modelMatrix = new Mat4(1);

		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH, 1f, WALL_HEIGHT), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-WALL_WIDTH * 0.6f, WALL_HEIGHT * 0.5f, 0f), modelMatrix);
		return modelMatrix;
	}

    /**
	 * Get the model matrix for the backk wall
	 * @return the model matrix
	 */
	private Mat4 getMforBackWall() {
		Mat4 modelMatrix = new Mat4(1);

		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH, 1f, WALL_HEIGHT), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(0, WALL_HEIGHT * 0.5f, -WALL_WIDTH * 0.5f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the floor
	 * @return the model matrix
	 */
	private Mat4 getMforFloor() {
		Mat4 modelMatrix = new Mat4(1);

		modelMatrix = Mat4.multiply(Mat4Transform.scale(FLOOR_SIZE, 1f, FLOOR_SIZE), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the right bottom left wall
	 * @return the model matrix
	 */
	private Mat4 getMforRBW() {
		Mat4 modelMatrix = new Mat4(1);
		
		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f, WALL_WIDTH / 3f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the middle bottom left wall
	 * @return the model matrix
	 */
	private Mat4 getMforMBW() {
		Mat4 modelMatrix = new Mat4(1);
		
		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f, 0f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the left bottom left wall
	 * @return the model matrix
	 */
	private Mat4 getMforLBW() {
		Mat4 modelMatrix = new Mat4(1);

		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f, -WALL_WIDTH / 3f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get the model matrix for the right middle left wall
	 * @return the model matrix
	 */
	private Mat4 getMforRMW() {
		Mat4 modelMatrix = new Mat4(1);
		
		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f + WALL_HEIGHT / 3f, WALL_WIDTH / 3f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get model matrix for the left middle left wall
	 * @return the model matrix
	 */
	private Mat4 getMforLMW() {
		Mat4 modelMatrix = new Mat4(1);
		
		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f + WALL_HEIGHT / 3f, -WALL_WIDTH / 3f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get model matrix for the left top left wall
	 * @return the model matrix
	 */
	private Mat4 getMforLTW() {
		Mat4 modelMatrix = new Mat4(1);

		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f + (WALL_HEIGHT / 3f * 2f), -WALL_WIDTH / 3f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get model matrix for right top left wall
	 * @return the model matrix
	 */
	private Mat4 getMforRTW() {
		Mat4 modelMatrix = new Mat4(1);
		
		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f + (WALL_HEIGHT / 3f * 2), WALL_WIDTH / 3f), modelMatrix);
		return modelMatrix;
	}

	/**
	 * Get model matrix for middle top left wall
	 * @return the model matrix
	 */
	private Mat4 getMforMTW() {
		Mat4 modelMatrix = new Mat4(1);
		
		modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH / 3f, 1f, WALL_HEIGHT / 3f), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
		modelMatrix = Mat4.multiply(Mat4Transform.translate(-16 * 0.5f, WALL_HEIGHT / 6f + (WALL_HEIGHT / 3f * 2), 0f), modelMatrix);
		return modelMatrix;
	}
}
