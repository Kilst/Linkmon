package com.linkmon.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.UIRenderer;

public class WorldRenderingComponent extends LibgdxRenderingComponent {

	private Sprite darkenWorld;
	
	private World world;
	
	public WorldRenderingComponent(World world) {
		this.world = world;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		darkenWorld = new Sprite(skin2.getRegion("darkenWorld"));
		darkenWorld.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darkenWorld.setColor(1f, 0f, 0f, 0.7f);
		
		this.sprite = new Sprite(skin2.getRegion("trainingBackground"));
		sprite.setSize(sprite.getWidth()*(Gdx.graphics.getHeight()/sprite.getHeight()), sprite.getHeight()*(Gdx.graphics.getHeight()/sprite.getHeight()));
	}
	
	public void draw(Batch batch, GameObject object) {
		// TODO Auto-generated method stub
		if(sprite != null)
			sprite.draw(batch);
	}
	
	public void drawLight(Batch batch, GameObject object) {
		// TODO Auto-generated method stub
		if(!world.isLightOn())
			darkenWorld.draw(batch);
	}
}
