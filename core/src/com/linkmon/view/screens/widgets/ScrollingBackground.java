package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.linkmon.view.UIRenderer;

public class ScrollingBackground extends Actor {
	
	private Texture texture;
	private int updatedX = 0;
	private int updatedY = 0;
	private float speedX = 80;
	private float speedY = 80;
	
	public ScrollingBackground(Texture tex) {
		texture = tex;
	}
	
	public void setScrollSpeed(float speedX, float speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

   @Override
   public void act(float delta) {
      super.act(delta);
      if(updatedX <= texture.getWidth()){
    	  updatedX+=speedX*delta;
      }
      else if(updatedX > texture.getWidth()){
    	  updatedX-=texture.getWidth();
      }
      
      if(updatedY <= texture.getHeight()){
    	  updatedY+=speedY*delta;
      }
      else if(updatedY > texture.getHeight()){
    	  updatedY-=texture.getHeight();
      }
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);
      batch.draw(texture, 0, 0, UIRenderer.WIDTH, UIRenderer.HEIGHT, updatedX, updatedY, UIRenderer.WIDTH, UIRenderer.HEIGHT, false, false);
   }
}
