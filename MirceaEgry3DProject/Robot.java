/**
 * This class manages the robot and holds all information about it.
 * University year: 2021-2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */

import com.jogamp.opengl.*;

import gmaths.*;

public class Robot {

    private final Vec3 DEFAULT_POSITION = new Vec3(0f, 0f, -3f);
    private final Vec3 DEFAULT_BODY_DIMENSION = new Vec3(1f, 2f, 1f);
    private final Vec3 DEFAULT_FOOT_DIMENSION = new Vec3(0.5f, 0.5f, 0.5f);
    private final Vec3 DEFAULT_NECK_DIMENSION = new Vec3(0.5f, 0.5f, 0.5f);
    private final Vec3 DEFAULT_HEAD_DIMENSION = new Vec3(1.5f, 0.5f, 0.5f);
    private final Vec3 DEFAULT_HEAD_PART_ONE_DIMENSION = new Vec3(0.3f, 0.3f, 0.3f);
    private final Vec3 DEFAULT_HEAD_PART_TWO_DIMENSION = new Vec3(0.3f, 0.3f, 0.3f);
    private final Vec3 DEFAULT_HEAD_PART_THREE_DIMENSION = new Vec3(0.3f, 0.3f, 0.3f);

    private Vec3 footDimensions;
    private Vec3 bodyDimensions;
    private Vec3 neckDimensions;
    private Vec3 headDimensions;
    private Vec3 headPart1Dimensions;
    private Vec3 headPart2Dimensions;
    private Vec3 headPart3Dimensions;

    public SGNode robotRoot;
    private Vec3 robotPosition;
    public TransformNode robotMoveTranslate, neckTranslate, headTranslate;

    public TransformNode robotTranslate;

    private Model cube, sphere;

    /**
     * The class constructor for the robot
     * @param gl the OpenGL object
     * @param cube model of a cube
     * @param sphere model of a sphere
     */
    public Robot(GL3 gl, Model cube, Model sphere) {
        this.cube = cube;
        this.sphere = sphere;
        this.cube = cube;
        robotPosition = new Vec3(DEFAULT_POSITION);
        bodyDimensions = new Vec3(DEFAULT_BODY_DIMENSION);
        footDimensions = new Vec3(DEFAULT_FOOT_DIMENSION);
        neckDimensions = new Vec3(DEFAULT_NECK_DIMENSION);
        headDimensions = new Vec3(DEFAULT_HEAD_DIMENSION);
        headPart1Dimensions = new Vec3(DEFAULT_HEAD_PART_ONE_DIMENSION);
        headPart2Dimensions = new Vec3(DEFAULT_HEAD_PART_TWO_DIMENSION);
        headPart3Dimensions = new Vec3(DEFAULT_HEAD_PART_THREE_DIMENSION);

        initialise(gl);
    }

