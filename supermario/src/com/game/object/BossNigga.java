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

public class BossNigga extends GameObject {
	private static final float WIDTH = 50;//34
	private static final float HEIGHT = 50;
	private Handler handler;
	private Texture tex;
	
	
	private PlayerState state;
	private BufferedImage[] spriteL,spriteS;
	private Animation goombasWalkL, playerWalkS;	// Hieu ung goombasWalkLarge, goombasWalkSmall
	private BufferedImage[] currSprite;
	private Animation currAnimation;
	
	public BossNigga(float x, float y, int scale, Handler handler) {
		super(x,y, ObjectId.BossNigga, WIDTH, HEIGHT, scale);
		this.handler = handler;
		tex = Game.getTexture();
		
		
		
		spriteL = tex.getGoombasL();
		spriteS = tex.getBossNiggaS();
		
		//goombasWalkL = new Animation(5, spriteL[1], spriteL[2], spriteL[3]);	// Lay animation nhan vat voi 3 hinh SpriteLarge dau tien
		playerWalkS = new Animation(5, spriteS[9], spriteS[10], spriteS[11]);
		
		state = PlayerState.Small;
		currSprite = spriteS;
		

		currAnimation = playerWalkS;
		
		setVelX(-5);
		
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
	
	
	private void collision() {
		for(int i = 0; i< handler.getGameObjs().size(); i++) {
			GameObject temp = handler.getGameObjs().get(i);
			if (temp == this) continue; // neu object dang thuc hien va cham thi khong lam gi ca
			if(temp.getId()==ObjectId.Nigga || temp.getId()==ObjectId.Goombas) continue;
			
			//cho boss duoi theo minh
			if(temp.getId()==ObjectId.Player ) {
				if (temp.getX() > this.getX() + 700) {
	                setVelX(7);
	            } else if (temp.getX() < this.getX() - 700) {
	                setVelX(-7);
	            }
			}
			
			
			if (temp.getId() == ObjectId.Block && getBoundsTop().intersects(temp.getBounds())) {
				setY(temp.getY() + temp.getHeight());
				setVelY(0);
				//((Block) temp).hit();
				//removeBlocks.add((Block) temp);
			} else {	
				// xét xem có bị chạm dưới hay không
				if(getBounds().intersects(temp.getBounds())) {
					setY(temp.getY() - getHeight());
					setVelY(0);
					//jumped = false;
				}
				
				// xxét xem có bị chạm trên hay không
				if (getBoundsTop().intersects(temp.getBounds())) {
					setY(temp.getY() + temp.getHeight());
					setVelY(0);
				}
				
				//xét xem có bị chạm phải hay không
				if (getBoundsRight().intersects(temp.getBounds())) {
					setX(temp.getX() - getWidth());
					setVelX(-2);
				}
				
				
				//xét xem có bị chạm trái hay không
				if (getBoundsLeft().intersects(temp.getBounds())) {
					setX(temp.getX() + getWidth());
					setVelX(2);
				}
				
			}
			
			if (temp.getId() == ObjectId.Pipe) {
				if (getBoundsLeft().intersects(temp.getBounds())) {
					setX(temp.getX() + 2 * getWidth());    // do ống nước to vl
				}
			}
		}
	}
	

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (getVelX() > 0) {	// khi di sang phai
	    	currAnimation.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	    	//forward = true;
	    } else if (getVelX() < 0) {	// khi di sang trai
	    	currAnimation.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight());
	    	//forward = false;
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


	@Override
	public Rectangle getBoundsTop() {
		// TODO Auto-generated method stub
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
				(int) getY(),
				(int) getWidth()/2,
				(int) getHeight()/2);
	}


	@Override
	public Rectangle getBoundsLeft() {
		// TODO Auto-generated method stub
		return new Rectangle((int) getX(),
				(int) (getY() + 5),
				5,
				(int) (getHeight() - 10));
	}


	@Override
	public Rectangle getBoundsRight() {
		// TODO Auto-generated method stub
		return new Rectangle( (int) (getX() + getWidth() -5),
				(int) getY() + 5,
				5,
				(int) getHeight() - 10);
	}
	
	private void showBounds(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
			
	}

}
