package com.linkmon.model.libgdx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.animations.IAnimationState;

public class PoopaScoopaAnimationComponent extends LibgdxAnimationComponent {

	TextureRegion[] idleAnimationFrames;
    Animation idle;
    
    public PoopaScoopaAnimationComponent(GameObject poopaScoopa) {
    	Array<AtlasRegion> animationRegions = ResourceLoader.getAnimationFromId(poopaScoopa.getId());
		
		idleAnimationFrames = new TextureRegion[animationRegions.size];
		int i = 0;
		for(AtlasRegion region : animationRegions) {
			idleAnimationFrames[i] = region;
			i++;
		}
		idle = new Animation(2f/15f,idleAnimationFrames);
		
		currentAnimation  = idle;
		
		poopaScoopa.setWidth(getCurrentKeyFrame().getRegionWidth());
		poopaScoopa.setHeight(getCurrentKeyFrame().getRegionHeight());
		
		looping = true;
    }
	
	@Override
	public void updateAnimations(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAnimationState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateCurrentAnimation() {
		// TODO Auto-generated method stub
		
	}

}
