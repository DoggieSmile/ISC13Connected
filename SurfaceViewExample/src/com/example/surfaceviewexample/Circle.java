package com.example.surfaceviewexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Circle extends Object {
	private static final String TAG = MainThread.class.getSimpleName();
	
	Bitmap kreis;
	float x = 0;
	float y = 0;
	
	public Circle(MainGamePanel mainGamePanel, Bitmap kreis){
		this.kreis = kreis;
		this.x = 0;
		this.y = 0;
		
	}
	
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(kreis, x-(kreis.getWidth()/2), y-(kreis.getHeight()/2), null);
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
	
}
