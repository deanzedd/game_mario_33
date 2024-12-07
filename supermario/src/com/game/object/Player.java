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

public class Player extends GameObject {
	private static final float WIDTH = 16;
	private static final float HEIGHT = 16;
	private Handler handler;
	private Texture tex;
	
	private PlayerState state;
	private BufferedImage[] spriteL,spriteS;
	private Animation playerWalkL, playerWalkS;	// Hieu ung PlayerWalkLarge, PlayerWalkSmall
	private BufferedImage[] currSprite;
	private Animation currAnimation;
	
	private LinkedList<Block> removeBlocks;
	
	private boolean jumped = false;
	private int health =2; 
	private boolean forward = false;
	
	public Player(float x, float y, int scale, Handler handler) {
		super(x,y, ObjectId.Player, WIDTH, HEIGHT, scale);
		this.handler = handler;
		tex = Game.getTexture();
		removeBlocks = new LinkedList<Block>();
		
		
		
		spriteL = tex.getMarioL();
		spriteS = tex.getMarioS();
		
		playerWalkL = new Animation(5, spriteL[1], spriteL[2], spriteL[3]);	// Lay animation nhan vat voi 3 hinh SpriteLarge dau tien
		playerWalkS = new Animation(5, spriteS[1], spriteS[2], spriteS[3]);	
		
		state = PlayerState.Small;
		currSprite = spriteS;
		

		currAnimation = playerWalkS;
	}

	@Override // ghi de tu lop cha GameObject
	public void tick() {
		setX(getVelX() + getX());
		setY(getVelY() + getY());
		
		applyGravity(); // cho vật thể đi xuống tắt đi thì obj đứng yên 
		
		collision(); // update va cham
		
		currAnimation.runAnimation();
		
		
	}

	@Override
	public void render(Graphics g) {
	    if (jumped) { 	// khi nhan vat nhay
	    	if (forward) {	// kiem tra xem co phai nhay len khong
	    		g.drawImage(currSprite[5], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
	    		
	    	} else {
	    		g.drawImage(currSprite[5], (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight(), null);
	    	} 
	    } else if (getVelX() > 0) {	// khi di sang phai
	    	currAnimation.drawAnimation(g, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	    	forward = true;
	    } else if (getVelX() < 0) {	// khi di sang trai
	    	currAnimation.drawAnimation(g, (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight());
	    	forward = false;
	    } else {	// neu dung yen
	    	g.drawImage(currSprite[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
	    }
	    //showBounds(g);
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
	
	
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
				(int) (getY() + getHeight()/2),
				(int) getWidth()/2,
				(int) getHeight()/2);
	} // bản chất thì hàm này đã tạo hitbox dưới cho obj
	
	// cach tao hinh chu nhat tren 2d
	//ham Rectangle(int x, int y, int width, int height)
	// voi x,y la toa do cua dinh dau tien cua hinh chu nhat voi toaj do Oxy có gốc là đỉnh màn hình bên trái
	// Width và height lần lượt là cạnh và chiều cao 
	// ** chieu cao height tính xuống dưới 
	
	
	// ve hitbox tren object
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
	
	public LinkedList<Block> getAndResetRemoveBlock() {	// xoa bo cac block can duoc xoa (vd. manh vun debris)
		LinkedList<Block> output = new LinkedList<Block>();
		for (Block removeBlock : removeBlocks) {
			if (!removeBlock.shouldRemove()) continue;
			output.add(removeBlock);
		}
		for (Block removeBlock: output) {
			removeBlocks.remove(removeBlock);
		}
		return output;
	}
	
	
}
