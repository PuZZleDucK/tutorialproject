package com.example.puzzleduck.threeD_Chess;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class threeD_Chess_view extends GLSurfaceView{
	
//	private static final String LOG_TAG = threeD_Chess.class.getSimpleName();
	private threeD_Renderer _renderer;
	

	private float _x = 0;
	private float _y = 0;

	
	public threeD_Chess_view(Context context){
		super(context);
		_renderer = new threeD_Renderer();
		setRenderer(_renderer);
	}
	
	
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
//		queueEvent(new Runnable(){
//			public void run(){
//				//_renderer.setColor(event.getY()/getWidth(), event.getY()/getHeight(), event.getY()/getHeight());
//				_renderer.setAngle(event.getX()*2);
//				
//			}
//		});
//		
//		return true;

		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			_x = event.getX();
			_y = event.getY();
		}
		if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			final float xDiff = (_x - event.getX());
			final float yDiff = (_y - event.getY());
			queueEvent(new Runnable(){
				public void run(){
				//_renderer.setColor(event.getY()/getWidth(), event.getY()/getHeight(), event.getY()/getHeight());
					_renderer.setXAngle(_renderer.getXAngle()+yDiff);
					_renderer.setYAngle(_renderer.getYAngle()+xDiff);
				}
			});
			_x = event.getX();
			_y = event.getY();
			
		}
	
		return true;
	}
	
	
	
	
}
