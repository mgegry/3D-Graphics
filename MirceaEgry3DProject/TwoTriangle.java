/**
 * This class holds drawing information about two trinagles.
 * University year: 2021-2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */
public final class TwoTriangle {
    public static final float[] vertices = {      // position, colour, tex coords
        -0.5f, 0.0f, -0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 3.0f,  // top left
        -0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 0.0f,  // bottom left
         0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,  3.0f, 0.0f,  // bottom right
         0.5f, 0.0f, -0.5f,  0.0f, 1.0f, 0.0f,  3.0f, 3.0f   // top right
    };
      
    public static final int[] indices = {         // Note that we start from 0!
        0, 1, 2,
        0, 2, 3
    };
    
}
