package com.example.puzzleduck.threeD_Chess;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class threeD_Chess extends Activity {
    
	//Decided I don;t have enough practice with open GL...
	// T4: going through the NeHe tutorials, they seem to almost be a standard
	// and have model loading in a tutorial
	// Props: http://www.insanitydesign.com/
	
	private GLSurfaceView _threeD_Chess_view;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        _threeD_Chess_view = new threeD_Chess_view(this);
        _threeD_Chess_view = new GLSurfaceView(this);

        //NeHe tutorial: adding custom renderer:
        _threeD_Chess_view.setRenderer(new threeD_Renderer(this));
        setContentView(_threeD_Chess_view);
    }
    
    //NeHe Tutorial: adding pause and resume:
    @Override
    protected void onResume(){
    	super.onResume();
    	_threeD_Chess_view.onResume();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	_threeD_Chess_view.onPause();
    }
    
    
    
}