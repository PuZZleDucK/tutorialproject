package com.example.puzzleduck.threeD_Chess;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Stars 
{
	private int number = 1;
	private Star[] stars;
	
	private Random rng = new Random();
	
	private float zoom = -15.0f;
	private float tilt = 90.0f;
	private float spin;
	
	private int[] textures = new int[1];
	
	public Stars(int number)
	{
		this.number = number;
		
		stars = new Star[number];
		
		for(int i = 0; i < number; i++)
		{
			stars[i] = new Star();
			stars[i].angle = 0.0f;
			stars[i].distance = ((float)i/number) * 5.0f;
			stars[i].r = rng.nextInt(256);
			stars[i].g = rng.nextInt(256);
			stars[i].b = rng.nextInt(256);
			
		}
	}
	
	
	public void draw(GL10 gl, boolean twinkle)
	{
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		for(int i = 0; i < number; i++)
		{
			Star thisStar = stars[i];

			gl.glLoadIdentity();
			gl.glTranslatef(0.0f, 0.0f, zoom);
			gl.glRotatef(tilt, 1.0f, 0.0f, 0.0f);
			gl.glRotatef(thisStar.angle, 0.0f, 1.0f, 0.0f);
			
			gl.glTranslatef(thisStar.distance, 0.0f, 0.0f);
			gl.glRotatef(-thisStar.angle, 0.0f, 1.0f, 0.0f);
			gl.glRotatef(-tilt, 1.0f, 0.0f, 0.0f);
			
			if(twinkle)
			{
				gl.glColor4f( (float)stars[((number-i)-1)].r/255, 
							  (float)stars[((number-i)-1)].g/255, 
							  (float)stars[((number-i)-1)].b/255, 1.0f);
				thisStar.draw(gl);
				
							  
			}
			
			gl.glRotatef(spin, 0.0f, 0.0f, 1.0f);
			gl.glColor4f((float)thisStar.r/255, (float)thisStar.g/255, (float)thisStar.b/255, 1.0f);
			
			thisStar.draw(gl);
			
			spin += 0.01f;
			thisStar.angle += (float) i/number;
			thisStar.distance -= 0.01f;
			if( thisStar.distance < 0.0f)
			{
				thisStar.distance = 5.0f;
				thisStar.r = rng.nextInt(256);
				thisStar.g = rng.nextInt(256);
				thisStar.b = rng.nextInt(256);
			}
			
			
		}
		
	}
	
	
	public void loadGLTexture(GL10 gl, Context context) {
		InputStream inStream = context.getResources().openRawResource(R.drawable.star);
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

		//create LINEAR tex and bind to array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		bitmap.recycle();
		
	}	
	

}
