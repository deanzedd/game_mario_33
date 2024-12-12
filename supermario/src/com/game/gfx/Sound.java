package com.game.gfx;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	public Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[1] = getClass().getResource("/sound/gameMusicofThrones.wav");
		soundURL[0] = getClass().getResource("/sound/titleMusic_1.wav");
		
		soundURL[2] = getClass().getResource("/sound/level2Music.wav");
		
		soundURL[3] = getClass().getResource("/sound/gameover.wav");
		soundURL[4] = getClass().getResource("/sound/coin.wav");
		soundURL[5] = getClass().getResource("/sound/marioDies.wav");
		soundURL[6] = getClass().getResource("/sound/jumpSound_1.wav");
		soundURL[7] = getClass().getResource("/sound/winning.wav");
		soundURL[8] = getClass().getResource("/sound/monsterDam.wav");
		soundURL[9] = getClass().getResource("/sound/playerKill.wav");
		
	}
	
	public void setFileforSE(int i) {
	    try {
	       
	        AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
	        clip = AudioSystem.getClip();
	        clip.open(ais);
	    } catch (Exception e) {
	        System.err.println("Error loading sound file: " + soundURL[i] + " | " + e.getMessage());
	        clip = null;
	    }
	}
	
	public void setFileforMusic(int i) {
	    try {
	        if (clip != null && clip.isRunning()) {
	            clip.stop();       // Dừng clip cũ
	            clip.close();      // Giải phóng tài nguyên clip
	        }
	        AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
	        clip = AudioSystem.getClip();
	        clip.open(ais);
	    } catch (Exception e) {
	        System.err.println("Error loading sound file: " + soundURL[i] + " | " + e.getMessage());
	        clip = null;
	    }
	}

	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
		clip.close();
	}
	public boolean isPlaying() {
	    // Kiểm tra xem âm thanh có đang phát hay không
		if (clip == null) return false;
	    return clip.isRunning();  // clip là đối tượng AudioClip hoặc tương tự
	}

}
