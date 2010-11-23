package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


	public class Pyramid{

		private static final int BYTE_SIZE = 4;
		private FloatBuffer vertexBuffer, colorBuffer;
//		private ByteBuffer indexBuffer;
		private float vertices[] ={
			 	0.0f,  1.0f,  0.0f,		//Top Of Triangle (Front)
				-1.0f, -1.0f, 1.0f,		//Left Of Triangle (Front)
				 1.0f, -1.0f, 1.0f,		//Right Of Triangle (Front)
				 0.0f,  1.0f, 0.0f,		//Top Of Triangle (Right)
				 1.0f, -1.0f, 1.0f,		//Left Of Triangle (Right)
				 1.0f, -1.0f, -1.0f,	//Right Of Triangle (Right)
				 0.0f,  1.0f, 0.0f,		//Top Of Triangle (Back)
				 1.0f, -1.0f, -1.0f,	//Left Of Triangle (Back)
				-1.0f, -1.0f, -1.0f,	//Right Of Triangle (Back)
				 0.0f,  1.0f, 0.0f,		//Top Of Triangle (Left)
				-1.0f, -1.0f, -1.0f,	//Left Of Triangle (Left)
				-1.0f, -1.0f, 1.0f		//Right Of Triangle (Left)
		};
		
		private float colors[] ={
	    		1.0f, 0.0f, 0.0f, 1.0f, //Red
	    		0.0f, 1.0f, 0.0f, 1.0f, //Green
	    		0.0f, 0.0f, 1.0f, 1.0f, //Blue
	    		1.0f, 0.0f, 0.0f, 1.0f, //Red
	    		0.0f, 0.0f, 1.0f, 1.0f, //Blue
	    		0.0f, 1.0f, 0.0f, 1.0f, //Green
	    		1.0f, 0.0f, 0.0f, 1.0f, //Red
	    		0.0f, 1.0f, 0.0f, 1.0f, //Green
	    		0.0f, 0.0f, 1.0f, 1.0f, //Blue
	    		1.0f, 0.0f, 0.0f, 1.0f, //Red
	    		0.0f, 0.0f, 1.0f, 1.0f, //Blue
	    		0.0f, 1.0f, 0.0f, 1.0f 	//Green
		};
		
		
		public Pyramid()
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
			//draw triangles
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length/3);
			//disable client state
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			
		}	
		
		
		
		
		
	}

