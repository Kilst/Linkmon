package com.linkmon.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.linkmon.model.gameobject.items.ItemIds;
import com.linkmon.model.gameobject.linkmon.LinkmonIds;

public class ResourceLoader {
	private static ResourceLoader instance;
	public static AssetManager assetManager;
	
	public static boolean loaded = false;
	
	public static final String UIPath = "UI/UI.png";
	public static final String UIAtlas = "UI/UI.pack";
	
	private ResourceLoader()
	{
		load();
	}
	
	public static ResourceLoader getInstance()
	{
		if(instance == null)
			instance = new ResourceLoader();
		return instance;
	}
	
	public static TextureRegion getRegionFromId(int id)
	{
		Texture tex;
		TextureAtlas atlas = null;
		TextureRegion region = null;
		switch(id)
		{
			case 0: {
				tex = ResourceLoader.assetManager.get("fire-boy.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 7: {
				tex = ResourceLoader.assetManager.get("baby-robot.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 8: {
				tex = ResourceLoader.assetManager.get("trainingBag.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 1: {
				tex = ResourceLoader.assetManager.get("my-robot.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 2: {
				tex = ResourceLoader.assetManager.get("meat.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 3: {
				tex = ResourceLoader.assetManager.get("poop.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 4: {
				tex = ResourceLoader.assetManager.get("thoughtBubble(food).png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 5: {
				tex = ResourceLoader.assetManager.get("thoughtBubble(poop).png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case 6: {
				tex = ResourceLoader.assetManager.get("thoughtBubble(sleep).png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			default: {
				region = null;
				break;
			}
			
		}
		return region;
	}
	
	public static Array<AtlasRegion>[] getLinkmonAnimFromId(int id)
	{
		TextureAtlas atlas = null;
		Array<AtlasRegion>[] regions = new Array[5];
		switch(id)
		{
			case LinkmonIds.FIRE_BABY: {
				atlas = new TextureAtlas("Animations/fire-baby-idle.pack");
				regions[0] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-baby-walk.pack");
				regions[1] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-baby-wave.pack");
				regions[2] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-sleep.pack");
				regions[3] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-angry.pack");
				regions[4] = atlas.getRegions();
		        break;
			}
			case LinkmonIds.FIRE_BOY: {
				atlas = new TextureAtlas("Animations/fire-boy-idle.pack");
				regions[0] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-walk.pack");
				regions[1] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-wave.pack");
				regions[2] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-sleep.pack");
				regions[3] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-angry.pack");
				regions[4] = atlas.getRegions();
		        break;
			}
			case LinkmonIds.FIRE_CHAMPION: {
				atlas = new TextureAtlas("Animations/fire-baby-idle.pack");
				regions[0] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-baby-walk.pack");
				regions[1] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-baby-wave.pack");
				regions[2] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-sleep.pack");
				regions[3] = atlas.getRegions();
				atlas = new TextureAtlas("Animations/fire-boy-angry.pack");
				regions[4] = atlas.getRegions();
		        break;
			}	
		}
		return regions;
	}
	
	public static void load()
	{
		if (assetManager == null)
		{
			assetManager = new AssetManager();
			
			TextureParameter param = new TextureParameter();
			param.minFilter = TextureFilter.MipMapLinearLinear;
			param.magFilter = TextureFilter.Linear;
			
			assetManager.load("trainingBag.png", Texture.class);
			
			assetManager.load("baby-robot.png", Texture.class);
			assetManager.load("my-robot.png", Texture.class);
			assetManager.load("fire-boy.png", Texture.class);
			assetManager.load("meat.png", Texture.class);
			assetManager.load("potion.png", Texture.class);
			assetManager.load("poop.png", Texture.class);
			assetManager.load("thoughtBubble(food).png", Texture.class);
			assetManager.load("thoughtBubble(poop).png", Texture.class);
			assetManager.load("thoughtBubble(sleep).png", Texture.class);
			
			assetManager.load(UIPath, Texture.class);
			assetManager.load(UIAtlas, TextureAtlas.class);
		}
		loaded = true;
	}
	
	public void dispose()
	{
//		assetManager.unload("maps/tileTextures/plantedSeeds.png");
//		assetManager.unload("maps/tileTextures/tomatoSeedling.png");
//		assetManager.unload("UI/Inventory/Textures/UIBase.png");
//		assetManager.unload(grass1ImagePath);
//		assetManager.unload(grass2ImagePath);
//		assetManager.unload(grass3ImagePath);
//		assetManager.unload(grass4ImagePath);
//		assetManager.unload(tilledImagePath);
//		assetManager.unload(treeImagePath);
//		assetManager.unload(tomatoImagePath);
//		assetManager.unload(houseImagePath);
//		assetManager.unload(selectImagePath);
//		assetManager.unload(screwsImagePath);
//		assetManager.unload(deleteImagePath);
//		assetManager.unload(groundImagePath);
//		assetManager.unload("maps/tileTextures/highlight.png");
//		assetManager.unload("maps/tileTextures/seeds.png");
//		assetManager.unload("maps/tileTextures/sickle.png");
		loaded = false;
	}

	public static TextureRegion getItemRegionFromId(int id) {
		// TODO Auto-generated method stub
		Texture tex;
		TextureAtlas atlas = null;
		TextureRegion region = null;
		switch(id)
		{
			case ItemIds.LARGE_MEAT: {
				tex = ResourceLoader.assetManager.get("meat.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case ItemIds.SUPER_MEAT: {
				tex = ResourceLoader.assetManager.get("meat.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
			case ItemIds.REVIVE_POTION: {
				tex = ResourceLoader.assetManager.get("potion.png", Texture.class);
				region = new TextureRegion(tex);
		        break;
			}
		}
		return region;
	}
}
