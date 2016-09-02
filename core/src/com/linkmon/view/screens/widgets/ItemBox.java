package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ItemBox extends Image {

	private Texture itemImage;
	
	public ItemBox() {
		
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("itemBox.png")))));
	}
	
	public void addItemImage(Texture itemImage) {
		this.itemImage = itemImage;
	}
	
	@Override
 	public void draw(Batch batch, float parentAlpha) {
		if(itemImage != null)
			batch.draw(itemImage, this.getX()+this.getWidth()*0.175f, this.getY()+this.getHeight()*0.175f, this.getWidth()*0.7f, this.getHeight()*0.7f);
		super.draw(batch, parentAlpha);
	}
}
