package com.linkmon.componentmodel.libgdx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.animations.IAnimationState;
import com.linkmon.helpers.ResourceLoader;

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
