package com.example.surfaceviewexample;

public class Collision {

	private int width;
	private int height;
	private  int x;
	private int y;

	public boolean collision(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

	}
	
}