    /**
     * This method initialises all the requiered robot data.
     * @param gl the OpenGL object
     */
    private void initialise(GL3 gl) {

        robotRoot = new NameNode("robot_root");
        robotMoveTranslate = new TransformNode("robot_transform", Mat4Transform.translate(new Vec3(robotPosition)));

        robotTranslate = new TransformNode("robot_transform", Mat4Transform.translate(0f, footDimensions.y, 0f));

        NameNode body = new NameNode("robot_body");
        Mat4 mat = Mat4Transform.scale(new Vec3(bodyDimensions));
        mat = Mat4.multiply(mat, Mat4Transform.translate(0f, 0.5f, 0f));
        TransformNode bodyTransform = new TransformNode("robot_body_transform", mat);
        ModelNode bodyShape = new ModelNode("Sphere(body)", sphere);

        NameNode foot = new NameNode("robot_foot");
        mat = Mat4Transform.scale(new Vec3(footDimensions));
        mat = Mat4.multiply(mat, Mat4Transform.translate(0f, -(footDimensions.y), 0f));
        TransformNode footTransform = new TransformNode("robot_foot_tranform", mat);
        ModelNode footShape = new ModelNode("Sphere(foot)", sphere);

        NameNode neck = new NameNode("robot_neck");
        mat = Mat4Transform.scale(new Vec3(neckDimensions));
        neckTranslate = new TransformNode("neck_translate", Mat4Transform.translate(new Vec3(0f, 0f, 0f)));
        mat = Mat4.multiply(mat, Mat4Transform.translate(0f, bodyDimensions.y * 2 + neckDimensions.y, 0f));
        TransformNode neckTransform = new TransformNode("robot_neck_transform", mat);
        ModelNode neckShape = new ModelNode("Sphere(neck)", sphere);

        NameNode head = new NameNode("robot_head");
        mat = Mat4Transform.scale(new Vec3(headDimensions));
        mat = Mat4.multiply(mat, Mat4Transform.translate(0f, bodyDimensions.y * 2 + neckDimensions.y + headDimensions.y * 2, 0f));
        TransformNode headTransform = new TransformNode("robot_head_transform", mat);
        ModelNode headShape = new ModelNode("Sphere(head)", sphere);

        NameNode headPart1 = new NameNode("robot_head");
        mat = Mat4Transform.scale(new Vec3(headPart1Dimensions));
        mat = Mat4.multiply(mat, Mat4Transform.translate(-1.7f, 3f + footDimensions.y * 2 + bodyDimensions.y * 2 + neckDimensions.y * 2 + headDimensions.y * 2 + headPart1Dimensions.y, 0f));
        TransformNode head1Transform = new TransformNode("robot_head_part_1_transform", mat);
        ModelNode head1Shape = new ModelNode("Sphere(headp1)", sphere);

        NameNode headPart2 = new NameNode("robot_head");
        mat = Mat4Transform.scale(new Vec3(headPart2Dimensions));
        mat = Mat4.multiply(mat, Mat4Transform.translate(0f, 3f + footDimensions.y * 2 + bodyDimensions.y * 2 + neckDimensions.y * 2 + headDimensions.y * 2 + headPart2Dimensions.y, 0f));
        TransformNode head2Transform = new TransformNode("robot_head_part_2_transform", mat);
        ModelNode head2Shape = new ModelNode("Sphere(headp2)", sphere);

        NameNode headPart3 = new NameNode("robot_head");
        mat = Mat4Transform.scale(new Vec3(headPart3Dimensions));
        mat = Mat4.multiply(mat, Mat4Transform.translate(1.7f, 3f + footDimensions.y * 2 + bodyDimensions.y * 2 + neckDimensions.y * 2 + headDimensions.y * 2 + headPart3Dimensions.y, 0f));
        TransformNode head3Transform = new TransformNode("robot_head_part_3_transform", mat);
        ModelNode head3Shape = new ModelNode("Sphere(headp3)", sphere);


		robotRoot.addChild(robotMoveTranslate);
		robotMoveTranslate.addChild(robotTranslate);
			robotTranslate.addChild(body);
				body.addChild(bodyTransform);
				    bodyTransform.addChild(bodyShape);
                body.addChild(foot);
                    foot.addChild(footTransform);
                    footTransform.addChild(footShape);
                body.addChild(neckTranslate);
                    neckTranslate.addChild(neck);
                        neck.addChild(neckTransform);
                        neckTransform.addChild(neckShape);
                body.addChild(head);
                    head.addChild(headTransform);
                    headTransform.addChild(headShape);
                body.addChild(headPart1);
                    headPart1.addChild(head1Transform);
                    head1Transform.addChild(head1Shape);
                body.addChild(headPart2);
                    headPart2.addChild(head2Transform);
                    head2Transform.addChild(head2Shape);
                body.addChild(headPart3);
                    headPart3.addChild(head3Transform);
                    head3Transform.addChild(head3Shape);
                
                    
        robotRoot.update();
    }

    /**
     * This method draws the robot in the scene.
     * @param gl OpenGl object
     */
    public void draw(GL3 gl) {
        robotRoot.draw(gl);
    }

    /**
     * This method updates the robot properties.
     */
    public void update() {
        robotRoot.update();
    }

    /**
     * This method sets the robot position
     * @param position vec3 containing the robot x, y, z positions.
     */
    public void setRobotPosition(Vec3 position) {
        robotPosition = new Vec3(position);
    }

    /**
     * This methos sets the body dimensions of the robot.
     * @param bodyDim vec3 containing the robot dimensions
     */
    public void setBodyDimesnions(Vec3 bodyDim) {
        bodyDimensions = new Vec3(bodyDim);
    }

    /**
     * This method sets the foot dimensions of the robot.
     * @param footDim vec3 containing the robot foot dimensions.
     */
    public void setFootDimensions(Vec3 footDim) {
        footDimensions = new Vec3(footDim);
    }

    /**
     * This method sets the neck dimensions of the robot.
     * @param neckDim vec3 containing the robot neck dimensions.
     */
    public void setNeckDimensions(Vec3 neckDim) {
        neckDimensions = new Vec3(neckDim);
    }

    /**
     * This method sets the head dimensions of the robot.
     * @param headDim vec3 containing the robot head dimensions.
     */
    public void setHeadDimensions(Vec3 headDim) {
        headDimensions = new Vec3(headDim);
    }

}
