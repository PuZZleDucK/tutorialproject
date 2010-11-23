package com.example.puzzleduck.threeD_Chess;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Cube {
	private static final int BYTE_SIZE = 4;
	private FloatBuffer vertexBuffer, textureBuffer;
	private ByteBuffer indexBuffer;
	
	private int[] textures = new int[1];
	
	private float vertices[] ={
    		-1.0f, -1.0f, 1.0f, //Vertex 0
    		1.0f, -1.0f, 1.0f,  //v1
    		-1.0f, 1.0f, 1.0f,  //v2
    		1.0f, 1.0f, 1.0f,   //v3
    		
    		1.0f, -1.0f, 1.0f,	//...
    		1.0f, -1.0f, -1.0f,    		
    		1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, -1.0f,
    		
    		1.0f, -1.0f, -1.0f,
    		-1.0f, -1.0f, -1.0f,    		
    		1.0f, 1.0f, -1.0f,
    		-1.0f, 1.0f, -1.0f,
    		
    		-1.0f, -1.0f, -1.0f,
    		-1.0f, -1.0f, 1.0f,    		
    		-1.0f, 1.0f, -1.0f,
    		-1.0f, 1.0f, 1.0f,
    		
    		-1.0f, -1.0f, -1.0f,
    		1.0f, -1.0f, -1.0f,    		
    		-1.0f, -1.0f, 1.0f,
    		1.0f, -1.0f, 1.0f,
    		
    		-1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f,    		
    		-1.0f, 1.0f, -1.0f,
    		1.0f, 1.0f, -1.0f
	};
	
	private float texture[] ={	
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f, 
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
    		
    		0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 0.0f,
    		1.0f, 1.0f,
	};
	
	private byte indices[] = {
    		0,1,3, 0,3,2, 			//Face front
    		4,5,7, 4,7,6, 			//Face right
    		8,9,11, 8,11,10, 		//... 
    		12,13,15, 12,15,14, 	
    		16,17,19, 16,19,18, 	
    		20,21,23, 20,23,22, 	
	};
	
	public Cube()
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
		
		indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
	}
	
	//Self Rendering
	public void draw(GL10 gl)
	{
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		//enable buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		//face rotation
		gl.glFrontFace(GL10.GL_CW);
		//point to buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		//draw triangles
		gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, indexBuffer);

		//disable client state
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
	}

	public void loadGLTexture(GL10 gl, Context context) {
		InputStream inStream = context.getResources().openRawResource(R.drawable.ehne);
		Bitmap bitmap = null;
		
		try
		{
			bitmap = BitmapFactory.decodeStream(inStream);
		}finally
		{
			try
			{
				inStream.close();
				inStream = null;
			}catch(IOException e){}
			
		}
		
		//gen tex and bind to array
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		//create filtered tex
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		//other filters
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
		//specify image for bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
		
	}	
	
	
	
	
	
}
