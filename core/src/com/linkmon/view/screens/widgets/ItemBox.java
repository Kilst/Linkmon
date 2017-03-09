package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.helpers.ResourceLoader;

public class ItemBox extends Image {

	private Texture itemImage;
	
	public ItemBox() {
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.setDrawable(skin2.getDrawable("itemBox"));
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
