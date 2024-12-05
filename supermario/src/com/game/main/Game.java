package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

import com.game.gfx.Camera;
import com.game.gfx.Texture;
import com.game.gfx.Windows;
import com.game.main.util.LevelHandler;
import com.game.object.Block;
import com.game.object.Player;
import com.game.object.util.Handler;
import com.game.object.util.KeyInput;

public class Game extends Canvas implements Runnable {
	
	//game constants
	private static final int MILLIS_PER_SEC = 1000;
	private static final int NANOS_PER_SEC = 1000000000;
	private static final double NUM_TICKS = 60.0;
	private static final String NAME = "Super Mario Bros";
	
	private static final int WINDOW_WIDTH = 960;
	private static final int WINDOW_HEIGHT = 720;
	private static final int SCREEN_WIDTH= WINDOW_WIDTH - 67;	
	private static final int SCREEN_HEIGHT= WINDOW_HEIGHT;
	private static final int SCREEN_OFFSET = 16*3;
	
	
	// game variables
	private boolean running;
	
	
	// game components (thanh phan tro choi)
	private Thread thread; // luong
	private Handler handler; //xu li
	private Camera cam;
	private static Texture tex;
	private LevelHandler levelHandler;
	
	private Image background;
	
	public Game() {
		initialize();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();

	}
	
	private void initialize() {
		//check
		ImageIcon icon = new ImageIcon(getClass().getResource("/tile/backkk.jpg"));
		background = icon.getImage();
		
		tex = new Texture();
		
		
		
		
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		
		levelHandler = new LevelHandler(handler);
		levelHandler.start();
		
		
		cam =new Camera (0, SCREEN_OFFSET);
		new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);
		
		start();
	}
	
	private synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private synchronized void stop() {	
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = NUM_TICKS;
		double ns = NANOS_PER_SEC /amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ ns;
			lastTime = now;
			
			while(delta >=1) {
				tick();
				updates++;
				delta--;
			}
			
			if (running) {
				render();
				frames++; //update frames
			}
			
			if(System.currentTimeMillis() - timer > MILLIS_PER_SEC ) {
				timer += MILLIS_PER_SEC;
				System.out.println("FPS: " + frames + " TPS: " + updates);
				updates = 0;
				frames = 0;
			}
		}
		
		stop();
	}
	private void tick() {
		handler.tick();
		cam.tick(handler.getPlayer());
	}
	
	private void render() {
		BufferStrategy buf = this.getBufferStrategy();
		if(buf ==  null) {
			this.createBufferStrategy(3);
			return;
		}
		
		// draw graphics
		Graphics g = buf.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g; //g2d voi nhieu tinh nang hon
		
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);//fill rectangle (The left and right edges of the rectangle are at x and x + width - 1.The top and bottom edges are at y and y + height - 1)
		
		
		//check
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this); 
		
		g2d.translate (cam.getX(),cam.getY());
		handler.render(g);		
		g2d.translate (-cam.getX(),-cam.getY());
		
		g.dispose(); // lam vo hieu graphic g
		buf.show(); // render next buffer or next frame
				
	}
	
	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}
	
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}
	public static Texture getTexture() {
		return tex;
	}
	
}
