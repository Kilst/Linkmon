package com.linkmon.model.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;

public class PixelData {

	// Alpha pixels && Y-Pixel Height
	
	int[][] pixelGrid = new int[33][33]; // 0 = transparent, 1 = filled
	
	int[] rows = new int[33]; // Y Height (returns height of pixel column based on player Xpos)
	
	

	public void getPixelData(GameObject object) {
		if (!((LibgdxRenderingComponent)object.getRenderer()).getSprite().getTexture().getTextureData().isPrepared()) {
			((LibgdxRenderingComponent)object.getRenderer()).getSprite().getTexture().getTextureData().prepare();
	    }
		
		pixelGrid = new int[(int) object.getWidth()+1][(int) object.getHeight()+1]; // 0 = transparent, 1 = filled

		rows = new int[(int) object.getHeight()+1]; // Y Height (returns height of pixel column based on player Xpos)
		
		Pixmap pixmap = ((LibgdxRenderingComponent)object.getRenderer()).getSprite().getTexture().getTextureData().consumePixmap();
		
		for (int x = 0; x < (int) object.getWidth(); x++) {
			int countY = 0;
			int height = 0;
	        for (int y = (int) object.getHeight(); y > -1; y--) {
	        	Color color = new Color(pixmap.getPixel(x, y));
	        	
	        	countY++;
	        	
	        	// Check for non-transparent pixel
	        	if (color.a != 0) {
	        		pixelGrid[x][y] = 1;
	        		height = countY;
	        	}
	        	else
	        		pixelGrid[x][y] = 0;
	        }
	        
	        String message = "X: " + x + " yCount: " + countY;
			Gdx.app.log("Collision", message);
	    	rows[x] = height;
		}
	}
	
}
