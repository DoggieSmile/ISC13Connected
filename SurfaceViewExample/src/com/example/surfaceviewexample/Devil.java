package com.example.surfaceviewexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Devil extends Sprite{

	boolean collision = false;
	public Devil(MainGamePanel mainGamePanel, Bitmap walk, int xPos, int yPos) {
		super(mainGamePanel, walk, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		xSpeed = -2;
		ySpeed = 0;
		direction = 1;
		currentFrame = ++currentFrame % 4;
		if (!collision) {
			x += xSpeed;
			y += ySpeed;
		}
	}

}