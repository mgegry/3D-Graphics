/**
 * This class handels the raw drawing of the verticies and also the buffer handeling.
 * This class was created fully with the help of online tutorials.
 * University year: 2021-2022
 * @author Mircea Gelu Egry
 * @version 1.0
 */

import gmaths.*;
import java.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.common.nio.*;

public class Mesh {
    private float[] verticies;
    private int[] indices;
    private int vertexStride = 8;
    private int vertexXYZFloats = 3;
    private int vertexNormalFloats = 3;
    private int vertexTexFloats = 2;

    private int[] vertexBufferId = new int[1];
    private int[] vertexArrayId = new int[1];
    private int[] elementBufferId = new int[1];

    /**
     * The class constructor
     * @param gl the OpenGL object
     * @param verticies the verticies to be drawn
     * @param indices array containig the order in which the verticies should be drawn.
     */
    public Mesh(GL3 gl, float[] verticies, int[] indices) {
        this.verticies = verticies;
        this.indices = indices;
        fillBuffers(gl);
    }

    /**
     * This method renders the verticies on the scree.
     * @param gl the OpenGl object
     */
    public void render(GL3 gl) {
        gl.glBindVertexArray(vertexArrayId[0]);
        gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindVertexArray(0);
    }

    /**
     * This method fills the vertex and the elemtent buffers and alocates the required amount of memory.
     * @param gl the OpenGL object
     */
    private void fillBuffers(GL3 gl) {
        gl.glGenVertexArrays(1, vertexArrayId, 0);
        gl.glBindVertexArray(vertexArrayId[0]);
        
        gl.glGenBuffers(1, vertexBufferId, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferId[0]);
        FloatBuffer fb = Buffers.newDirectFloatBuffer(verticies);

        gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * verticies.length, fb, GL.GL_STATIC_DRAW);

        int stride = vertexStride;
        int numXYZFloats = vertexXYZFloats;
        int offset = 0;

        // Generate vertex pointer to x, y, z positions
        gl.glVertexAttribPointer(0, numXYZFloats, GL.GL_FLOAT, false, stride * Float.BYTES, offset);
        gl.glEnableVertexAttribArray(0);

        int numNormalFloats = vertexNormalFloats;
        offset = numXYZFloats * Float.BYTES;

        // Generate vertex pointer to x, y, z normals
        gl.glVertexAttribPointer(1, numNormalFloats, GL.GL_FLOAT, false, stride * Float.BYTES, offset);
        gl.glEnableVertexAttribArray(1);

        int numTexFloats = vertexTexFloats;
        offset = (numXYZFloats + numNormalFloats) * Float.BYTES;
        
        // Generate vertex pointer to texture coordinates
        gl.glVertexAttribPointer(2, numTexFloats, GL.GL_FLOAT, false, stride * Float.BYTES, offset);
        gl.glEnableVertexAttribArray(2);

        // Generate the element buffer
        gl.glGenBuffers(1, elementBufferId, 0);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, elementBufferId[0]);
        IntBuffer ib = Buffers.newDirectIntBuffer(indices);
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, Integer.BYTES * indices.length, ib, GL.GL_STATIC_DRAW);

        //gl.glBindVertexArray(0);
    }

    /**
     * This method disposes the unused resources.
     * @param gl the OpenGl object
     */
    public void dispose(GL3 gl) {
        gl.glDeleteBuffers(1, vertexBufferId, 0);
        gl.glDeleteVertexArrays(1, vertexArrayId, 0);
        gl.glDeleteBuffers(1, elementBufferId, 0);
    }
}
