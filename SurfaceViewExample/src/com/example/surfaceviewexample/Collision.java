package com.example.surfaceviewexample;

import android.graphics.Canvas;
import android.util.Log;

public class Collision {
	MainGamePanel gamePanel;
	private int width = 0;
	private int height = 0;
	private int x = 0;
	private int y = 0;;

	/**
	 * Iteriert die Listen stillImageList und spriteList durch und prüft, ob
	 * eine Kollision vorliegt.
	 * 
	 * @param sprite
	 *            Das Sprite, das bewegt wurde und mit dem nun ein
	 *            Kollisionscheck durchgeführt werden soll.
	 */
	 void checkCollision(Sprite sprite) {
			height = sprite.getHeight();
			width = sprite.getWidth();
			x = sprite.getX();
			y = sprite.getY();
			Log.d("Devil", "spriteListSize: "+gamePanel.getSpriteList().size());
			for (int i = 0; i < gamePanel.getSpriteList().size(); i++) {
				int x2 = gamePanel.spriteList.get(i).x;
				int y2 = gamePanel.spriteList.get(i).y;
				int width2 = gamePanel.spriteList.get(i).getWidth() / 4;
				int height2 = gamePanel.spriteList.get(i).getHeight() / 4;
				if (!gamePanel.spriteList.get(i).equals(sprite))
					collision(x2, y2, width2, height2);
			}
			for (int i = 0; i < gamePanel.stillImageList.size(); i++) {
				int x2 = (int) gamePanel.stillImageList.get(i).getX();
				int y2 = (int) gamePanel.stillImageList.get(i).getY();
				int width2 = gamePanel.stillImageList.get(i).getWidth();
				int height2 = gamePanel.stillImageList.get(i).getHeight();
				collision(x2, y2, width2, height2);
			}
			gamePanel.spriteList.get(0);
		}

		/**
		 * Prüft zuerst für jeden einzelnen Eckpunkt des Objekts aus der List, ob es
		 * innerhalb des übergebenen Sprites liegt und danach, ob einer der
		 * Eckpunkte des Sprites innerhalb des Objektes aus der Liste liegt.
		 * 
		 * @param x2
		 *            beschreibt den x-Wert des Objekts mit dem geprüft werden soll
		 * @param y2
		 *            beschreibt den y-Wert des Objekts mit dem geprüft werden soll
		 * @param height2
		 *            beschreibt die Höhe des Objektes mit dem geprüft werden soll
		 * @param width2
		 *            beschreibt die Breite des Objektes mit dem geprüft werden soll
		 * @return gibt aus, ob eine Kollision vorliegt
		 */
		private boolean collision(int x2, int y2, int height2, int width2) {

			boolean collision = false;
			collision |= x2 > x && x2 < x + width && y2 > y && y2 < y + height;
			collision |= x2 + width2 > x && x2 + width2 < x + width && y2 > y
					&& y2 < y + height;
			collision |= x2 + width2 > x && x2 + width2 < x + width
					&& y2 + height2 > y && y2 + height2 < y + height;
			collision |= x2 > x && x2 < x + width && y2 + height2 > y
					&& y2 + height2 < y + height;

			collision |= x > x2 && x < x2 + width2 && y > y2 && y < y2 + height2;
			collision |= x + width > x2 && x + width < x2 + width2 && y > y2
					&& y < y2 + height2;
			collision |= x + width > x2 && x + width < x2 + width2
					&& y + height > y2 && y + height < y2 + height2;
			collision |= x > x2 && x < x2 + width2 && y + height > y2
					&& y + height < y2 + height2;

			return collision;
		}
}
