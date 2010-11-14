package com.example.puzzleduck.threeD_Chess;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class threeD_Renderer implements GLSurfaceView.Renderer {
//	private static final String LOG_TAG = threeD_Chess.class.getSimpleName();

	private ShortBuffer _indexBuffer;
	private FloatBuffer _vertexBuffer;
	private FloatBuffer _colorBuffer;
	private int _vertexCount = 0;

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
	
	
	private void initTriangle(){               //  vertex * indicies * float size
		
		float[] coords = {
//				-0.5f, -0.5f, 0f,
//				0.5f, -0.5f, 0f,
//				0f, 0.5f, 0f
	            -0.5f, -0.5f, 0.5f, // 0
	            0.5f, -0.5f, 0.5f, // 1
	            0f, -0.5f, -0.5f, // 2
	            0f, 0.5f, 0f, // 3
		};
		_vertexCount = coords.length;
		
		float[] colors = {
//				1f, 0f, 0f, 1f,
//				0f, 1f, 0f, 1f,
//				0f, 0f, 1f, 1f,
	            1f, 0f, 0f, 1f, // point 0 red
	            0f, 1f, 0f, 1f, // point 1 green
	            0f, 0f, 1f, 1f, // point 2 blue
	            1f, 1f, 1f, 1f, // point 3 white
		};
		
		short[] indicies = new short[]{
//				0,1,2
	            0, 1, 3, // rwg
	            0, 2, 1, // rbg
	            0, 3, 2, // rbw
	            1, 2, 3, // bwg
		};
		
		ByteBuffer vertexbb =  ByteBuffer.allocateDirect(coords.length * 4);
		vertexbb.order(ByteOrder.nativeOrder());
		_vertexBuffer = vertexbb.asFloatBuffer();
		
        // vertex * short size
		ByteBuffer indexbb =  ByteBuffer.allocateDirect(indicies.length * 3);
		indexbb.order(ByteOrder.nativeOrder());
		_indexBuffer = indexbb.asShortBuffer();
		
        // vertex * short size
		ByteBuffer colorbb =  ByteBuffer.allocateDirect(colors.length * 4);
		colorbb.order(ByteOrder.nativeOrder());
		_colorBuffer = colorbb.asFloatBuffer();
		

		
		_vertexBuffer.put(coords);
		_indexBuffer.put(indicies);
		_colorBuffer.put(colors);
		
		_vertexBuffer.position(0);
		_indexBuffer.position(0);
		_colorBuffer.position(0);
	}
	
	

	

	public void onDrawFrame(GL10 gl) {
		//clear buffer and reset matrix
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT| GL10.GL_DEPTH_BUFFER_BIT);
		
		//gl.glColor4f(_red, _green, _blue, 1.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
		
		for(int i = 1; i <= 10; i++)
		{
			gl.glLoadIdentity();
			gl.glTranslatef(0.0f, -1f, -0.1f + -1.5f * i );
			//rotate
			gl.glRotatef(_Xangle, 1f, 0f, 0f);
			gl.glRotatef(_Yangle, 0f, 1f, 0f);
			gl.glDrawElements(GL10.GL_TRIANGLES, _vertexCount, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
	
		}
		
		
		
		
//		
//	    // clear the color buffer and the depth buffer
//	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//	 
//	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
//	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
//	 
//	    for (int i = 1; i <= 10; i++) {
//	        gl.glLoadIdentity();
//	        gl.glTranslatef(0.0f, -1f, -1.0f + -1.5f * i);
//	        // set rotation
//	        gl.glRotatef(_Xangle, 1f, 0f, 0f);
//	        gl.glRotatef(_Yangle, 0f, 1f, 0f);
//	        gl.glDrawElements(GL10.GL_TRIANGLES, _vertexCount, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
//	    }

		
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		_width = width;
		_height = height;
		gl.glViewport(0, 0, width, height);
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		float size = 0.01f * (float) Math.tan(Math.toRadians(45.0)/2);
		float ratio = _width/_height;
		
//		gl.glOrthof(-1, 1, -1/ratio, 1/ratio, 0.01f, 100.0f);
		gl.glFrustumf(-size, size, -size/ratio, size/ratio, 0.01f, 100.0f);
		gl.glViewport(0, 0, (int)_width, (int)_height);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glEnable(GL10.GL_DEPTH_TEST);//checking z-order
		
		
		//Define "clipping wall"/clear color - once only
		gl.glClearColor(0f, 0f, 0f, 1.0f);
		//enable culling
		gl.glEnable(GL10.GL_CULL_FACE);
		//winding
		gl.glFrontFace(GL10.GL_CCW);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		initTriangle();
		
		
		
		
//	    gl.glMatrixMode(GL10.GL_PROJECTION);
//	    float size = .01f * (float) Math.tan(Math.toRadians(45.0) / 2); 
//	    float ratio = _width / _height;
//	    // perspective:
//	    gl.glFrustumf(-size, size, -size / ratio, size / ratio, 0.01f, 100.0f);
//	    // orthographic:
//	    //gl.glOrthof(-1, 1, -1 / ratio, 1 / ratio, 0.01f, 100.0f);
//	    gl.glViewport(0, 0, (int) _width, (int) _height);
//	    gl.glMatrixMode(GL10.GL_MODELVIEW);
//	    gl.glEnable(GL10.GL_DEPTH_TEST);
//	 
//	    // define the color we want to be displayed as the "clipping wall"
//	    gl.glClearColor(0f, 0f, 0f, 1.0f);
//	 
//	    // enable the differentiation of which side may be visible 
//	    gl.glEnable(GL10.GL_CULL_FACE);
//	    // which is the front? the one which is drawn counter clockwise
//	    gl.glFrontFace(GL10.GL_CCW);
//	    // which one should NOT be drawn
//	    gl.glCullFace(GL10.GL_BACK);
//	 
//	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//	    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//	 
//	    initTriangle();

		
		
	}
	
	

//	public void setColor(float r, float g, float b)
//	{
//		_red   = r;
//		_green = g;
//		_blue  = b;
//	}

	
	
}
