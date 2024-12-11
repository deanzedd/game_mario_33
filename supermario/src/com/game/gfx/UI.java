package com.game.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.text.DecimalFormat;

import com.game.main.Game;
import com.game.object.Player;

public class UI {
	Game gp;
    Font arial_40, arial_80;
    Graphics2D g2;
    private Image titleImage, titleImage2, titleImage3, healthImage;
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 = the first screen, 1 = the second screen
    public double playTime;
    public int score; // Thêm biến lưu trữ điểm
    public int health =3;
    public int firstRoundHealth =3;
    public int secondRoundHealth = 5;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public String currentDialogue = "";

    public UI(Game gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80 = new Font("Arial", Font.PLAIN, 80);

        try {
            titleImage = new javax.swing.ImageIcon(getClass().getResource("/titlescreen/newPlayer_1.png")).getImage();
            titleImage2 = new javax.swing.ImageIcon(getClass().getResource("/titlescreen/backgroundTitle.png")).getImage();
            titleImage3 = new javax.swing.ImageIcon(getClass().getResource("/titlescreen/MAMILO_ADVENTURE_2.png")).getImage();
            healthImage = new javax.swing.ImageIcon(getClass().getResource("/UI/HEART.png")).getImage();
        } catch (NullPointerException e) {
            System.err.println("Main character image not found!");
        }
    }

    public void updateScore(int points) {
        score += points; // Cộng điểm
    }
    public void updateHealth(int damage) {
        health -= damage; // Cộng điểm
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            g2.setFont(arial_40);
            g2.drawString("Time: " + dFormat.format(playTime), gp.getScreenWidth() - gp.SCREEN_OFFSET * 4, gp.SCREEN_OFFSET);
            
            drawHealth (g2);
            drawScore ();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            g2.setFont(arial_40);
            playTime += (double) 1 / 180;
            g2.drawString("Time: " + dFormat.format(playTime), gp.getScreenWidth() - gp.SCREEN_OFFSET * 4, gp.SCREEN_OFFSET);
            drawHealth (g2);
            drawScore ();
        }
        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState) {
        	g2.setFont(arial_40);
            g2.drawString("Time: " + dFormat.format(playTime), gp.getScreenWidth() - gp.SCREEN_OFFSET * 4, gp.SCREEN_OFFSET);
            
        	drawGameOverScreen ();
        	drawScore ();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
        	g2.setFont(arial_40);
            g2.drawString("Time: " + dFormat.format(playTime), gp.getScreenWidth() - gp.SCREEN_OFFSET * 4, gp.SCREEN_OFFSET);
            
        	drawDialogueScreen();
        }
    }
    public void drawDialogueScreen() {
    	
    	//WINDOW
    	int x = gp.SCREEN_OFFSET*3;
    	int y = gp.SCREEN_OFFSET/2;
    	int width = gp.getScreenWidth() - (gp.SCREEN_OFFSET*4);
    	int height = gp.SCREEN_OFFSET*5;
    	drawSubWindow (x, y, width, height);
    	
    	x+= gp.SCREEN_OFFSET;
    	y+=gp.SCREEN_OFFSET;
    	g2.drawString(currentDialogue, x, y);
    }
    
    public void drawSubWindow (int x, int y, int width, int height) {
    	
    	Color c = new Color (0,0,0,150);
    	g2.setColor(c);
    	g2.fillRoundRect(x, y, width, height, 35, 35);
    	
    	c = new Color (255,255,255,150);
    	g2.setColor(c);
    	g2.setStroke(new BasicStroke(5));
    	g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    	
    }
    
	public void drawHealth(Graphics2D g2) {
		g2.drawImage(healthImage, gp.SCREEN_OFFSET, gp.SCREEN_OFFSET + 8, gp.SCREEN_OFFSET, gp.SCREEN_OFFSET, null);
		g2.drawString("X " + health, gp.SCREEN_OFFSET * 2, gp.SCREEN_OFFSET * 2);
    
	}
	public void drawScore () {
		g2.drawString("Score: " + score , gp.SCREEN_OFFSET, gp.SCREEN_OFFSET); 
	}
	
	
	public void drawPauseScreen() {
		
		String text = "PAUSED";
		int x = getXforCenteredText (text);
		int y = gp.getScreenHeight()/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawGameOverScreen () {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.getWindowWidth(), gp.getWindowHeight());
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		String text = "YOU LOSE";
		int x = getXforCenteredText (text);
		int y=gp.SCREEN_OFFSET*6;;
		g2.drawString(text, x, y);
	}
	
	public void drawTitleScreen () {
		
		if (titleScreenState == 0) {
			//BACKGROUND COLOR
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, 0, gp.getWindowWidth(), gp.getWindowHeight());
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
			String text = "MAMILO";
			int x;
			int y=gp.SCREEN_OFFSET*2+24;;
			
			
			//MAIN CHARACTER IMAGE
			
			g2.drawImage(titleImage2, -48*2, 0, gp.getWidth()+48*4, gp.getHeight(),null);
			//g2.drawImage(titleImage, gp.SCREEN_OFFSET*8, gp.SCREEN_OFFSET*5, gp.SCREEN_OFFSET*4, gp.SCREEN_OFFSET*4,null);
			
			// GAME NAME
			g2.drawImage(titleImage3, gp.getWidth()/4-96, 0, gp.SCREEN_OFFSET*18, gp.SCREEN_OFFSET*9,null  );
			
			
			//MENU
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
			text = "New Game";
			x = getXforCenteredText (text) ;
			y += gp.SCREEN_OFFSET*7 +gp.SCREEN_OFFSET/2;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.SCREEN_OFFSET, y);
			}
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
			text = "Play Instruction";
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
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, gp.getWindowWidth(), gp.getWindowHeight());
			
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
		else if (titleScreenState == 2) {
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, gp.getWindowWidth(), gp.getWindowHeight());
			
			g2.setColor (Color.white);
			g2.setFont(g2.getFont().deriveFont(20F));
			
			int y = gp.SCREEN_OFFSET*5;
			
			String text ="Cốt truyện: Mamilo là 1 nhà truy tìm kho báu bị lạc vào một vùng đất bí ẩn,";
			int x = getXforCenteredText (text);
			g2.drawString (text, x, y);
			
			text ="cậu phải tìm mọi cách để đến được lâu đài, đánh bại boss cuối để lấy được phần thưởng";
			x = getXforCenteredText (text);
			y+=gp.SCREEN_OFFSET/2;
			g2.drawString (text, x, y);
			
			if (commandNum == 0) {
				g2.drawString(">",x-gp.SCREEN_OFFSET, y-gp.SCREEN_OFFSET/2);
			}
			
			text ="Back";
			x = getXforCenteredText (text);
			y += gp.SCREEN_OFFSET;
			g2.drawString (text, x, y);
			if (commandNum == 1) {
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