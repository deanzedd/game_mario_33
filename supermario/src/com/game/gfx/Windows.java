package com.game.gfx;

import java.awt.Dimension;
import javax.swing.JFrame;
import com.game.main.Game;

public class Windows {
	private JFrame frame;
	private Dimension size;
	
	public Windows(int width, int height, String title, Game game) {
		size = new Dimension(width, height);
		frame = new JFrame(title);
		
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set frame de close chuong trinh
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		
		
		
		
		
	}
	
}
