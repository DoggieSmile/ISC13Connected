package com.example.surfaceviewexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	Collision collision;
	Devil devild;
	Circle circleC;

	List<StillImage> stillImageList = new ArrayList<StillImage>();

	/**
	 * Eine Liste von Sprites.
	 */
	List<Sprite> spriteList = new ArrayList<Sprite>();
	// hopefully is the same everywhere...
	/**
	 * Die Bildschirmh�he, die definiert wird, wenn das SpielPanel zum ersten
	 * Mal aufgerufen wird.
	 */
	static int screenHeight;
	/**
	 * Die Bildschirmbreite, die definiert wird, wenn das SpielPanel zum ersten
	 * Mal aufgerufen wird.
	 */
	static int screenWidth;
	/**
	 * Bilder, die ben�tigt werden; werden in loadImage geladen.
	 */
	Bitmap kreis, walk, devil;
	/**
	 * DebugTag
	 */
	private static final String TAG = MainThread.class.getSimpleName();
	/**
	 * Kreise, die ben�tigt werden.
	 */
	StillImage circle;
	/**
	 * Das Spriteobjekt.
	 */
	Sprite sprite, devilsprite;
	/**
	 * Der Spielthread.
	 */
	private MainThread gameThread;

	SurfaceHolder surfaceHolder;

	/**
	 * L�dt alle Bilder und Objekte, die f�r das Spiel n�tig sind.
	 * 
	 * @return
	 */
	public boolean loadImages() {
		kreis = BitmapFactory.decodeResource(getResources(), R.drawable.kreis);
		circleC = new Circle(MainGamePanel.this, kreis);
		walk = BitmapFactory.decodeResource(getResources(),
				R.drawable.death_scythe);
		devil = BitmapFactory.decodeResource(getResources(), R.drawable.devil);
		devild = new Devil(MainGamePanel.this, devil, 100, 100);
		stillImageList.add(circleC);
		spriteList.add(devild);
		sprite = new Sprite(MainGamePanel.this, walk, 50, 50);
		spriteList.add(sprite);
		devilsprite = new Sprite(MainGamePanel.this, devil, 200, 200);
		spriteList.add(devilsprite);

		// BodyDef groundBodyDef = null;
		// groundBodyDef.position.set(screenWidth, screenHeight);
		return true;
	}

	/**
	 * Definiert das SpielPanel mit ben�tigten Attributen.
	 * 
	 * @param context
	 */
	public MainGamePanel(Context context) {

		super(context);
		// sets GamePanel as the handler for events
		getHolder().addCallback(this);

		// make Panel focusable to handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * Wird aufgerufen, wenn das SpielPanel zum ersten Mal geladen wird. Der
	 * Thread wird gestartet, die Bildschirmh�he und Breite ausgelesen und die
	 * Bilder eingeladen
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// create game loop thread
		// gameThread = new MainThread(getHolder(), this);
		screenHeight = this.getHeight();
		screenWidth = this.getWidth();
		// gameThread.setRunning(true);
		// gameThread.start();
		startGameThread(getHolder(), this);
		loadImages();
	}

	/**
	 * When the surface is destroyed, the thread will be stopped
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// boolean retry = true;
		// while (retry) {
		// try {
		// thread.join();
		// retry = false;
		// } catch (InterruptedException e) {
		// // try aggain shutting down the thread
		// }
		// }
		// Log.d(TAG, "Thread was shut down cleanly");
	}

	/**
	 * Definiert die TouchEvents.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = 0;
		float y = 0;
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			x = event.getX();
			y = event.getY();

			break;
		case MotionEvent.ACTION_DOWN:
			// synchronized(getHolder()){
			x = event.getX();
			y = event.getY();
			for (int i = 0; i < spriteList.size(); i++) {
				if (spriteList.get(i).collision(x, y)) {
					spriteList.remove(i);
					break;
				}
			}
			// }
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			break;
		}
		circleC.setX(x);
		circleC.setY(y);
		return true;

	}

	/**
	 * Zeichnet die Objekte auf den Bildschirm.
	 * 
	 * @param canvas
	 *            Der Ort, an dem gezeichnet wird.
	 */
	protected void doDraw(Canvas canvas) {
		if (!spriteList.isEmpty()){
			if(checkCollision(devilsprite)){
				devilsprite.testcollision = true;
			}else{
				devilsprite.testcollision = false;
			}
		}
		canvas.drawARGB(255, 150, 150, 10);

		for (int i = 0; i < spriteList.size(); i++) {
			spriteList.get(i).doDraw(canvas);
		}
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.RED);
		for (int i = 0; i < stillImageList.size(); i++) {
			stillImageList.get(i).doDraw(canvas);
		}
		// circleC.doDraw(canvas);
		for (int i = 1; i < 5; i++)
			canvas.drawCircle(i * 100, 50, 50, p);

	}

	/**
	 * Wird aufgerufen, wenn das Spiel in den Hintergrund geholt wird, um andere
	 * Apps zu starten. Schlie�t den Thread.
	 */
	public void onPause() {
		// kill bg Thread
		boolean retry = true;
		gameThread.setRunning(false);

		while (retry) {
			try {
				gameThread.join();
				if (gameThread.getState() == Thread.State.TERMINATED) {
					Log.d(TAG, "Aktueller ThreadState: "
							+ gameThread.getState().toString());
					retry = false;
					gameThread = null;
					spriteList.clear();
					stillImageList.clear();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// TODO: just for testing
	public float getcircleX() {
		// float f = circle.getX();
		float f = 0;
		Log.d(TAG, "circlef: " + f);
		return f;
	}

	/**
	 * Wird aufgerufen, wenn die App wieder aus dem Hintergrund hervorgeholt
	 * wird. Es wird ein neuer Thread gestartet. Das Spiel beginnt also von
	 * vorne.
	 */
	public void onResume() {
		Log.d(TAG, "onResume GameThread?: " + (gameThread == null));
		if (gameThread != null) {
			Log.d(TAG, "onResume GameThreadState: "
					+ gameThread.getState().toString());
		}
		surfaceHolder = getHolder();
		getHolder().addCallback(this);
		// Create and start bg Thread
		try {
			startGameThread();
		} catch (Exception e) {
			Log.e(TAG, "StartGame Exception!");
		}
		Log.d(TAG, "TryBlock aus Resume ueberstanden!");
	}

	public void startGameThread() {
		if (gameThread == null) {
			Log.d(TAG, "Erstelle neuen GameThread");
			gameThread = new MainThread(this.surfaceHolder, this);
			gameThread.setRunning(true);
			gameThread.start();
			Log.d(TAG, "Neuer GameThread erstellt");
			// } else if (!gameThread.getRunning()) {
			// Log.d(TAG, "Setze GameThread auf Running und starte");
			// gameThread.setRunning(true);
			// gameThread.start();
			// Log.d(TAG, "Gamethread auf Running + Start");
		} else if (gameThread.getState() == Thread.State.TERMINATED) {
			Log.d(TAG, "Terminierten GameThread vernichten");
			gameThread = null;
			gameThread = new MainThread(this.surfaceHolder, this);
			gameThread.setRunning(true);
			gameThread.start();
			Log.d(TAG, "Vernichteter Thread wurde wiedergeboren");
		}
	}

	public void startGameThread(SurfaceHolder holder, MainGamePanel panel) {
		if (gameThread == null) {
			Log.d(TAG, "Erstelle neuen GameThread");
			gameThread = new MainThread(this.surfaceHolder, panel);
			gameThread.setRunning(true);
			gameThread.start();
			Log.d(TAG, "Neuer GameThread erstellt");
			// } else if (!gameThread.getRunning()) {
			// Log.d(TAG, "Setze GameThread auf Running und starte");
			// gameThread.setRunning(true);
			// gameThread.start();
			// Log.d(TAG, "Gamethread auf Running und Start gesetzt");
		} else if (gameThread.getState() == Thread.State.TERMINATED) {
			Log.d(TAG, "Terminierten GameThread vernichten");
			gameThread = null;
			gameThread = new MainThread(this.surfaceHolder, panel);
			gameThread.setRunning(true);
			gameThread.start();
			Log.d(TAG, "Vernichteter Thread wurde wiedergeboren");
		}
	}

	public List<StillImage> getStillImageList() {
		return stillImageList;
	}

	public void setStillImageList(List<StillImage> stillImageList) {
		this.stillImageList = stillImageList;
	}

	public List<Sprite> getSpriteList() {
		return spriteList;
	}

	public void setSpriteList(List<Sprite> spriteList) {
		this.spriteList = spriteList;
	}
	
	
	
	private int width = 0;
	private int height = 0;
	private int x = 0;
	private int y = 0;;
	 boolean checkCollision(Sprite sprite) {
			height = sprite.getHeight();
			width = sprite.getWidth();
			x = sprite.getX();
			y = sprite.getY();
//			Log.d("Devil", "spriteListSize: "+spriteList.size());
			for (int i = 0; i < spriteList.size(); i++) {
				int x2 = spriteList.get(i).x;
				int y2 = spriteList.get(i).y;
				int width2 = spriteList.get(i).getWidth() ;
				int height2 = spriteList.get(i).getHeight();
				if (!spriteList.get(i).equals(sprite))
					if(collision(x2, y2, width2, height2))
						return true;
			}
			for (int i = 0; i < stillImageList.size(); i++) {
				int x2 = (int) stillImageList.get(i).getX();
				int y2 = (int) stillImageList.get(i).getY();
				int width2 = stillImageList.get(i).getWidth();
				int height2 = stillImageList.get(i).getHeight();
				if(collision(x2, y2, width2, height2)){
					return true;
				}
			}
			return false;
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
			Log.d("Collision", "Collision: "+collision);
			return collision;
		}
}
