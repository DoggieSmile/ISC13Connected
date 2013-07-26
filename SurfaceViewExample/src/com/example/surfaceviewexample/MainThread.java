package com.example.surfaceviewexample;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();
	// flag to hold game state
	/**
	 * Aktueller Spielstatus
	 */
	private boolean running;
	/**
	 * Das Spielpanel.
	 */
	static MainGamePanel gamePanel;
	private SurfaceHolder surfaceHolder;
	/**
	 * Der Thread
	 */
	Thread t = null;
	/**
	 * Konstruktor f�r den Thread. 
	 * @param surfaceHolder
	 * @param gamePanel
	 */
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;

	}
	/**
	 * Bestimmt den Status von running.
	 * @param running bestimmt den Status des Threads.
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Gibt aktuellen Status von running
	 */
	public boolean getRunning() {
		return this.running;
	}

	/**
	 * Wird ausgef�hrt w�hrend der Thread aktiv ist.
	 */
	@Override
	public void run() {
//		if(gamePanel.loadImages()){
//			Log.d("test", "zeug laden");
//		}
		
		long tickCount = 0L;
		Log.d(TAG, "Starting game loop");
		
		while (running) {
			try{
				sleep(25);
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			Canvas canvas = null;
			if (!surfaceHolder.getSurface().isValid()) {
				continue;
			}
			try{
			tickCount++;
			canvas = surfaceHolder.lockCanvas();
			synchronized(gamePanel.getHolder()){
				if (canvas != null) {
					gamePanel.doDraw(canvas);
				}
			}
			}finally{
				if(canvas!=null){
				surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			// update game state
			// render state to the screen
			Log.d(TAG, "Game loop executed " + tickCount + " times");
		}
	}
}
