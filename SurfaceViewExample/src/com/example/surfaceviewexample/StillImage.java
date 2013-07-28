package com.example.surfaceviewexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class StillImage {
	private static final String TAG = MainThread.class.getSimpleName();
	
	Bitmap bmp;
	float x = 0;
	float y = 0;
	int height = 0;
	int width = 0;
	
	public StillImage(MainGamePanel mainGamePanel, Bitmap bmp){
		this.bmp = bmp;
		this.x = 500;
		this.y = 500;
		this.height = bmp.getHeight();
		this.width = bmp.getWidth();
		
	}
	
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(bmp, x-(bmp.getWidth()/2), y-(bmp.getHeight()/2), null);
	}

	public boolean solid(){
		return true;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}
	
}
