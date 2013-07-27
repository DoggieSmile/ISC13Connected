package com.example.surfaceviewexample;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {
	private static final String TAG = MainThread.class.getSimpleName();
	/**
	 * Der Timer wird genutzt, um sicherzustellen, dass der Sprite nicht bei
	 * jedem Threaddurchlauf neu gezeichnet wird. Verlangsamt die Bewegung des
	 * Sprites.
	 */
	int timer = 0;
	// direction: 0 up, 1 left, 2 down, 3 right
	// animation 0 down, 1 left, 3 up, 2 right
	int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	/**
	 * Anzahl der Reihen des Bildes.
	 */
	private static final int BMP_ROWS = 4;
	/**
	 * Anzahl der Zeilen des Bildes.
	 */
	private static final int BMP_COLOUMNS = 4;
	/**
	 * Anfangsposition des Sprites.
	 */
	int x, y;
	/**
	 * Geschwindigkeit, mit der sich der Sprite bewegen soll.
	 */
	int xSpeed, ySpeed;
	/**
	 * Höhe und Breite des Bildes.
	 */
	int height, width;
	/**
	 * Das Bild, das für das Sprite benutzt wird.
	 */
	Bitmap bmp;
	/**
	 * Das Panel, auf das das Sprite gezeichnet wird.
	 */
	MainGamePanel gamePanel;
	/**
	 * Momentane Richtung des Sprites.
	 */
	int currentFrame = 0;
	/**
	 * Anfangasrichtung des Sprites
	 */
	int direction = 2;

	// TODO anstatt hier alle objekte zu importieren, sollten objekte lieber
	// alle von objekt erben, welche eine liste von objekten hält

	// public boolean collision;

	/**
	 * Das ist der Konstruktor eines Sprites. Er wird aufgerufen, wenn ein neuer
	 * Sprite angelegt wird. Das Sprite muss so aufgebaut sein, dass die erste
	 * Reihe nach oben läuft, die zweite Reihe nach links, die dirtte nach unten
	 * und die vierte nach rechts. Außerdem müssen wir jede Richtung 4
	 * Einzelbilder vorhanden sein.
	 * 
	 * @param mainGamePanel
	 *            Auf dem Panel wird der Sprite gezeichnet
	 * @param walk
	 *            Das Bild, das eingelesen wird
	 * @param xPos
	 *            Die Start-x-Position des Sprites
	 * @param yPos
	 *            Die Start-y-Position des Sprites
	 */
	public Sprite(MainGamePanel mainGamePanel, Bitmap walk, int xPos, int yPos) {
		// TODO Auto-generated constructor stub

		this.bmp = walk;
		this.height = bmp.getHeight() / BMP_ROWS;
		this.width = bmp.getWidth() / BMP_COLOUMNS;
		this.x = xPos;
		this.y = yPos;
		// this.xSpeed = 5;
		// this.ySpeed = 0;
		Random rnd = new Random(System.currentTimeMillis());
		xSpeed = rnd.nextInt(50) - 5;

		ySpeed = rnd.nextInt(50) - 5;
	}

	/**
	 * Das ist die Methode, die für das Zeichnen zuständig ist. Hier wird
	 * spezifiziert welcher Teil des Sprites gezeichnet werden soll und und die
	 * update-Funktion aufgerufen, um die neue Position auszugeben.
	 * 
	 * @param canvas
	 *            Canvas ist der Canvas, auf dem gezeichnet wird
	 */
	public void doDraw(Canvas canvas) {
		if (timer <4) {
			timer += 1;
		} else {
			update();
			timer = 0;
		}
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;

		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		// size of sprite (sizeable)
		Rect dst = new Rect(x, y, x + width, y + height); // x: Ecke oben
															// links;
															// x+width: oben
															// rechts; y:
															// unten
															// rechts;
															// y+width:
															// unten links
		canvas.drawBitmap(bmp, src, dst, null);

	}

	/**
	 * Die update-Methode kann nicht von außen aufgerufen werden, weil sie nur
	 * für Draw wichtig ist. Sie spezifiziert, wohin sich der Sprite bewegt.
	 */
	private void update() {

		System.out.println("bin in update");

		// 0 = down
		// 1 = left
		// 2 = right
		// 3 = up
		// facing down
		if (x > gamePanel.screenWidth - width - xSpeed) {
			xSpeed = 0;
			ySpeed = 2;
			direction = 0;
			// going left
		}
		if (y > gamePanel.screenHeight - height - ySpeed) {
			xSpeed = -2;
			ySpeed = 0;
			direction = 1;
		}
		// facing up
		if (x + xSpeed < 0) {
			x = 0;
			xSpeed = 0;
			ySpeed = -2;
			direction = 3;
		}// facing right
		if (y + ySpeed < 0) {
			y = 0;
			xSpeed = 2;
			ySpeed = 0;
			direction = 2;
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentFrame = ++currentFrame % 4;
		// if (!collision(xSpeed, ySpeed)) {
		x += xSpeed;
		y += ySpeed;
		// }
	}

	/**
	 * Wird für die Kollsionsabfrage benutzt und prüft, ob die Box, um das
	 * Sprite innerhalb der Variablen x2 und y2 liegt.
	 * 
	 * @param x2
	 *            die x-Koordinate, die geprüft wird
	 * @param y2
	 *            die y-Koordinate, die geprüft wird
	 * @return Wahr, wenn eine Kollision stattgefunden hat, falsch sonst
	 */
	public boolean collision(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

	}

	public boolean solid() {
		return true;
	}

	/**
	 * Bestimmt, welche Spalte des Sprites gerade angezeigt werden soll.
	 * 
	 * @return Gibt die Spaltennummer zurück.
	 */
	private int getAnimationRow() {

		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);

		int direction = (int) Math.round(dirDouble) % BMP_ROWS;

		return DIRECTION_TO_ANIMATION_MAP[direction];

	}
}
