package com.linkmon.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.linkmon.LinkmonIds;

public class ResourceLoader {
	private static ResourceLoader instance;
	public static AssetManager assetManager;
	
	public static boolean loaded = false;
	
	public static String UIPath = "UI/UI.png";
	public static String UIAtlas = "UI/UI.pack";
	
	static BitmapFont fontSmall = null;
	static BitmapFont fontMedium = null;
	static BitmapFont fontSmallLarge = null;
	static BitmapFont fontLarge = null;
	
	static BitmapFont fontSmallSample = null;
	static BitmapFont fontMediumSample = null;
	static BitmapFont fontSmallLargeSample = null;
	static BitmapFont fontLargeSample = null;
	
	private ResourceLoader()
	{
		load();
	}
	
	public static ResourceLoader getInstance()
	{
		if (assetManager != null) {
	        assetManager.dispose();
	        assetManager = null;
	        loaded = false;
	    }
		
		if(instance == null)
			instance = new ResourceLoader();
		return instance;
	}
	
	public static Texture getRegionFromId(int id)
	{
		Texture tex = null;
		
		switch(id)
		{
			case ObjectId.FIREBALL: {
				tex = ResourceLoader.assetManager.get("fireBall.png", Texture.class);
		        break;
			}
			case (ObjectId.ATTACK_BEAM): {
				tex = ResourceLoader.assetManager.get("attackBeam.png", Texture.class);
		        break;
			}
			case (ObjectId.ATTACK_BEAM_TAIL): {
				tex = ResourceLoader.assetManager.get("attackBeamTail.png", Texture.class);
		        break;
			}
			case (ObjectId.DUCK): {
				tex = ResourceLoader.assetManager.get("duck.png", Texture.class);
		        break;
			}
			case (ObjectId.FINISH_LINE): {
				tex = ResourceLoader.assetManager.get("finish_line.png", Texture.class);
		        break;
			}
			case (ObjectId.WAVE): {
				tex = ResourceLoader.assetManager.get("waves.png", Texture.class);
		        break;
			}
			case 33: {
				tex = ResourceLoader.assetManager.get("spottedEgg.png", Texture.class);
		        break;
			}
			case 0: {
				tex = ResourceLoader.assetManager.get("fire-boy.png", Texture.class);
		        break;
			}
			case ObjectId.RING: {
				tex = ResourceLoader.assetManager.get("ring.png", Texture.class);
		        break;
			}
			case 7: {
				tex = ResourceLoader.assetManager.get("baby-robot.png", Texture.class);
		        break;
			}
			case 8: {
				tex = ResourceLoader.assetManager.get("trainingBag.png", Texture.class);
		        break;
			}
			case 1: {
				tex = ResourceLoader.assetManager.get("my-robot.png", Texture.class);
		        break;
			}
			case ObjectId.MEAT: {
				tex = ResourceLoader.assetManager.get("meat.png", Texture.class);
		        break;
			}
			case ObjectId.POOP: {
				tex = ResourceLoader.assetManager.get("poop.png", Texture.class);
		        break;
			}
			case 4: {
				tex = ResourceLoader.assetManager.get("thoughtBubble(food).png", Texture.class);
		        break;
			}
			case 5: {
				tex = ResourceLoader.assetManager.get("thoughtBubble(poop).png", Texture.class);
		        break;
			}
			case 6: {
				tex = ResourceLoader.assetManager.get("thoughtBubble(sleep).png", Texture.class);
		        break;
			}
			case ObjectId.HEALTH_POTION: {
				tex = ResourceLoader.assetManager.get("redPotion.png", Texture.class);
		        break;
			}
			case ObjectId.LEAF_BALL: {
				tex = ResourceLoader.assetManager.get("leafBall.png", Texture.class);
		        break;
			}
			case ObjectId.ENERGY_POTION: {
				tex = ResourceLoader.assetManager.get("bluePotion.png", Texture.class);
		        break;
			}
			default: {
				break;
			}
			
		}
		return tex;
	}
	
