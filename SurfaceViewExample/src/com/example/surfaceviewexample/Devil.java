package com.example.surfaceviewexample;

import android.graphics.Bitmap;

public class Devil extends Sprite {

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
		if (!collision(xSpeed, ySpeed)) {
			x += xSpeed;
			y += ySpeed;
		}
	}
}