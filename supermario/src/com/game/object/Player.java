package com.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.game.gfx.Animation;
import com.game.gfx.Texture;
import com.game.gfx.UI;
import com.game.main.Game;
import com.game.object.util.Handler;
import com.game.object.util.ObjectId;

public class Player extends GameObject {
	private static final float WIDTH = 16;
	private static final float HEIGHT = 16;
	private Handler handler;
	private Texture tex;
	private int damageCooldown = 0; // Thời gian chờ giữa các lần mất máu

	
	private PlayerState state;
	private BufferedImage[] spriteL,spriteS;
	private Animation playerWalkL, playerWalkS;	// Hieu ung PlayerWalkLarge, PlayerWalkSmall
	private BufferedImage[] currSprite;
	private Animation currAnimation;
	
	private LinkedList<Block> removeBlocks;
	
	private boolean jumped = false;
	private boolean forward = false;
	public UI ui;
	public Game gp;
	
	public Player(float x, float y, int scale, Handler handler, UI ui, Game gp) {
		super(x,y, ObjectId.Player, WIDTH, HEIGHT, scale);
		this.handler = handler;
		this.ui=ui;
		this.gp = gp;
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
		
		// Giảm giá trị cooldown
	    if (damageCooldown > 0) {
	        damageCooldown--;
	    }
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
	    for (int i = 0; i < handler.getGameObjs().size(); i++) {
	        GameObject temp = handler.getGameObjs().get(i);

	        // Bỏ qua chính đối tượng này
	        if (temp == this) continue;

	        // Xử lý Block
	        if (temp.getId() == ObjectId.Block) {
	            Block block = (Block) temp;

	            // Bỏ qua Block có thể đi xuyên qua
	            if (block.enterable) continue;

	            // Kiểm tra va chạm từ trên
	            if (getBoundsTop().intersects(block.getBounds())) {
	                setY(block.getY() + block.getHeight());
	                setVelY(0);
	                if (block.getIndex() == 24) {
	                	gp.playSE(4);
	                    removeBlocks.add(block);
	                    block.hit();
	                    ui.updateScore(1);
	                     //  gp.gameState= gp.pauseState; // for testing    
	                }
	            }
	            // Kiểm tra va chạm từ dưới
	            if (getBounds().intersects(block.getBounds())) {
	                setY(block.getY() - getHeight());
	                setVelY(0);
	                jumped = false;
	            }

	            // Kiểm tra va chạm từ phải
	            if (getBoundsRight().intersects(block.getBounds())) {
	                setX(block.getX() - getWidth());
	                 if (block.getIndex() == 41) {
	                	 drawCastleDialogue();
	            }
	            
	            }

	            // Kiểm tra va chạm từ trái
	            if (getBoundsLeft().intersects(block.getBounds())) {
	                setX(block.getX() + block.getWidth());
	                 if (block.getIndex() == 41) {
	                	 drawCastleDialogue();
	            }
	            
	            }
	        }

	        // Xử lý Pipe
	        if (temp.getId() == ObjectId.Pipe) {
	            Pipe pipe = (Pipe) temp;

	            // Bỏ qua Pipe có thể đi xuyên qua
	            if (pipe.enterable) continue;

	            // Kiểm tra va chạm từ trên
	            if (getBoundsTop().intersects(pipe.getBounds())) {
	                setY(pipe.getY() + pipe.getHeight());
	                setVelY(0);
	            }

	            // Kiểm tra va chạm từ dưới
	            if (getBounds().intersects(pipe.getBounds())) {
	                setY(pipe.getY() - getHeight());
	                setVelY(0);
	                jumped = false;
	            }

	            // Kiểm tra va chạm từ phải
	            if (getBoundsRight().intersects(pipe.getBounds())) {
	                setX(pipe.getX() - getWidth());
	            }

	            // Kiểm tra va chạm từ trái
	            if (getBoundsLeft().intersects(pipe.getBounds())) {
	                setX(pipe.getX() + pipe.getWidth());

	            }
	        }

	        // Xử lý Goombas
	        if (temp.getId() == ObjectId.Goombas) {
	            if (getBounds().intersects(temp.getBoundsTop())) {
	                handler.removeObj(temp);
	                return;
	            }
	            if (getBoundsLeft().intersects(temp.getBoundsRight()) || 
	                getBoundsRight().intersects(temp.getBoundsLeft())) {
	                if (damageCooldown == 0) { // Kiểm tra cooldown
	                    ui.updateHealth(1);
	                    if (ui.health <=0) {
	                        gp.gameState = gp.gameOverState;
	                    }
	                    damageCooldown = 60; // Thời gian chờ (60 khung hình ~ 1 giây nếu FPS = 60)
	                }
	                return;
	            }
	        }
	    }
	}


	public void drawCastleDialogue () {
		 if (ui.score >= 12) {
         	gp.gameState = gp.dialogueState;
         	gp.ui.currentDialogue ="Congrats, now you have to fight the Boss";
         } else {
         	gp.gameState = gp.dialogueState;
         	gp.ui.currentDialogue ="You need to have 12 points";
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
