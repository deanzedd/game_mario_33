package com.game.gfx;

import java.awt.image.BufferedImage;
import java.util.List;

public class Texture {
    private final String PARENT_FOLDER = "/tile/";

    private final int MARIO_L_COUNT = 21; // đơn giản chỉ là số animations của mario large
    private final int MARIO_S_COUNT = 16; // đơn giản chỉ là số animations của mario small
    private final int PLAYER_COUNT = 4;
    

    private final int TILE_1_COUNT = 28;  // nhìn ảnh tileset sẽ hiểu vì sao
    private final int TILE_2_COUNT = 33;

    private BufferedImageLoader loader;
    private BufferedImage player_sheet, enemy_sheet, npc_sheet, block_sheet,
                          tile_sheet, game_over_sheet, intro_sheet,goombas_sheet,player_sheet1;

    public BufferedImage[] mario_l, mario_s, tile_1, tile_2, tile_3, tile_4, pipe_1, pipe_2, debris_1, Goombas_s,Goombas_l;

    public Texture() {
    	Goombas_s = new BufferedImage[1];
    	Goombas_l = new BufferedImage[MARIO_L_COUNT];
        mario_l = new BufferedImage[MARIO_L_COUNT];
        mario_s = new BufferedImage[MARIO_S_COUNT];
        tile_1 = new BufferedImage[TILE_1_COUNT+TILE_2_COUNT];
        tile_2 = new BufferedImage[TILE_1_COUNT+TILE_2_COUNT];
        tile_3 = new BufferedImage[TILE_1_COUNT+TILE_2_COUNT];
        tile_4 = new BufferedImage[TILE_1_COUNT+TILE_2_COUNT];
        pipe_1 = new BufferedImage[4];
        pipe_2 = new BufferedImage[4];
        debris_1 = new BufferedImage[4];
        loader = new BufferedImageLoader();
    
    try{
    	player_sheet = loader.loadImage (PARENT_FOLDER + "NES - Super Mario Bros - Mario Forms.png");
    	enemy_sheet = loader.loadImage (PARENT_FOLDER + "NES - Super Mario Bros - Sprites.png");
    	npc_sheet = loader.loadImage (PARENT_FOLDER + "NES - Super Mario Bros - Sprites.png");
        block_sheet = loader.loadImage (PARENT_FOLDER+ "NES - Super Mario Bros - Tileset.png");
        tile_sheet = loader.loadImage (PARENT_FOLDER+ "NES - Super Mario Bros - Tileset.png"); 
        game_over_sheet = loader.loadImage (PARENT_FOLDER+ "NES - Super Mario Bros - Time Up Game Over Screen.png");
        intro_sheet = loader.loadImage (PARENT_FOLDER+ "NES - Super Mario Bros - Title Screen.png");
        goombas_sheet = loader.loadImage (PARENT_FOLDER + "Ghost.png");
        player_sheet1 = loader.loadImage (PARENT_FOLDER + "PLAYER3.png");
    	
    } catch (Exception e) {
    	e.printStackTrace();
    }
    getPlayerTextures();
    getTileTextures();
    getPipeTexture();
    getDebrisTextures();
    getGoombasTextures();
    }
    
    
    private void getPlayerTextures() {
    	int x_off=0;  
    	int y_off=0;   // toa do pixel bat dau. luu y chon dung toa do pixel
    	int width=16;  // chieu dai chieu rong pixel tung khung hinh
    	int height =16;
    	
    	for (int i=0; i<PLAYER_COUNT;i++) {
    		mario_s[i]=player_sheet1.getSubimage(x_off+i*(width), y_off, width, height);
    	}
    	for (int i=0; i<PLAYER_COUNT;i++) {
    		mario_s[i+4]=player_sheet1.getSubimage(x_off+i*(width), y_off + height, width, height);
    	}
    	for (int i=0; i<PLAYER_COUNT;i++) {
    		mario_s[i+8]=player_sheet1.getSubimage(x_off+i*(width), y_off +2* height, width, height);
    	}
    	for (int i=0; i<PLAYER_COUNT;i++) {
    		mario_s[i+12]=player_sheet1.getSubimage(x_off+i*(width), y_off +3* height, width, height);
    	}
    	
    	
    }
    
    
    
