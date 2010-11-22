package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {

	private static final int BYTE_SIZE = 4;
	private FloatBuffer vertexBuffer, colorBuffer;
	private float vertices[] ={
			-1.0f, -1.0f, 0.0f, //Bottom Left
			1.0f, -1.0f, 0.0f, 	//Bottom Right
			-1.0f, 1.0f, 0.0f, 	//Top Left
			1.0f, 1.0f, 0.0f 	//Top Right
	};
	
	private float colors[] ={
			0.0f, 0.0f, 0.0f, 1.0f, 	//Bottom Left	k
			0.0f, 1.0f, 0.0f, 1.0f, 	//Bottom Right	g
			0.0f, 0.0f, 1.0f, 1.0f, 	//Top Left		b
			0.0f, 0.0f, 0.0f, 1.0f		//Top Right		k
	};
	
	public Square()
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
		
	}
	
	//Self Rendering
	public void draw(GL10 gl)
	{
		//face rotation
		gl.glFrontFace(GL10.GL_CW);
		//point to buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		//set color matrix
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		//enable buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//		//set color: blue
//		gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		//draw triangles
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length/3);
		//disable client state
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
	}	
	
	
}
