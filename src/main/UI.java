package main;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import entity.Entity;

import java.awt.BasicStroke;

import object.OBJ_Key;

public class UI {
	Graphics2D g2;
	GamePanel gp;

	Font arial_40, arial_80B;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public int commandNum = 0;
	public String currentDialogue = "";
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	

	double playTime;

	public UI(GamePanel gp) {
		this.gp = gp;

		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);

	}

	public void showMessage(String text) {

		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(arial_40);
		g2.setColor(Color.white);

		

		if (gp.gameState == gp.titleState) {
			drawTitleScreen();

		}

		if (gp.gameState == gp.playState) {
			playTime += (double) 1 / 60;
			g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
		}
		if (gp.gameState == gp.pauseState) {

			drawPauseScreen();
		}

		if (gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}

	}

	public void drawDialogueScreen() {
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;

		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		x += gp.tileSize;
		y += gp.tileSize;
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}

	}

	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}

	private void drawTitleScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		String text = "Blue Boy Adventure";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y = gp.tileSize * 6;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));

		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3;
		g2.drawString(text, x, y);

		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}

	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}

	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}
}
