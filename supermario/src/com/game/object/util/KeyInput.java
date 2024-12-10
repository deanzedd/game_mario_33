package com.game.object.util;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.main.Game;


//extending KeyAdapter allows the extending class (Keyinput) to become a KeyEvent Listener 
// A KeyEvent Listener takes action when certain events take place (keyPress, keyRelease)
public class KeyInput extends KeyAdapter {

	
	private boolean[] keyDown = new boolean[4];
	private Handler handler;
	Game gp;
	
	//for checking
	
	public KeyInput(Handler handler, Game gp) {
		this.handler = handler;
		this.gp =gp;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//TITLE STATE
		if (gp.gameState == gp.titleState) {
			if (gp.ui.titleScreenState == 0) {
				if (key == KeyEvent.VK_W) {
					gp.ui.commandNum --;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum =2;
					}
				}
				if (key == KeyEvent.VK_S) {
					gp.ui.commandNum ++;
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum =0;
					}
				}
				if (key == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum ==0 ) {
						gp.ui.titleScreenState = 1;
					}
					else if (gp.ui.commandNum == 1) {
						gp.ui.titleScreenState = 2;
					}
					else if (gp.ui.commandNum == 2) {
						System.exit(0);
					}
				}
			}
			
			else if (gp.ui.titleScreenState == 1) {
				if (key == KeyEvent.VK_W) {
					gp.ui.commandNum --;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum =3;
					}
				}
				if (key == KeyEvent.VK_S) {
					gp.ui.commandNum ++;
					if (gp.ui.commandNum > 3) {
						gp.ui.commandNum =0;
					}
				}
				if (key == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum ==0 ) {
						gp.gameState =gp.playState;
						gp.stopMusic();
						gp.playMusic(1);         //se add music sau
					}
					else if (gp.ui.commandNum == 1) {
						gp.gameState =gp.playState;
						gp.stopMusic();
						gp.playMusic(1);         //se add music sau
					}
					else if (gp.ui.commandNum == 2) {
						gp.gameState =gp.playState;
						gp.stopMusic();
						gp.playMusic(1);         //se add music sau
					}
					else if (gp.ui.commandNum == 3) {
						gp.ui.titleScreenState=0;
					}
				}
			}
			else if (gp.ui.titleScreenState == 2) {
				if (key == KeyEvent.VK_W) {
					gp.ui.commandNum --;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum =1;
					}
				}
				if (key == KeyEvent.VK_S) {
					gp.ui.commandNum ++;
					if (gp.ui.commandNum > 1) {
						gp.ui.commandNum =0;
					}
				}
				if (key == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum ==0 ) {
						
					}
					else if (gp.ui.commandNum == 1) {
						gp.ui.titleScreenState =0;  
					}
				}
				
			}
				
		}
		
		
		
		if(key == KeyEvent.VK_ESCAPE) { //VK == virtual key
			System.exit(0);
		}
		
		if(key == KeyEvent.VK_P) {
			if (gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			} else  if (gp.gameState == gp.pauseState) {
				gp.gameState =gp.playState;
			}
		}
		
		// ý tưởng là khi thực hiện nhập từ bàn phím dịch chuyển tọa độ của obj
		// để ý tick method ở trước. ta chỉ cần update player velocity 1 cách tự động với tick 
		
		
		// jump
		if (key == KeyEvent.VK_W) {
			if(!handler.getPlayer().hasJumped()) {
				handler.getPlayer().setVelY(-15);
				keyDown[0] = true;
				handler.getPlayer().setJumped(true);
				if (gp.gameState == gp.playState ) {
					gp.playSE(2);
				}
			}
		}
		
		// move left
		if (key == KeyEvent.VK_A) {
			handler.getPlayer().setVelX(-8);
			keyDown[1] = true;
		}
		
		// move right
		if (key == KeyEvent.VK_D) {
			handler.getPlayer().setVelX(8);
			keyDown[2] = true;
		}	
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			keyDown[0] = false;
		}
		
		if (key == KeyEvent.VK_A) {
			keyDown[1] = false;
		}
		
		if (key == KeyEvent.VK_D) {
			keyDown[2] = false;
		}
		
		if (!keyDown[1] && !keyDown[2]) {
			handler.getPlayer().setVelX(0);
		}
	}
	
}

