package com.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.object.util.ObjectId;

public class Block extends GameObject {
	
	private Texture tex = Game.getTexture();
	private int index;
	private BufferedImage[] sprite;
	
	private boolean hit;
	private Debris debris;
	
	
	
	public Block(int x, int y, int width, int height, int index, int scale) {
		super(x, y, ObjectId.Block, width, height, scale);
		this.index= index;
		sprite = tex.getTile1();
	}

	@Override
	public void tick() {
		if (hit) {
			debris.tick();
		}
	}
	
	public boolean shouldRemove() {		// Kiem tra xem phan tu co duoc cho bien mat khong
		if (debris.shouldRemove()) return true;
		return false;
	}

	@Override
	public void render(Graphics g) {
		if (!hit) {
			g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
		} else {
			debris.draw(g);
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	}
	
	public void hit() {	// Khi main char cham vao cac block, tao ra cac manh vun debris
		hit = true;
		debris = new Debris(getX(), getY(), getWidth(), getHeight(), getScale());
		
	}
	
	
	
}
