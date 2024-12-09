package com.game.main.util; //HELP US IMPORT OUR FILE INTO DIFFERENT GAME OBJECTS

import java.awt.image.BufferedImage;

import com.game.gfx.BufferedImageLoader;
import com.game.object.Block;
import com.game.object.Goombas;
import com.game.object.Pipe;
import com.game.object.Player;
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
		setLevel(PARENT_FOLDER + "/map1_5.png");
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
				//Block // ae lưu ý index sẽ là thứ tự của tile trong ảnh. ở đây ta có 4 thể loại tile là tile_1
	            // tile_2,3,4. ví dụ tile 4 index 4 sẽ là ô thứ 4 trong ảnh và nó màu xám =)))
	            if (red==0 && green==0 && blue==0) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,0 ,3, false )); // số thứ 2 từ phải sang là index
	            } 
	            
	            if (red==255 && green==0 && blue==0) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,28 ,3, false )); 
	            } 
	            if (red==0 && green==0 && blue==255) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,2 ,3, false )); 
	            }
	            if (red==150 && green==150 && blue==0) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,24 ,3, false )); 
	            }
	            
	            //lâu đài
	            if (red==255 && green==10 && blue==0) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,11 ,3, false )); 
	            }
	            if (red==255 && green==0 && blue==10) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,17 ,3, true )); 
	            }
	            if (red==255 && green==100 && blue==0) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,40 ,3, true )); 
	            }
	            if (red==255 && green==100 && blue==100) {
	                handler.addObj(new Block(i*16 ,j*16 ,16 ,16 ,41 ,3, true )); 
	            }
				
				//Cột
	            else if (red==25&& green==111 && blue==48) {
	                handler.addObj(new Pipe(i*16 ,j*16 ,32 ,16 ,0 ,3 ,false)); //sprite index 0:đỉnh cột
	            } else if (red==34 && green==177 &&blue==76 ) {
	                handler.addObj(new Pipe(i*16 ,j*16 ,32 ,16 ,1 ,3 ,false )); //sprite index 1:thân cột
	            } 
	            
	            //MONSTER
	            else if (blue==39 && green==127 && red==255) {
					handler.addObj(new Goombas(i*16, j*16,3,handler) );//?????????????????????????
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
