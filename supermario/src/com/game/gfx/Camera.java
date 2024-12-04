package com.game.gfx;

import com.game.main.Game;
import com.game.object.GameObject;

public class Camera {
	private int x,y;
	
	public Camera (int x, int y) {
		this.x=x;
		this.y=y;
	}
	public void tick (GameObject player) {
		x = (int) (-player.getX() + Game.getScreenWidth()/2);
	}
	public void setX (int x) {
		this.x =x;
	}
	public void setY (int y) {
		this.y =y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
