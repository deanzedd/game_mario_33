package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

import com.game.gfx.Camera;
import com.game.gfx.Sound;
import com.game.gfx.Texture;
import com.game.gfx.UI;
import com.game.gfx.Windows;
import com.game.main.util.LevelHandler;
import com.game.object.Block;
import com.game.object.Player;
import com.game.object.util.Handler;
import com.game.object.util.KeyInput;

public class Game extends Canvas implements Runnable {

    // Game constants
    private static final int MILLIS_PER_SEC = 1000;
    private static final int NANOS_PER_SEC = 1000000000;
    private static final double NUM_TICKS = 60.0;
    private static final String NAME = "Super Mario Bros";

    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;
    private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
    private static final int SCREEN_HEIGHT = WINDOW_HEIGHT;
    public  final int SCREEN_OFFSET = 16 * 3;

    // Game variables
    private boolean running;

    // Game components
    private Thread thread;
    private Handler handler;
    private Camera cam;
    private static Texture tex;
    private LevelHandler levelHandler;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    
    //UI
    public UI ui = new UI(this);
    private Image background;
    
    //SOUND
    Sound sound = new Sound();
    
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
    }

    private void initialize() {
        playMusic(0);
    	try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/tile/BACK2.jpg"));
            background = icon.getImage();
        } catch (NullPointerException e) {
            System.err.println("Background image not found!");
            return;
        }

        tex = new Texture();

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler, this));

        levelHandler = new LevelHandler(handler);
        levelHandler.start();

        cam = new Camera(0, SCREEN_OFFSET);
        new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);

        gameState = titleState;

        start();
    }

    private synchronized void start() {
        if (running) return; // Prevent multiple threads
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
        try {
            running = false;
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = NANOS_PER_SEC / NUM_TICKS;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > MILLIS_PER_SEC) {
                timer += MILLIS_PER_SEC;
                System.out.println("FPS: " + frames + " TPS: " + updates);
                updates = 0;
                frames = 0;
            }
        }

        stop();
    }

    private void tick() {
        if (gameState == playState) {
            handler.tick();
            cam.tick(handler.getPlayer());
        }
    }

    private void render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        // Xóa màn hình trước khi vẽ
        g.setColor(Color.DARK_GRAY); // Hoặc màu nền mặc định
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameState == titleState) {
            ui.draw(g2d);
        } else {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

            g2d.translate(cam.getX(), cam.getY());
            handler.render(g);
            g2d.translate(-cam.getX(), -cam.getY());

            ui.draw(g2d); // UI
        }

        g2d.dispose();
        buf.show();
    }
    
    public void playMusic(int i) {
    	sound.setFile(i);
    	sound.play();
    	sound.loop();
    }
    public void stopMusic() {
    	sound.stop();
    }
    public void playSE(int i) {
    	sound.setFile(i);
    	sound.play();
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

