package com.google.puzzleduck.threeD_Chess;

import android.app.Activity;
import android.os.Bundle;

public class threeD_Chess extends Activity {
    
	private static final String LOG_TAG = threeD_Chess.class.getSimpleName();
	private threeD_Chess_view _threeD_Chess_view;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        _threeD_Chess_view = new threeD_Chess_view(this);
        setContentView(_threeD_Chess_view);
        
        
        //setContentView(R.layout.main);
    }
}