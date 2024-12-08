package com.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.game.gfx.Animation;
import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.object.util.Handler;
import com.game.object.util.ObjectId;



public class Goombas extends GameObject {
	private static final float WIDTH = 16;
	private static final float HEIGHT = 16;
	private Handler handler;
	private Texture tex;
	
	//
	private boolean jumped = false;
	private LinkedList<Block> removeBlocks;
	//
	
	private PlayerState state;
	private BufferedImage[] spriteL,spriteS;
	private Animation GoombasWalkL;	// Hieu ung PlayerWalkLarge, PlayerWalkSmall
	private BufferedImage[] currSprite;
	private Animation currAnimation;
	
	
	
	
	
	
	
	
	//
	public Goombas(float x, float y, int scale, Handler handler) {
		super(x,y, ObjectId.Player, WIDTH, HEIGHT, scale);
		this.handler = handler;
		tex = Game.getTexture();
		
		//
		removeBlocks = new LinkedList<Block>();
		
		
		spriteL = tex.getGoombasL();
		
		GoombasWalkL = new Animation(5, spriteL[1], spriteL[2]);
		
		currAnimation = GoombasWalkL;
		
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		setX(getVelX() + getX());
		setY(getVelY() + getY());
		
		applyGravity(); // cho vật thể đi xuống tắt đi thì obj đứng yên 
		
		collision(); // update va cham
		
		currAnimation.runAnimation();
		
	}
	//
	//
	//
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		
		if (getVelX() > 0) {	// khi di sang phai
			currAnimation.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    	
		} else if (getVelX() < 0) {	// khi di sang trai
			currAnimation.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight());
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
				(int) (getY() + getHeight()/2),
				(int) getWidth()/2,
				(int) getHeight()/2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private void collision() {
		for(int i = 0; i< handler.getGameObjs().size(); i++) {
			GameObject temp = handler.getGameObjs().get(i);
			if (temp == this) continue; // neu object dang thuc hien va cham thi khong lam gi ca
			if (removeBlocks.contains(temp)) continue;
			
			
			if (temp.getId() == ObjectId.Block && getBoundsTop().intersects(temp.getBounds())) {
				setY(temp.getY() + temp.getHeight());
				setVelY(0);
				((Block) temp).hit();
				removeBlocks.add((Block) temp);
			} else {	
				// xét xem có bị chạm dưới hay không
				if(getBounds().intersects(temp.getBounds())) {
					setY(temp.getY() - getHeight());
					setVelY(0);
					jumped = false;
				}
				
				// xxét xem có bị chạm trên hay không
				if (getBoundsTop().intersects(temp.getBounds())) {
					setY(temp.getY() + temp.getHeight());
					setVelY(0);
				}
				
				//xét xem có bị chạm phải hay không
				if (getBoundsRight().intersects(temp.getBounds())) {
					setX(temp.getX() - getWidth());
				}
				
				
				//xét xem có bị chạm trái hay không
				if (getBoundsLeft().intersects(temp.getBounds())) {
					setX(temp.getX() + getWidth());
				}
				
			}
			
			if (temp.getId() == ObjectId.Pipe) {
				if (getBoundsLeft().intersects(temp.getBounds())) {
					setX(temp.getX() + 2 * getWidth());    // do ống nước to vl
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	//check
	public Rectangle getBoundsTop() {
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
				(int) getY(),
				(int) getWidth()/2,
				(int) getHeight()/2);
	}
	
	// tao hitbox hinh chu nhat ben phai obj
	public Rectangle getBoundsRight() {
		return new Rectangle( (int) (getX() + getWidth() -5),
				(int) getY() + 5,
				5,
				(int) getHeight() - 10);
	}
	
	// tao hitbox hinh chu nhat ben trai obj
	public Rectangle getBoundsLeft() {
		return new Rectangle((int) getX(),
				(int) (getY() + 5),
				5,
				(int) (getHeight() - 10));
	}
	
	
	
	
	
	
	
	private void showBounds(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
			
	}
	
	public boolean hasJumped() {
		return jumped;
	}
	
	public void setJumped(boolean hasJumped) {
		jumped = hasJumped;
	}
	//check
	
	
	
	
	
}
