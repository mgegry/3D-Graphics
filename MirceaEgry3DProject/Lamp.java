/**
 * This class is responsible for drawing the lamp and holding its information.
 * University year: 2021-2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */
import gmaths.*;
import com.jogamp.opengl.*;

public class Lamp {

    private final Vec3 DEFAULT_LAMP_STAND_DIMENSION = new Vec3(0.8f, 0.3f, 0.8f);
    private final Vec3 DEAFULT_LAMP_ARM_DIMENSION = new Vec3(0.1f, 5f, 0.1f);
    private final Vec3 DEAFULT_LAMP_TOP_DIMENSION = new Vec3(0.5f, 0.1f, 0.1f);
    private final Vec3 DEAFULT_LAMP_HEAD_DIMENSION = new Vec3(0.3f, 0.4f, 0.3f);
    private final Vec3 DEAFULT_LAMP_BULB_DIMENSION = new Vec3(0.3f, 0.3f, 0.3f);

    private final Vec3 DEAFULT_LAMP_POSITION = new Vec3(7f, 0f, 5f);

    private Vec3 lampStandDimension;
    private Vec3 lampArmDimension;
    private Vec3 lampTopDimension;
    private Vec3 lampHeadDimension;
    private Vec3 lampBulbDimension;

    private Vec3 lampPosition;

    private Model cube, sphere, stand;

    private SGNode lampRoot;
    private TransformNode lampMoveTranslate;

	/**
	 * Constructor for the lamp class
	 * @param gl OpenGl opejct
	 * @param cube model for a cube
	 * @param sphere model for a sphere
	 */
    public Lamp(GL3 gl, Model cube, Model sphere, Model stand) {
        this.cube = cube;
        this.sphere = sphere;
		this.stand = stand;

        lampStandDimension = new Vec3(DEFAULT_LAMP_STAND_DIMENSION);
        lampArmDimension = new Vec3(DEAFULT_LAMP_ARM_DIMENSION);
        lampTopDimension = new Vec3(DEAFULT_LAMP_TOP_DIMENSION);
        lampHeadDimension = new Vec3(DEAFULT_LAMP_HEAD_DIMENSION);
        lampBulbDimension = new Vec3(DEAFULT_LAMP_BULB_DIMENSION);

        lampPosition = new Vec3(DEAFULT_LAMP_POSITION);

        initialise(gl);
    }

	/**
	 * This method initialises all the lamp properties(dimension, position, rotation).
	 * @param gl OpenGL object
	 */
    private void initialise(GL3 gl) {
        lampRoot = new NameNode("lamp_root");
		lampMoveTranslate = new TransformNode("lamp transform", Mat4Transform.translate(new Vec3(lampPosition)));

		TransformNode lampTranslate = new TransformNode("lamp transform", Mat4Transform.translate(0, 0, 0));

		NameNode lampStand = new NameNode("lamp_stand");
		Mat4 mat = Mat4Transform.scale(lampStandDimension.x, lampStandDimension.y, lampStandDimension.z);
		mat = Mat4.multiply(mat, Mat4Transform.translate(0, 0.5f, 0));
		TransformNode standTransform = new TransformNode("lamp_stand_transform", mat);
		ModelNode standShape = new ModelNode("Cube(stand)", stand);

		NameNode arm = new NameNode("lamp_arm"); 
		mat = new Mat4(1);
		mat = Mat4.multiply(mat, Mat4Transform.translate(0, lampArmDimension.y / 2f, 0));
		mat = Mat4.multiply(mat, Mat4Transform.scale(lampArmDimension.x, lampArmDimension.y, lampArmDimension.z));
		mat = Mat4.multiply(mat, Mat4Transform.translate(0, 0, 0));
		TransformNode armTransform = new TransformNode("lamp_arm transform", mat);
		ModelNode armShape = new ModelNode("Cube(arm)", cube);

		NameNode top = new NameNode("lamp_top");
		mat = new Mat4(1);
		mat = Mat4.multiply(mat, Mat4Transform.translate(-lampTopDimension.x / 2f + 0.05f, lampTopDimension.y / 2f + lampArmDimension.y, 0));
		mat = Mat4.multiply(mat, Mat4Transform.scale(lampTopDimension.x, lampTopDimension.y, lampTopDimension.z));
		TransformNode topTransform = new TransformNode("lamp_top transform", mat);
		ModelNode topShape = new ModelNode("Cube(top)", cube);

		NameNode head = new NameNode("lamp_head");
		mat = new Mat4(1);
		mat = Mat4.multiply(mat, Mat4Transform.translate(-lampTopDimension.x, lampArmDimension.y, 0f));
		mat = Mat4.multiply(mat, Mat4Transform.scale(lampHeadDimension.x, lampHeadDimension.y, lampHeadDimension.z));
		TransformNode headTransform = new TransformNode("lamp_head transform", mat);
		ModelNode headShape = new ModelNode("Cube(head)", cube);

		NameNode bulb = new NameNode("lamp_bulb");
		mat = new Mat4(1);
		mat = Mat4.multiply(mat, Mat4Transform.translate(-lampTopDimension.x, lampArmDimension.y - lampBulbDimension.y / 2f, 0f));
		mat = Mat4.multiply(mat, Mat4Transform.scale(lampBulbDimension.x, lampBulbDimension.y, lampBulbDimension.z));
		TransformNode bulbTransform = new TransformNode("lamp_bulb transform", mat);
		ModelNode bulbShape = new ModelNode("Sphere(bulb)", sphere);

		lampRoot.addChild(lampMoveTranslate);
		  lampMoveTranslate.addChild(lampTranslate);
		    lampTranslate.addChild(lampStand);
		      lampStand.addChild(standTransform);
		        standTransform.addChild(standShape);
			  lampStand.addChild(arm);
			  	arm.addChild(armTransform);
				armTransform.addChild(armShape);
			  lampStand.addChild(top);
			  	top.addChild(topTransform);
				topTransform.addChild(topShape);
			  lampStand.addChild(head);
					head.addChild(headTransform);
					headTransform.addChild(headShape);
			  lampStand.addChild(bulb);
			  		bulb.addChild(bulbTransform);
					bulbTransform.addChild(bulbShape);
		lampRoot.update();
    }

	/**
	 * This method draws the lamp
	 * @param gl OpenGl object
	 */
    public void draw(GL3 gl) {
        lampRoot.draw(gl);
    }

	/**
	 * This method sets the lamp stand dimension
	 * @param dim dimension of the lamp stand
	 */
    public void setLampStandDimensions(Vec3 dim) {
        lampStandDimension = new Vec3(dim);
    }

	/**
	 * This method sets the lamp arm dimension
	 * @param dim dimension of the lamp arm
	 */
    public void setLampArmDimensions(Vec3 dim) {
        lampArmDimension = new Vec3(dim);
    }

	/**
	 * This method sets the lamp top dimension
	 * @param dim dimension of the lamp top
	 */
    public void setLampTopDimensions(Vec3 dim) {
        lampTopDimension = new Vec3(dim);
    }

	/**
	 * This method sets the lamp head dimension
	 * @param dim dimension of the lamp head
	 */
    public void setLampHeadDimensions(Vec3 dim) {
        lampHeadDimension = new Vec3(dim);
    }
 
	/**
	 * This method sets teh lamp bulb dimension
	 * @param dim dimension of the lamp bulb
	 */
    public void setLampBulbDimnensions(Vec3 dim) {
        lampBulbDimension = new Vec3(dim);
    }
}
