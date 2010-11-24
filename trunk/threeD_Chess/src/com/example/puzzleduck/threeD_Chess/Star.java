package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Star 
{
	private static final int BYTE_SIZE = 4;
	public int r, g, b;
	public float distance, angle;
	
	private FloatBuffer vertexBuffer, textureBuffer;

	private float vertices[] = {
			-1.0f, -1.0f, 0.0f, 	//Bottom Left
			1.0f, -1.0f, 0.0f, 		//Bottom Right
			-1.0f, 1.0f, 0.0f,	 	//Top Left
			1.0f, 1.0f, 0.0f 		
	};

	private float texture[] = {
			0.0f, 0.0f, 
			1.0f, 0.0f, 
			0.0f, 1.0f, 
			1.0f, 1.0f,
	};
	
	public Star()
	{
		ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * BYTE_SIZE);
		bBuff.order(ByteOrder.nativeOrder());
		vertexBuffer = bBuff.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		bBuff = ByteBuffer.allocateDirect(texture.length * BYTE_SIZE);
		bBuff.order(ByteOrder.nativeOrder());
		textureBuffer = bBuff.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		
	}
	
	public void draw(GL10 gl)
	{

		//enable buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		//point to buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		

		//draw triangles
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length/3);

		//disable client state
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

}
