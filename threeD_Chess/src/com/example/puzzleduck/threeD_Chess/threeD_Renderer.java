package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class threeD_Renderer extends GLSurfaceView implements Renderer {

	private static final int BYTE_SIZE = 4;
	private final int numStars = 50;
	private Stars stars;

	private boolean twinkle = true;
	private boolean blend = true;
	
//	private Cube cube;

//	private float xRot, yRot;
//	private float xSpeed, ySpeed;
//	private float depth = -5.0f;
	
	private int filter;
	
//	private boolean light = true;

//	private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f,};
//	private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f,};
//	private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f,};
	
//	private FloatBuffer lightAmbientBuffer, lightDiffuseBuffer, lightPositionBuffer;
	
//	private float oldX, oldY;
	
	private final float TOUCH_SCALE = 0.2f;
	
	private Context context;
	
	
	public threeD_Renderer(Context context){
		super(context);
		this.setRenderer(this);
		
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.context = context;
		
//		ByteBuffer bBuff = ByteBuffer.allocateDirect(lightAmbient.length * BYTE_SIZE);
//		bBuff.order(ByteOrder.nativeOrder());
//		lightAmbientBuffer = bBuff.asFloatBuffer();
//		lightAmbientBuffer.put(lightAmbient);
//		lightAmbientBuffer.position(0);
//
//		bBuff = ByteBuffer.allocateDirect(lightDiffuse.length * BYTE_SIZE);
//		bBuff.order(ByteOrder.nativeOrder());
//		lightDiffuseBuffer = bBuff.asFloatBuffer();
//		lightDiffuseBuffer.put(lightDiffuse);
//		lightDiffuseBuffer.position(0);
//
//		bBuff = ByteBuffer.allocateDirect(lightPosition.length * BYTE_SIZE);
//		bBuff.order(ByteOrder.nativeOrder());
//		lightPositionBuffer = bBuff.asFloatBuffer();
//		lightPositionBuffer.put(lightPosition);
//		lightPositionBuffer.position(0);
//		
//		
//		cube = new Cube();
	}
	

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		//lights:
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbientBuffer);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuseBuffer);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer);
//		gl.glEnable(GL10.GL_LIGHT0);
		
		//blending
//		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
//		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
//		
//		
//		gl.glDisable(GL10.GL_DITHER);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glShadeModel(GL10.GL_SMOOTH);
		
		gl.glClearColor(0f, 0f, 0f, 0.5f);
		gl.glClearDepthf(1.0f);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		
		gl.glEnable(GL10.GL_BLEND);//checking z-order
		gl.glEnable(GL10.GL_DEPTH_TEST);//checking z-order
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		//perspective
		
//		cube.loadGLTexture(gl, this.context);
		stars = new Stars(numStars);
		stars.loadGLTexture(gl, this.context);
	
	}
	


	public void onDrawFrame(GL10 gl) {
		//NeHe:  clear buffer and reset matrix
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT| GL10.GL_DEPTH_BUFFER_BIT);
//		gl.glLoadIdentity();

		if(blend)
		{
			gl.glEnable(GL10.GL_BLEND);
			gl.glDisable(GL10.GL_DEPTH_TEST);
		}else
		{
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDisable(GL10.GL_BLEND);
		}
//		
//		if(blend)
//		{
//			gl.glEnable(GL10.GL_BLEND);
//			gl.glDisable(GL10.GL_DEPTH_TEST);
//		}else
//		{
//			gl.glDisable(GL10.GL_BLEND);
//			gl.glEnable(GL10.GL_DEPTH_TEST);
//		}
//		
//		
//		gl.glTranslatef(0.0f, 0.0f, depth);
//		gl.glScalef(0.8f, 0.8f, 0.8f);
//		gl.glRotatef(xRot, 1.0f, 0.0f, 0.0f);
//		gl.glRotatef(yRot, 0.0f, 1.0f, 0.0f);
		
//		cube.draw(gl, filter);
		stars.draw(gl, twinkle);
//		xRot += xSpeed;
//		yRot += ySpeed;
						
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
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


//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) 
//	{
//		if( keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
//		{
//			ySpeed -= 0.1f;
//		}else if( keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
//		{
//			ySpeed += 0.1f;
//		}else if( keyCode == KeyEvent.KEYCODE_DPAD_UP)
//		{
//			xSpeed -= 0.1f;
//		}else if( keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
//		{
//			ySpeed += 0.1f;
//		}else if( keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
//		{
//			filter += 1;
//			if(filter > 2){filter = 0;}
//		}
//		
//		return true;
//	}


	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		float x = event.getX();
		float y = event.getY();
		if( event.getAction() == MotionEvent.ACTION_UP )
		{
			int upperArea = this.getHeight()/10;
			int lowerArea = this.getHeight()-upperArea;
			
			//zoom in top 10% of screen
			if(y < lowerArea)
			{
				if(x < (this.getWidth()/2))
				{
					blend = !blend;
				}else{
					twinkle = !twinkle;
				}
			}
		}
		return true;
	}
	
	
}
