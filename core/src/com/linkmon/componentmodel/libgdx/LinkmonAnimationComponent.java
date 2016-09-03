package com.linkmon.componentmodel.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.animations.AnimationStateAngry;
import com.linkmon.componentmodel.linkmon.animations.AnimationStateIdle;
import com.linkmon.componentmodel.linkmon.animations.AnimationStateSleeping;
import com.linkmon.componentmodel.linkmon.animations.AnimationStateWalk;
import com.linkmon.componentmodel.linkmon.animations.AnimationStateWave;
import com.linkmon.componentmodel.linkmon.animations.IAnimationState;
import com.linkmon.helpers.ResourceLoader;

public class LinkmonAnimationComponent extends LibgdxAnimationComponent {
	
	TextureRegion[] idleAnimationFrames;
    Animation idle;
    TextureRegion[] walkAnimationFrames;
    Animation walk;
    TextureRegion[] waveAnimationFrames;
    Animation wave;
    TextureRegion[] sleepAnimationFrames;
    Animation sleep;
    TextureRegion[] angryAnimationFrames;
    Animation angry;
    
    public LinkmonAnimationComponent(GameObject object) {
		super();
		// TODO Auto-generated constructor stub
		
		Array<AtlasRegion>[] animationRegions;
		animationRegions = ResourceLoader.getLinkmonAnimFromId(object.getId());
		
		idleAnimationFrames = new TextureRegion[animationRegions[0].size];
		int i = 0;
		for(AtlasRegion region : animationRegions[0]) {
			idleAnimationFrames[i] = region;
			i++;
		}
		idle = new Animation(2f/76f,idleAnimationFrames);
		
		walkAnimationFrames = new TextureRegion[animationRegions[1].size];
		i = 0;
		for(AtlasRegion region : animationRegions[1]) {
			walkAnimationFrames[i] = region;
			i++;
		}
		walk = new Animation(2f/76f,walkAnimationFrames);
		
		waveAnimationFrames = new TextureRegion[animationRegions[2].size];
		i = 0;
		for(AtlasRegion region : animationRegions[2]) {
			waveAnimationFrames[i] = region;
			i++;
		}
		wave = new Animation(2f/76f,waveAnimationFrames);
		
		sleepAnimationFrames = new TextureRegion[animationRegions[3].size];
		i = 0;
		for(AtlasRegion region : animationRegions[3]) {
			sleepAnimationFrames[i] = region;
			i++;
		}
		sleep = new Animation(2f/76f,sleepAnimationFrames);
		
		angryAnimationFrames = new TextureRegion[animationRegions[4].size];
		i = 0;
		for(AtlasRegion region : animationRegions[4]) {
			angryAnimationFrames[i] = region;
			i++;
		}
		angry = new Animation(2f/76f,angryAnimationFrames);
		
		
		currentAnimation  = idle;
		
		state = new AnimationStateIdle();
	}
	
	public void angry() {
		if(currentAnimation != angry) {
			currentAnimation = angry;
			elapsedTime = 0;
			looping = false;
		}
	}
	
	public void sleep() {
		if(currentAnimation != sleep) {
			currentAnimation = sleep;
			elapsedTime = 0;
			looping = false;
		}
	}
	
	public void wave() {
		if(currentAnimation != wave) {
			currentAnimation = wave;
			elapsedTime = 0;
			looping = false;
		}
	}
	
	public void walk() {
		if(currentAnimation != walk) {
			currentAnimation = walk;
			elapsedTime = 0;
			looping = true;
		}
	}
	
	public void idle() {
		if(currentAnimation != idle) {
			currentAnimation = idle;
			elapsedTime = 0;
			looping = true;
		}
	}
	
	@Override
	protected void updateCurrentAnimation() {
		if(state instanceof AnimationStateWave) {
			wave();
			return;
		}
		else if(state instanceof AnimationStateAngry) {
			angry();
			return;
		}
		else if(state instanceof AnimationStateSleeping) {
			sleep();
			return;
		}
		else if(state instanceof AnimationStateWalk) {
			walk();
			return;
		}
		else if (state instanceof AnimationStateIdle) {
			idle();
			return;
		}
	}

	@Override
	public IAnimationState getState() {
		// TODO Auto-generated method stub
		return state;
	}
}
