package com.game.main.util; //HELP US IMPORT OUR FILE INTO DIFFERENT GAME OBJECTS

import java.awt.image.BufferedImage;

import com.game.gfx.BufferedImageLoader;
import com.game.object.Block;
import com.game.object.Pipe;
import com.game.object.Player;
import com.game.object.StaticBlock;
import com.game.object.util.Handler;


public class LevelHandler {
	private final String PARENT_FOLDER = "/level";
	
	private BufferedImageLoader loader;
	
	private BufferedImage levelTiles;
	private Handler handler;
    
	
	public LevelHandler (Handler handler) {
		this.handler = handler;
		loader = new BufferedImageLoader();
		
	}
	public void start() {
		setLevel(PARENT_FOLDER + "/map1.png");
		loadCharacters (PARENT_FOLDER + "/map2.png");
	}
	public void setLevel(String levelTilesPath) {
		this.levelTiles = loader.loadImage(levelTilesPath);
		
		int width = levelTiles.getWidth();
		int height = levelTiles.getHeight();
		
		for (int i=0; i < height;i++) {
			for (int j=0;j<width;j++) {
				//                             alpha      red        green      blue
				// getRGB() return int 4 bytes 0000 0000, 0000 0000, 0000 0000, 0000 0001
				//                                                              1111 1111
				//                                                                      &
				//                                                              0000 0001
				
				int pixel = levelTiles.getRGB(i, j);
				int red = (pixel >> 16)& 0xff;
				int green = (pixel >> 8)& 0xff;
				int blue = pixel & 0xff;
				
				if (red == 255 && green==255 && blue==255) continue;
				
				
				//Block
				if (red== green && red== blue) {
					handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,0 ,3 ));
				} 
				//Block cố định
				else if (red == 80 && green == 81 && blue == 82) { 
		            handler.addObj(new StaticBlock(i * 16, j * 16, 16, 16, 0, 3));
				}
				
				//Cột
				else if (blue==48 && green==111 && red==25) {
					handler.addObj(new Pipe(i*16 ,j*16 ,32 ,16 ,0 ,3 ,false )); //sprite index 0:đỉnh cột
				} else if (blue==76 && green==177 && red==34) {
					handler.addObj(new Pipe(i*16 ,j*16 ,32 ,16 ,1 ,3 ,false )); //sprite index 1:thân cột
				}  
				
				
			}
		}
	}
	private void loadCharacters (String levelCharactersPath) {
        this.levelTiles = loader.loadImage(levelCharactersPath);
		
		int width = levelTiles.getWidth();
		int height = levelTiles.getHeight();
		
		for (int i=0; i < height;i++) {
			for (int j=0;j<width;j++) {
				
				int pixel = levelTiles.getRGB(i, j);
				int red = (pixel >> 16)& 0xff;
				int green = (pixel >> 8)& 0xff;
				int blue = pixel & 0xff;
				
				if (red == 255 && green==255 && blue==255) continue;
				
				if (red== green && red== blue) {
					if (red==0) {
						handler.setPlayer(new Player(i*16, j*16,3,handler));
					}
				}
				
			}
		}
	}
}
