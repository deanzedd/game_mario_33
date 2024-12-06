package com.game.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import com.game.main.Game;

public class UI {
	Game gp;
	Font arial_40, arial_80;
	Graphics2D g2;
	private Image titleImage;
	public int commandNum =0;
	public int titleScreenState = 0; //0= the first screen, 1= the second screen
	
	public UI (Game gp) {
		this.gp = gp;
		arial_40 = new Font ("Arial", Font.PLAIN, 40);
		arial_80 = new Font ("Arial", Font.PLAIN, 80);
		
		try {
            titleImage = new javax.swing.ImageIcon(getClass().getResource("/titlescreen/newPlayer_1.png")).getImage();
        } catch (NullPointerException e) {
            System.err.println("Main character image not found!");
        }
	}
	
	public void draw (Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_80);
		g2.setColor(Color.white);
		//TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		//PAUSE STATE
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
	
	public void drawTitleScreen () {
		
		if (titleScreenState == 0) {
			//BACKGROUND COLOR
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, 0, gp.getWindowWidth(), gp.getWindowHeight());
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
			String text = "Huster Adventure";
			int x = getXforCenteredText (text) ;
			int y = gp.SCREEN_OFFSET*3;
			
			//SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+4, y+4);
			//GAME NAME COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//MAIN CHARACTER IMAGE
			x = gp.getWindowWidth()/2- gp.SCREEN_OFFSET*3 +30;
			y = gp.SCREEN_OFFSET*5;
			g2.drawImage(titleImage, x, y, gp.SCREEN_OFFSET*4, gp.SCREEN_OFFSET*4,null);
			
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
			text = "New Game";
			x = getXforCenteredText (text) ;
			y += gp.SCREEN_OFFSET*5;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.SCREEN_OFFSET, y);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
			text = "Load Game";
			x = getXforCenteredText (text) ;
			y += gp.SCREEN_OFFSET;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x-gp.SCREEN_OFFSET, y);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
			text = "Quit";
			x = getXforCenteredText (text) ;
			y += gp.SCREEN_OFFSET;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x-gp.SCREEN_OFFSET, y);
			}
		}
		
		else if (titleScreenState == 1) {
			//CLASS SELECTION SCREEN
			g2.setColor (Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select your class";
			int x = getXforCenteredText (text);
			int y = gp.SCREEN_OFFSET*5;
			g2.drawString (text, x, y);
			
			text ="Mario Normal";
			x = getXforCenteredText (text);
			y += gp.SCREEN_OFFSET*3;
			g2.drawString (text, x, y);
			if (commandNum == 0) {
				g2.drawString(">",x-gp.SCREEN_OFFSET, y);
			}
			
			text ="Mario Black";
			x = getXforCenteredText (text);
			y += gp.SCREEN_OFFSET;
			g2.drawString (text, x, y);
			if (commandNum == 1) {
				g2.drawString(">",x-gp.SCREEN_OFFSET, y);
			}
			
			text ="Mario White";
			x = getXforCenteredText (text);
			y += gp.SCREEN_OFFSET;
			g2.drawString (text, x, y);
			if (commandNum == 2) {
				g2.drawString(">",x-gp.SCREEN_OFFSET, y);
			}
			
			text ="Back";
			x = getXforCenteredText (text);
			y += gp.SCREEN_OFFSET;
			g2.drawString (text, x, y);
			if (commandNum == 3) {
				g2.drawString(">",x-gp.SCREEN_OFFSET, y);
			}
		}
		
		
	}
	
	public int getXforCenteredText (String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.getScreenWidth()/2 - length/2;
		return x+ gp.SCREEN_OFFSET/2;
	}
}
