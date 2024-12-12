package com.game.gfx;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/titleMusic_1.wav");
		soundURL[1] = getClass().getResource("/sound/gameMusic_1.wav");
		soundURL[2] = getClass().getResource("/sound/jumpSound_1.wav");
		soundURL[3] = getClass().getResource("/sound/gameover.wav");
		soundURL[4] = getClass().getResource("/sound/coin_amplified.wav");
		soundURL[5] = getClass().getResource("/sound/marioDies.wav");
		soundURL[6] = getClass().getResource("/sound/level2Music.wav");
		soundURL[7] = getClass().getResource("/sound/winning.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
			
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
	}
	public boolean isPlaying() {
	    // Kiểm tra xem âm thanh có đang phát hay không
	    return clip.isRunning();  // clip là đối tượng AudioClip hoặc tương tự
	}

}
