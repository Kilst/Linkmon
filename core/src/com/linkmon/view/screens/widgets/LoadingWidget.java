package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class LoadingWidget extends Actor {
	
	Animation loading;
	
	TextureRegion currentFrame;
	
	float elapsedTime = 0;
	
	public LoadingWidget() {
//		AtlasRegion animations = new AtlasRegion(ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class));
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("Animations/Loading/loading.pack"));
		
		Array<AtlasRegion> regions = atlas.getRegions();
		
		
		TextureRegion[] loadingAnimationFrames = new TextureRegion[regions.size];
		int i = 0;
		for(AtlasRegion region : regions) {
			loadingAnimationFrames[i] = region;
			i++;
		}
		loading = new Animation(0.5f,loadingAnimationFrames);
		
		this.setSize(loadingAnimationFrames[0].getRegionWidth(), loadingAnimationFrames[0].getRegionHeight());
	}
	
	private void getCurrentFrame() {
		elapsedTime+= Gdx.graphics.getDeltaTime();
		currentFrame = loading.getKeyFrame(elapsedTime, true);
	}
	
	@Override
	public void act(float delta) {
		getCurrentFrame();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		getCurrentFrame();
		
		batch.draw(currentFrame, getX(), getY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
	}
}
