package com.game.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.game.main.Game;

public class UI {
	Game gp;
	Font arial_40, arial_80;
	Graphics2D g2;
	
	public UI (Game gp) {
		this.gp = gp;
		arial_40 = new Font ("Arial", Font.PLAIN, 40);
		arial_80 = new Font ("Arial", Font.PLAIN, 80);
	}
	
	public void draw (Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_80);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}

	}
	
	
	public void drawPauseScreen() {
		
		String text = "PAUSED";
		int x = getXforCenteredText (text);
		int y = gp.getScreenHeight()/2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXforCenteredText (String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.getScreenWidth()/2 - length/2;
		return x;
	}
}
