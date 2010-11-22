package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class threeD_Renderer implements Renderer {
	
	//NeHe: Adding new object classes
	private Triangle triangle;
	private Square square;
	
	
	private float sqrRot, triRot = 0;
	
	
	public threeD_Renderer(){
		triangle = new Triangle();
		square = new Square();
	}
	
	
//	private ShortBuffer _indexBuffer;
//	private FloatBuffer _vertexBuffer;
//	private FloatBuffer _colorBuffer;
//	private int _vertexCount = 0;

	private float _Xangle;
	private float _Yangle;
	private float _width = 320f;
	private float _height = 480f;
	
	public void setXAngle( float angle )
	{
		_Xangle = angle;
	}
	
	public void setYAngle( float angle )
	{
		_Yangle = angle;
	}
	
	public float getXAngle()
	{
		return _Xangle;
	}
	
	public float getYAngle()
	{
		return _Yangle;
	}
		

	public void onDrawFrame(GL10 gl) {
		//NeHe:		
		
		//clear buffer and reset matrix
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT| GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		

		gl.glTranslatef(0.0f, -1.2f, -6.0f);
		gl.glRotatef(sqrRot, 1.0f, 0.0f, 0.0f);
		square.draw(gl);

		gl.glLoadIdentity(); 
		
		gl.glTranslatef(0.0f, 1.3f, -6.0f);
		gl.glRotatef(triRot, 0.0f, 1.3f, -6.0f);
		triangle.draw(gl);

		triRot += 1.1f;
		sqrRot -= 2.2f;
		
		//Origional:
		
		//gl.glColor4f(_red, _green, _blue, 1.0f);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
//		gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
//		
//		for(int i = 1; i <= 10; i++)
//		{
//			gl.glTranslatef(0.0f, -1f, -0.1f + -1.5f * i );
//			//rotate
//			gl.glRotatef(_Xangle, 1f, 0f, 0f);
//			gl.glRotatef(_Yangle, 0f, 1f, 0f);
//			gl.glDrawElements(GL10.GL_TRIANGLES, _vertexCount, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
//	
//		}
				
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
//		_width = width;
//		_height = height;
		if( height == 0 )
		{
			height = 1;
		}
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		//aspect ratio
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 1.0f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//NeHe:
		gl.glShadeModel(GL10.GL_SMOOTH);
		//Define "clipping wall"/clear color - lower transparency than origional?
		gl.glClearColor(0f, 0f, 0f, 0.5f);
		//Clear depth
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);//checking z-order
		gl.glDepthFunc(GL10.GL_LEQUAL);
		//perspective
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
//		//Origional
//		
//		float size = 0.01f * (float) Math.tan(Math.toRadians(45.0)/2);
//		float ratio = _width/_height;
//		
////		gl.glOrthof(-1, 1, -1/ratio, 1/ratio, 0.01f, 100.0f);
//		gl.glFrustumf(-size, size, -size/ratio, size/ratio, 0.01f, 100.0f);
//		gl.glViewport(0, 0, (int)_width, (int)_height);
//		
//		
//		//enable culling
//		gl.glEnable(GL10.GL_CULL_FACE);
//		//winding
//		gl.glFrontFace(GL10.GL_CCW);
//		gl.glCullFace(GL10.GL_BACK);
//		
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//		initTriangle();
	
	}
	
	
}
