package com.linkmon.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	
	public WorldRenderingComponent(World world, String backGroundImageName) {
		this.world = world;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		darkenWorld = new Sprite(skin2.getRegion("darkenWorld"));
		darkenWorld.setSize(1280, 720);
		darkenWorld.setColor(1f, 0f, 0f, 0.7f);
		
		this.sprite = new Sprite(ResourceLoader.assetManager.get(backGroundImageName+".png", Texture.class));
		sprite.setSize(1280, 720);
	}
	
	public WorldRenderingComponent(World world, String backgroundTexture, boolean f) {
		this.world = world;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		darkenWorld = new Sprite(skin2.getRegion("darkenWorld"));
		darkenWorld.setSize(1280, 720);
		darkenWorld.setColor(1f, 0f, 0f, 0.7f);
		
		this.sprite = new Sprite(ResourceLoader.assetManager.get(backgroundTexture, Texture.class));
		sprite.setSize(1280, 720);
	}
	
	public void setBackground() {
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.sprite = new Sprite(ResourceLoader.assetManager.get("battleBackground.png", Texture.class));
		sprite.setSize(1280, 720);
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
