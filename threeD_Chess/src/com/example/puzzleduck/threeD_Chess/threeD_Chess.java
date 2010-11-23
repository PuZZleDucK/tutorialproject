package com.example.puzzleduck.threeD_Chess;

import android.app.Activity;
import android.os.Bundle;

public class threeD_Chess extends Activity {
	
	private threeD_Renderer _threeD_Chess_view;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        _threeD_Chess_view = new threeD_Renderer(this);

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