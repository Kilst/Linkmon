package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.linkmon.helpers.ResourceLoader;

public class AnimationWidget extends Actor {
	
	Animation animation;
	
	TextureRegion currentFrame;
	
	float elapsedTime = 0;
	
	public AnimationWidget(FileHandle file, float frameDuration) {
//		AtlasRegion animations = new AtlasRegion(ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class));
		TextureAtlas atlas = new TextureAtlas(file);
		
		Array<AtlasRegion> regions = atlas.getRegions();
		
		
		TextureRegion[] loadingAnimationFrames = new TextureRegion[regions.size];
		int i = 0;
		for(AtlasRegion region : regions) {
			loadingAnimationFrames[i] = region;
			i++;
		}
		animation = new Animation(frameDuration,loadingAnimationFrames);
		
		this.setSize(loadingAnimationFrames[0].getRegionWidth(), loadingAnimationFrames[0].getRegionHeight());
	}
	
	public AnimationWidget(int linkmonId, float frameDuration) {
//		AtlasRegion animations = new AtlasRegion(ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class));
		TextureAtlas atlas = new TextureAtlas();
		
		Array<AtlasRegion>[] regions = ResourceLoader.getLinkmonAnimFromId(linkmonId);
		
		
		TextureRegion[] loadingAnimationFrames = new TextureRegion[regions[0].size];
		int i = 0;
		for(AtlasRegion region : regions[0]) {
			loadingAnimationFrames[i] = region;
			i++;
		}
		animation = new Animation(frameDuration,loadingAnimationFrames);
		
		this.setSize(loadingAnimationFrames[0].getRegionWidth(), loadingAnimationFrames[0].getRegionHeight());
	}
	
	private void getCurrentFrame() {
		elapsedTime+= Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(elapsedTime, true);
	}
	
	@Override
	public void act(float delta) {
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		getCurrentFrame();
		
		batch.draw(currentFrame, getX(), getY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
	}
}
