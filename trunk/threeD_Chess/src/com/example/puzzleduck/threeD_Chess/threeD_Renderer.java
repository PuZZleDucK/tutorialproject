package com.example.puzzleduck.threeD_Chess;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class threeD_Renderer implements Renderer {
	
	//NeHe: Adding new object classes
	private Triangle triangle;
	private Square square;

	private Pyramid pyramid;
	private Cube cube;
	
	
	private float sqrRot, triRot = 0;
	
	
	public threeD_Renderer(){
		triangle = new Triangle();
		square = new Square();
		pyramid = new Pyramid();
		cube = new Cube();
	}
	
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
	
		gl.glTranslatef(0.0f, -1.2f, -26.0f);
		gl.glRotatef(sqrRot, 1.0f, 0.0f, 0.0f);
		square.draw(gl);

		gl.glTranslatef(0.0f, -2.6f, -10.0f);
		gl.glRotatef(sqrRot, 1.0f, 0.0f, 0.0f);
		square.draw(gl);

		gl.glLoadIdentity(); 
		
		gl.glTranslatef(0.0f, 1.3f, -26.0f);
		gl.glRotatef(triRot, 0.0f, 1.3f, -6.0f);
		triangle.draw(gl);

		
		gl.glLoadIdentity(); 
		gl.glTranslatef(0.0f, -1.2f, -7.0f);
		gl.glScalef(0.8f, 0.8f, 0.8f);
		gl.glRotatef(sqrRot, 1.0f, 1.0f, 1.0f);
		cube.draw(gl);
		
		
		gl.glLoadIdentity(); 
		gl.glTranslatef(0.0f, 1.3f, -6.0f);
		gl.glRotatef(triRot, 0.0f, 1.0f, 0.0f);
		pyramid.draw(gl);
		
		
		
		
		
		triRot += 2.0f;
		sqrRot -= 1.0f;
						
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		_width = width;
		_height = height;
		if( height == 0 )
		{
			height = 1;
		}
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		//aspect ratio
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
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
		
	
	}
	
	
}
