package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class MyProgressBar extends Table {
	
    private Image full;
    
    private final int patchNinePixelPad = 3;
    
    private float progress;
    private float targetProgress;
    
    private boolean updated = false;
    
    public MyProgressBar(Skin skin, int progress, int targetProgress) {
    	this.setBackground(skin.getDrawable("progressBarBorder"));
        full = new Image(skin.getDrawable("progressBarFill"));
        this.progress = progress;
        this.targetProgress = targetProgress;
        
        this.add(full).expand().fillY().align(Align.left);
    }
    
    public void update(int progress) {
    	this.progress = progress;
    	updated = true;
    }
    
    @Override
    public void draw(Batch batch, float alpha){
    	super.draw(batch, alpha);
    	
    	// Have to update here as the table doesn't get its width until after Screen.show() is finished (so there is no place to
    	// update it in the screen without resorting to the same idea, except on every screen it is used)
    	if(updated) {
    		full.setWidth(((progress/targetProgress)*this.getWidth())-patchNinePixelPad); // Don't x2 for some reason. GameUI is
    																					  // wrong if we do, but stats right..
    		// For some reason this test is needed..
    		if(full.getWidth()+patchNinePixelPad*2 > this.getWidth())
    			full.setWidth(this.getWidth()-patchNinePixelPad*2);
    		updated = false;
    	}
    }
}
