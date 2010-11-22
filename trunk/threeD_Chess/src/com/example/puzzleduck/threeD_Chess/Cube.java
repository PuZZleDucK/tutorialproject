package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
	private static final int BYTE_SIZE = 4;
	private FloatBuffer vertexBuffer, colorBuffer;
	private ByteBuffer indexBuffer;
	private float vertices[] ={
            -1.0f, -1.0f, -1.0f,	//lower back left (0)
            1.0f, -1.0f, -1.0f,		//lower back right (1)
            1.0f,  1.0f, -1.0f,		//upper back right (2)
            -1.0f, 1.0f, -1.0f,		//upper back left (3)
            -1.0f, -1.0f,  1.0f,	//lower front left (4)
            1.0f, -1.0f,  1.0f,		//lower front right (5)
            1.0f,  1.0f,  1.0f,		//upper front right (6)
            -1.0f,  1.0f,  1.0f		//upper front left (7)
	};
	
	private float colors[] ={
            0.0f,  1.0f,  0.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            0.0f,  0.0f,  1.0f,  1.0f,
            1.0f,  0.0f,  1.0f,  1.0f
	};
	
	private byte indices[] = {
            0, 4, 5,    0, 5, 1,
            1, 5, 6,    1, 6, 2,
            2, 6, 7,    2, 7, 3,
            3, 7, 4,    3, 4, 0,
            4, 7, 6,    4, 6, 5,
            3, 0, 1,    3, 1, 2
	};
	
	public Cube()
	{
		ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * BYTE_SIZE);
		bBuff.order(ByteOrder.nativeOrder());
		vertexBuffer = bBuff.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		bBuff = ByteBuffer.allocateDirect(colors.length * BYTE_SIZE);
		bBuff.order(ByteOrder.nativeOrder());
		colorBuffer = bBuff.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
	}
	
	//Self Rendering
	public void draw(GL10 gl)
	{
		//face rotation
		gl.glFrontFace(GL10.GL_CW);
		//point to buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		//enable buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		//draw triangles
		gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, indexBuffer);

		//disable client state
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
	}	
	
	
	
	
	
}
