package com.game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	
	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage... args) {	// Thiet lap toc do cho animation frame
		this.speed = speed;
		images = new BufferedImage[args.length];
		for (int i = 0; i < args.length; i++) {
			images[i] = args[i];
		}
		
		frames = args.length;
		
	}
	
	public void runAnimation() {	// Ham chay animation frame
		index++;
		if (index > speed) {
			index = 0;
			nextFrame();
		}
	}
	
	private void nextFrame() {		// Ham chuyen (transit) sang animation tiep theo
		currentImg = images[count];
		count++;
		
		if (count >= frames) {
			count = 0;
		}
	}
	
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {	// Ham ve animation len man hinh
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);
		
	}
}
