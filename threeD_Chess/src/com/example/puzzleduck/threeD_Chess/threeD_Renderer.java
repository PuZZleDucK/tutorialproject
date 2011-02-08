package com.example.puzzleduck.threeD_Chess;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

public class threeD_Renderer extends GLSurfaceView implements Renderer {

	private final int numStars = threeD_Chess.LEVELS * threeD_Chess.RANKS * threeD_Chess.FILES;
	private Stars stars;

	private boolean twinkle = true;
	
	private Context context;
	
	
	public threeD_Renderer(Context context){
		super(context);
		this.setRenderer(this);
		
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.context = context;
	}
	

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glShadeModel(GL10.GL_SMOOTH);
		
		gl.glClearColor(0f, 0f, 0f, 0.5f);
		gl.glClearDepthf(1.0f);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_DEPTH_TEST);
//		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		//perspective
		
		stars = new Stars(numStars);
		stars.loadGLTexture(gl, this.context,1);
	
	}
	


	public void onDrawFrame(GL10 gl) {
		//NeHe:  clear buffer and reset matrix
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT| GL10.GL_DEPTH_BUFFER_BIT);

		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);

		stars.draw(gl, twinkle);
//		threeD_Chess.DoMain3DcLoop();
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

// handle move here...
//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) 
//	{
//		if( keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
//		{
//			ySpeed -= 0.1f;
//		}else if( keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
//		{
//			ySpeed += 0.1f;
//		}
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
			
			if(y < lowerArea)
			{
				if(x < (this.getWidth()/2))
				{
//					blend = !blend;
				}else{
					twinkle = !twinkle;
				}
			}
		}
		return true;
	}
	

	
	
	
	
	
	
	
}
