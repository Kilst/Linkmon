package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.linkmon.view.WorldRenderer;

public class MyProgressBar extends Actor {
	
	private NinePatch empty;
    private NinePatch full;
    
    private float width;
    private float height;
    
    private float alignment;
    
    private float progress;
    private int targetProgress;
    
    public MyProgressBar(float width, float height) {
    	empty = new NinePatch(new Texture(Gdx.files.internal("loadingBarEmpty.png")), 8, 8, 8, 8);
        full = new NinePatch(new Texture(Gdx.files.internal("loadingBarFull.png")), 8, 8, 8, 8);
        progress = 100;
        targetProgress = 100;
        this.width = width;
        this.height = height;
        
        alignment = 0;
    }
    
    public void update(int targetProgress) {
    	this.targetProgress = targetProgress;
    }
    
    public void alignMiddle(int widgetHeight) {
    	alignment = (widgetHeight - height)/2;
    }
    
	@Override
	public void draw(Batch batch, float alpha){
		if(progress > targetProgress)
			progress-=0.25f;
		else
			if(progress < targetProgress)
				progress+=1f;
		if(progress < 95 && progress > 30)
			batch.setColor(1, 1, 0.1f, 1);
		else if(progress <= 30)
			batch.setColor(1, 0, 0, 1);
		if(progress > 0)
			full.draw(batch, this.getX()+10*WorldRenderer.scaleXY, this.getY()+alignment*WorldRenderer.scaleXY, (progress*(width/115))*WorldRenderer.scaleXY, height*WorldRenderer.scaleXY);
		batch.setColor(1, 1, 1, 1);
		empty.draw(batch, this.getX(), this.getY()+alignment*WorldRenderer.scaleY, width*WorldRenderer.scaleXY, height*WorldRenderer.scaleXY);
	}
}