	public static Array<AtlasRegion>[] getLinkmonAnimFromId(int id)
	{
		TextureAtlas atlas = null;
		Array<AtlasRegion>[] regions = new Array[5];
		switch(id)
		{
			case LinkmonIds.FIRE_BABY: {
				atlas = assetManager.get("Animations/fire-baby-idle.pack");
				regions[0] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-baby-walk.pack");
				regions[1] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-baby-wave.pack");
				regions[2] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-sleep.pack");
				regions[3] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-angry.pack");
				regions[4] = atlas.getRegions();
		        break;
			}
			case LinkmonIds.FIRE_BOY: {
				atlas = assetManager.get("Animations/eyeGuy-idle.pack");
				regions[0] = atlas.getRegions();
				atlas = assetManager.get("Animations/eyeGuy-walk.pack");
				regions[1] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-wave.pack");
				regions[2] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-sleep.pack");
				regions[3] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-angry.pack");
				regions[4] = atlas.getRegions();
		        break;
			}
			case LinkmonIds.FIRE_CHAMPION: {
				atlas = assetManager.get("Animations/fire-baby-idle.pack");
				regions[0] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-baby-walk.pack");
				regions[1] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-baby-wave.pack");
				regions[2] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-sleep.pack");
				regions[3] = atlas.getRegions();
				atlas = assetManager.get("Animations/fire-boy-angry.pack");
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
			
			assetManager.load("background.png", Texture.class);
			assetManager.load("battleBackground.png", Texture.class);
			assetManager.load("battleIntroBackground.png", Texture.class);
			assetManager.load("battleTowerBackgroundClouds.png", Texture.class);
			assetManager.load("battleTowerBackgroundSky.png", Texture.class);
			assetManager.load("battleTowerBackgroundTower.png", Texture.class);
			assetManager.load("bigContainer.png", Texture.class);
			assetManager.load("bigContainerBlue.png", Texture.class);
			assetManager.load("blueContainer.png", Texture.class);
			assetManager.load("evolutionBackground.png", Texture.class);
			assetManager.load("feedBackground.png", Texture.class);
			assetManager.load("greenContainer.png", Texture.class);
			assetManager.load("shopBackground.png", Texture.class);
			assetManager.load("statsBackground.png", Texture.class);
			assetManager.load("trainingBackground.png", Texture.class);
			
			assetManager.load("UI/statsBackground.png", Texture.class);
			assetManager.load("UI/ringGrabBackground.png", Texture.class);
			
			assetManager.load("leafBall.png", Texture.class);
			assetManager.load("whitePotion.png", Texture.class);
			assetManager.load("redPotion.png", Texture.class);
			assetManager.load("bluePotion.png", Texture.class);
			assetManager.load("greenPotion.png", Texture.class);
			assetManager.load("rainbowPotion.png", Texture.class);
			
			assetManager.load("ring.png", Texture.class);
			assetManager.load("fireBall.png", Texture.class);
			
			assetManager.load("duck.png", Texture.class);
			assetManager.load("finish_line.png", Texture.class);
			assetManager.load("waves.png", Texture.class);
			
			assetManager.load("attackBeam.png", Texture.class);
			assetManager.load("attackBeamTail.png", Texture.class);
			
			assetManager.load("trainingBag.png", Texture.class);
			
			assetManager.load("spottedEgg.png", Texture.class);
			
			assetManager.load("baby-robot.png", Texture.class);
			assetManager.load("my-robot.png", Texture.class);
			assetManager.load("fire-boy.png", Texture.class);
			assetManager.load("meat.png", Texture.class);
			assetManager.load("starBall.png", Texture.class);
			assetManager.load("poopaScoopa.png", Texture.class);
			assetManager.load("syringe.png", Texture.class);
			
			assetManager.load("poop.png", Texture.class);
			assetManager.load("thoughtBubble(food).png", Texture.class);
			assetManager.load("thoughtBubble(poop).png", Texture.class);
			assetManager.load("thoughtBubble(sleep).png", Texture.class);
			
			assetManager.load("Animations/fire-baby-idle.pack", TextureAtlas.class);
			assetManager.load("Animations/fire-baby-walk.pack", TextureAtlas.class);
			assetManager.load("Animations/fire-baby-wave.pack", TextureAtlas.class);
//			assetManager.load("Animations/fire-baby-sleep.pack", TextureAtlas.class);
//			assetManager.load("Animations/fire-baby-angry.pack", TextureAtlas.class);
			
			assetManager.load("Animations/fire-boy-idle.pack", TextureAtlas.class);
			assetManager.load("Animations/fire-boy-walk.pack", TextureAtlas.class);
			assetManager.load("Animations/fire-boy-wave.pack", TextureAtlas.class);
			assetManager.load("Animations/fire-boy-sleep.pack", TextureAtlas.class);
			assetManager.load("Animations/fire-boy-angry.pack", TextureAtlas.class);
			assetManager.load("Animations/eyeGuy-walk.pack", TextureAtlas.class);
			assetManager.load("Animations/eyeGuy-idle.pack", TextureAtlas.class);
			
			assetManager.load("Animations/PoopaScoopa/PoopaScoopa.pack", TextureAtlas.class);
			
			assetManager.load(UIPath, Texture.class);
			assetManager.load(UIAtlas, TextureAtlas.class);
			
			SmartFontGenerator fontGen = new SmartFontGenerator();
			FileHandle exoFile = Gdx.files.internal("UI/lut.ttf");
			fontSmall = fontGen.createFont(exoFile, "move-medium", 18);
			fontMedium = fontGen.createFont(exoFile, "move-large", 36);
			fontSmallLarge = fontGen.createFont(exoFile, "move-large", 46);
			fontLarge = fontGen.createFont(exoFile, "move-extra-large", 48);
			
			exoFile = Gdx.files.internal("UI/samplefont.ttf");
			
			fontSmallSample = fontGen.createFont(exoFile, "exo-medium", 18);
			fontMediumSample = fontGen.createFont(exoFile, "exo-large", 36);
			fontLargeSample = fontGen.createFont(exoFile, "exo-extra-large", 48);
			
			ResourceLoader.getSampleFont("small");
			ResourceLoader.getLutFont("small");
			ResourceLoader.getSampleFont("medium");
			ResourceLoader.getLutFont("medium");
			ResourceLoader.getSampleFont("smallLarge");
			ResourceLoader.getLutFont("smallLarge");
			ResourceLoader.getSampleFont("large");
			ResourceLoader.getLutFont("large");
			
		}
		loaded = true;
	}
	
	public static void dispose()
	{
		assetManager.unload("background.png");
		assetManager.unload("battleBackground.png");
		assetManager.unload("battleIntroBackground.png");
		assetManager.unload("battleTowerBackgroundClouds.png");
		assetManager.unload("battleTowerBackgroundSky.png");
		assetManager.unload("battleTowerBackgroundTower.png");
		assetManager.unload("bigContainer.png");
		assetManager.unload("bigContainerBlue.png");
		assetManager.unload("blueContainer.png");
		assetManager.unload("evolutionBackground.png");
		assetManager.unload("feedBackground.png");
		assetManager.unload("greenContainer.png");
		assetManager.unload("shopBackground.png");
		assetManager.unload("statsBackground.png");
		assetManager.unload("trainingBackground.png");
		
		
		assetManager.unload("UI/statsBackground.png");
		assetManager.unload("UI/ringGrabBackground.png");
		
		assetManager.unload("leafBall.png");
		assetManager.unload("whitePotion.png");
		assetManager.unload("redPotion.png");
		assetManager.unload("bluePotion.png");
		assetManager.unload("greenPotion.png");
		assetManager.unload("rainbowPotion.png");
		
		assetManager.unload("ring.png");
		assetManager.unload("fireBall.png");
		
		assetManager.unload("duck.png");
		assetManager.unload("finish_line.png");
		assetManager.unload("waves.png");
		
		assetManager.unload("attackBeam.png");
		assetManager.unload("attackBeamTail.png");
		
		assetManager.unload("trainingBag.png");
		
		assetManager.unload("spottedEgg.png");
		
		assetManager.unload("baby-robot.png");
		assetManager.unload("my-robot.png");
		assetManager.unload("fire-boy.png");
		assetManager.unload("meat.png");
		assetManager.unload("starBall.png");
		assetManager.unload("poopaScoopa.png");
		assetManager.unload("syringe.png");
		
		assetManager.unload("poop.png");
		assetManager.unload("thoughtBubble(food).png");
		assetManager.unload("thoughtBubble(poop).png");
		assetManager.unload("thoughtBubble(sleep).png");
		
		assetManager.unload("Animations/fire-baby-idle.pack");
		assetManager.unload("Animations/fire-baby-walk.pack");
		assetManager.unload("Animations/fire-baby-wave.pack");
//		assetManager.unload("Animations/fire-baby-sleep.pack", TextureAtlas.class);
//		assetManager.unload("Animations/fire-baby-angry.pack", TextureAtlas.class);
		
		assetManager.unload("Animations/fire-boy-idle.pack");
		assetManager.unload("Animations/fire-boy-walk.pack");
		assetManager.unload("Animations/fire-boy-wave.pack");
		assetManager.unload("Animations/fire-boy-sleep.pack");
		assetManager.unload("Animations/fire-boy-angry.pack");
		assetManager.unload("Animations/eyeGuy-walk.pack");
		assetManager.unload("Animations/eyeGuy-idle.pack");
		
		assetManager.unload("Animations/PoopaScoopa/PoopaScoopa.pack");
		
		assetManager.unload(UIPath);
		assetManager.unload(UIAtlas);
		loaded = false;
	}
	
	public static Array<AtlasRegion> getAnimationFromId(int id) {
		// TODO Auto-generated method stub
		TextureAtlas atlas = null;
		Array<AtlasRegion> region = null;
		switch(id)
		{
			case ObjectId.POOPA_SCOOPA: {
				atlas = new TextureAtlas("Animations/PoopaScoopa/PoopaScoopa.pack");
				region = atlas.getRegions();
		        break;
			}
		}
		return region;
	}

	public static Texture getItemRegionFromId(int id) {
		// TODO Auto-generated method stub
		Texture tex = null;
		switch(id)
		{
			case ObjectId.MEAT: {
				tex = ResourceLoader.assetManager.get("meat.png", Texture.class);
		        break;
			}
			case ObjectId.SUPER_MEAT: {
				tex = ResourceLoader.assetManager.get("meat.png", Texture.class);
		        break;
			}
			case ObjectId.HEALTH_POTION: {
				tex = ResourceLoader.assetManager.get("redPotion.png", Texture.class);
		        break;
			}
			case ObjectId.ENERGY_POTION: {
				tex = ResourceLoader.assetManager.get("bluePotion.png", Texture.class);
		        break;
			}
			case ObjectId.REVIVE_POTION: {
				tex = ResourceLoader.assetManager.get("rainbowPotion.png", Texture.class);
		        break;
			}
			case ObjectId.POOPA_SCOOPA: {
				tex = ResourceLoader.assetManager.get("poopaScoopa.png", Texture.class);
		        break;
			}
			case ObjectId.MEDICINE: {
				tex = ResourceLoader.assetManager.get("syringe.png", Texture.class);
		        break;
			}
		}
		return tex;
	}
	
	public static BitmapFont getSampleFont(String size) {
		
		if(size.matches("small"))
			return fontSmallSample;
		else if(size.matches("medium"))
			return fontMediumSample;
		else
			return fontLargeSample;
	}
	
	public static BitmapFont getLutFont(String size) {
		
		if(size.matches("small"))
			return fontSmall;
		else if(size.matches("medium"))
			return fontMedium;
		else if(size.matches("smallLarge"))
			return fontSmallLarge;
		else
			return fontLarge;
	}
}