    private void getTileTextures() {
    	int x_off=0;  
    	int y_off=0;   
    	int width=16;  
    	int height=16;
    	
    	for (int j=0;j<4;j++) {
    		for (int i=0;i< TILE_1_COUNT; i++ )
    			if (j==0) {
    				tile_1[i] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			} else if (j==1) {
    				tile_2[i] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			} else if (j==2) {
    				tile_3[i] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			} else if (j==3) {
    				tile_4[i] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			}
    	  	y_off += height;
    		for (int i=0;i< TILE_2_COUNT; i++ )
    			if (j==0) {
    				tile_1[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			} else if (j==1) {
    				tile_2[i+ TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			} else if (j==2) {
    				tile_3[i+ TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			} else if (j==3) {
    				tile_4[i+ TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i*width, y_off + j*height, width, height);
    			}
    		}
  
    	
    	}
    
    
    private void getPipeTexture() {  //lấy họa tiết ống
        int x_off = 0;
        int y_off = 16 * 8;
        int width = 32;
        int height = 16;
        
        //pipe_1 loại pipe màu xanh
        pipe_1[0] = tile_sheet.getSubimage(x_off, y_off, width, height);
        pipe_1[1] = tile_sheet.getSubimage(x_off, y_off + height, width, height);
        pipe_1[2] = tile_sheet.getSubimage(x_off + width, y_off, width, height);
        pipe_1[3] = tile_sheet.getSubimage(x_off + width, y_off + height, width, height);
       
        y_off+= 16*4;
        pipe_2[0] = tile_sheet.getSubimage(x_off, y_off, width, height);
        pipe_2[1] = tile_sheet.getSubimage(x_off, y_off + height, width, height);
        pipe_2[2] = tile_sheet.getSubimage(x_off + width, y_off, width, height);
        pipe_2[3] = tile_sheet.getSubimage(x_off + width, y_off + height, width, height);
        
    }
    
    private void getDebrisTextures() {	// Ham tao hoa tiet manh vo
    	int x_off = 384;
    	int y_off = 0;
    	int width = 8;
    	int height = 8;
    	
    
    	debris_1[0] = block_sheet.getSubimage(x_off, y_off, width, height);
    	debris_1[1] = block_sheet.getSubimage(x_off, y_off + height, width, height);
    	debris_1[2] = block_sheet.getSubimage(x_off + width, y_off, width, height);
    	debris_1[3] = block_sheet.getSubimage(x_off + width, y_off + height, width, height);
    }
    
    private void getGoombasTextures() {
    	int x_off=0;  
    	int y_off=0;   // toa do pixel bat dau. luu y chon dung toa do pixel
    	int width=15;  // chieu dai chieu rong pixel tung khung hinh
    	int height =15;
    	
    	
    		Goombas_s[0]=goombas_sheet.getSubimage(x_off, y_off, width, height);
    	
    	
    }
    
    public BufferedImage[] getGoombasS() {
    	return Goombas_s;
    }
    
    public BufferedImage[] getGoombasL() {
    	return Goombas_l;
    }
    
    public BufferedImage[] getMarioL() {
    	return mario_l;
    }
    public BufferedImage[] getMarioS() {
    	return mario_s;
    }
    public BufferedImage[] getTile1() {
    	return tile_1;
    }
    public BufferedImage[] getTile2() {
    	return tile_2;
    }
    public BufferedImage[] getTile3() {
    	return tile_3;
    }
    public BufferedImage[] getTile4() {
    	return tile_4;
    }
    public BufferedImage[] getPipe1() {
    	return pipe_1;
    }
    public BufferedImage[] getPipe2() {
    	return pipe_2;
    }
    public BufferedImage[] getDebris1() {
    	return debris_1;
    }
    
    
}