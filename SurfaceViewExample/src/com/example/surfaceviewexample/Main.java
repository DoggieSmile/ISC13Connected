package com.example.surfaceviewexample;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Main extends Activity {
	private static final String TAG = Main.class.getSimpleName();	

	static MainGamePanel gamePanel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	gamePanel = new MainGamePanel(this);
    	init(gamePanel);
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // set our MainGamePanel as the View
        setContentView(gamePanel);
        Log.d(TAG, "View added");
    }
	
    public void init(MainGamePanel gamePanel){
    	this.gamePanel = gamePanel;
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
    @Override
    protected void onPause(){
    	Log.d(TAG, "Pausing...");
    	super.onPause();
    	gamePanel.onPause();
    }
    @Override
    protected void onResume(){
    	Log.d(TAG, "Resuming...");
    	super.onResume();
    	gamePanel.onResume();
    }
    
}
