/**
 * This class is the starting point for the project
 * This class was created with the help of online tutorials
 * University year: 2021-2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Museum extends JFrame implements ActionListener {
  
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 768;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  private GLCanvas canvas;
  private MEventListener glEventListener;
  private final FPSAnimator animator; 

  /**
   * Constructor of the class
   * Takes care of initialising and creating a drawing canvas and binding event listeners.
   * @param textForTitleBar
   */
  public Museum(String textForTitleBar) {
    super(textForTitleBar);
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
    canvas = new GLCanvas(glcapabilities);
    Camera camera = new Camera(Camera.DEFAULT_POSITION, Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
    glEventListener = new MEventListener(camera);
    canvas.addGLEventListener(glEventListener);
	  canvas.addKeyListener(new MyKeyboardInput(camera));
    canvas.addMouseMotionListener(new MyMouseInput(camera));
    getContentPane().add(canvas, BorderLayout.CENTER);

    JPanel p = new JPanel();

    JButton b = new JButton("Pose 1");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Pose 2");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Pose 3");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Pose 4");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Pose 5");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Lights on/off");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Lamp on/off");
    b.addActionListener(this);
    p.add(b);
    b = new JButton("Day/Night");
    b.addActionListener(this);
    p.add(b);

    
    this.add(p, BorderLayout.SOUTH);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        remove(canvas);
        dispose();
        System.exit(0);
      }
    });
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }

  /**
   * Callback function triggered by an event.
   * @param e the event that occured
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("lights on/off")) {
      glEventListener.switchLights();
    } else if (e.getActionCommand().equalsIgnoreCase("pose 1")) {
      glEventListener.moveRobotPose1();
    } else if (e.getActionCommand().equalsIgnoreCase("pose 2")) {
      glEventListener.moveRobotPose2();
    } else if (e.getActionCommand().equalsIgnoreCase("pose 3")) {
      glEventListener.moveRobotPose3();
    } else if (e.getActionCommand().equalsIgnoreCase("pose 4")) {
      glEventListener.moveRobotPose4();
    } else if (e.getActionCommand().equalsIgnoreCase("pose 5")) {
      glEventListener.moveRobotPose5();
    } else if (e.getActionCommand().equalsIgnoreCase("Day/Night")) {
      glEventListener.setDayNight();
    }
  }

  public static void main(String[] args) {
      Museum m = new Museum("3D Graphics Project");
      m.getContentPane().setPreferredSize(dimension);
      m.pack();
      m.setVisible(true);
    }

  
  class MyKeyboardInput extends KeyAdapter  {
    private Camera camera;
    
    public MyKeyboardInput(Camera camera) {
      this.camera = camera;
    }
    
    /**
     * This method handles keyboard events.
     * @param e the key event type
     */
    public void keyPressed(KeyEvent e) {
      Camera.Movement m = Camera.Movement.NO_MOVEMENT;
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
        case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
        case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
        case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
        case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
        case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
      }
      camera.keyboardInput(m);
    }
  }

  class MyMouseInput extends MouseMotionAdapter {
    private Point lastpoint;
    private Camera camera;
    
    public MyMouseInput(Camera camera) {
      this.camera = camera;
    }
    
    /**
     * mouse is used to control camera position
     *
     * @param e  instance of MouseEvent
     */    
    public void mouseDragged(MouseEvent e) {
      Point ms = e.getPoint();
      float sensitivity = 0.001f;
      float dx=(float) (ms.x-lastpoint.x)*sensitivity;
      float dy=(float) (ms.y-lastpoint.y)*sensitivity;
      //System.out.println("dy,dy: "+dx+","+dy);
      if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
        camera.updateYawPitch(dx, -dy);
      lastpoint = ms;
    }

    /**
     * mouse is used to control camera position
     *
     * @param e  instance of MouseEvent
     */  
    public void mouseMoved(MouseEvent e) {   
      lastpoint = e.getPoint(); 
    }
  }
}