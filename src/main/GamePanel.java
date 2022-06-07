package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Screen settings
	final int originalTileSize = 16; // 16x16
	final int scale = 3;

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	// world settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	public TileManager tileM = new TileManager(this);
	int FPS = 60;

	KeyHandler keyH = new KeyHandler(this);
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;

	Thread gameThread;

	public CollisionChecker cChecker = new CollisionChecker(this);

	public Player player = new Player(this, keyH);

	public SuperObject obj[] = new SuperObject[10];

	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);

	Sound music = new Sound();
	Sound se = new Sound();

	public void setupGame() {
		aSetter.setObject();

		playMusic(0);
	}

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {

		double drawInterval = 1000000000 / FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {

			update();
			repaint();
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void update() {
		player.update();
	}

	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		if (gameState == titleState) {
			ui.draw(g2);

		}

			else {
		super.paintComponent(g);

	
		tileM.draw(g2);

		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2);
		
		ui.draw(g2);
		
		
		g2.dispose();
	}
	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
