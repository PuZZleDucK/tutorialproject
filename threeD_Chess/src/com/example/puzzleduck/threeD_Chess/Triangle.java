package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

//NeHe

public class Triangle {

	private static final int BYTE_SIZE = 4;
	private FloatBuffer vertexBuffer;
	private float vertices[] ={
			0.0f, 1.0f, 0.0f,	//T
			-1.0f, -1.0f, 0.0f,	//B-l
			1.0f, -1.0f, 0.0f	//B-r
	};
	
	public Triangle()
	{
		ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * BYTE_SIZE);
		bBuff.order(ByteOrder.nativeOrder());
		vertexBuffer = bBuff.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
	}
	
	//Self Rendering
	public void draw(GL10 gl)
	{
		//face rotation
		gl.glFrontFace(GL10.GL_CW);
		//point to buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		//enable buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//traw triangles
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length/3);
		//disable client state
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}
}
