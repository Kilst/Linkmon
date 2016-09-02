package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class EggButton extends ImageButton {
	
	private Drawable backImage;
	
	private Drawable selectImage;
	
	public boolean selected = false;
	
	public int eggId;

	public EggButton(int eggId, Drawable imageUp, Drawable backImage, Drawable selectImage) {
		super(imageUp);
		// TODO Auto-generated constructor stub
		
		this.eggId = eggId;
		
		this.backImage = backImage;
		this.selectImage = selectImage;
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		
		backImage.draw(batch, getX(), getY(), this.getWidth(), this.getHeight());
		
		if(selected)
			selectImage.draw(batch, getX(), getY(), this.getWidth(), this.getHeight());
		
		super.draw(batch, alpha);
		
	}

}
