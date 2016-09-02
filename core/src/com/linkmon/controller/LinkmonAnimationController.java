package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.linkmon.AnimationType;

public class LinkmonAnimationController {
	
	public int animationType = AnimationType.IDLE;
	Animation idle;
	Animation walk;
	Animation wave;
	Animation sleep;
	Animation angry;
	
	float elapsedTime = 0;
	
	boolean isPlaying = false;
	boolean looping = true;
    
    public boolean flipped = false;
    
    TextureRegion currentFrame;
    Animation currentAnimation;
    LinkmonController linkmonController;
	
	public LinkmonAnimationController(Animation idle, Animation walk, Animation wave, Animation sleep, Animation angry) {
		this.idle = idle;
		this.walk = walk;
		this.wave = wave;
		this.sleep = sleep;
		this.angry = angry;
		
		currentAnimation  = idle;
		
		animationType = AnimationType.IDLE;
	}
	
	public boolean getCurrentAnimationEnded() {
		return currentAnimation.isAnimationFinished(elapsedTime);
	}
	
	public void angry() {
		if(currentAnimation != angry) {
			currentAnimation = angry;
			elapsedTime = 0;
			looping = false;
			animationType = AnimationType.ANGRY;
		}
	}
	
	public void sleep() {
		if(currentAnimation != sleep) {
			currentAnimation = sleep;
			elapsedTime = 0;
			looping = false;
			animationType = AnimationType.SLEEP;
		}
	}
	
	public void wave() {
		if(currentAnimation != wave) {
			currentAnimation = wave;
			elapsedTime = 0;
			looping = false;
			animationType = AnimationType.WAVING;
		}
	}
	
	public void walk() {
		if(currentAnimation == walk && !wave.isAnimationFinished(elapsedTime))
			return;
		if(currentAnimation != walk) {
			currentAnimation = walk;
			elapsedTime = 0;
			looping = true;
			animationType = AnimationType.WALKING;
		}
	}
	
	public void idle() {
		if(currentAnimation == wave && !wave.isAnimationFinished(elapsedTime))
			return;
		if(currentAnimation != idle) {
			currentAnimation = idle;
			elapsedTime = 0;
			looping = true;
			animationType = AnimationType.IDLE;
		}
	}
	
	public TextureRegion getCurrentFrame(float delta) {
		
		elapsedTime+= delta;
		
		setCurrentAnimation();
		
		currentFrame  = currentAnimation.getKeyFrame(elapsedTime,looping);
		
		return currentFrame;
	}
	
	private void setCurrentAnimation() {
		
		if(animationType == AnimationType.WAVING) {
			wave();
			return;
		}
		else if(animationType == AnimationType.ANGRY) {
			angry();
			return;
		}
		else if(animationType == AnimationType.SLEEP) {
			sleep();
			return;
		}
		else if(animationType == AnimationType.WALKING) {
			walk();
			return;
		}
		else if (animationType == AnimationType.IDLE) {
			if(currentAnimation != walk && !currentAnimation.isAnimationFinished(elapsedTime))
				return;
			idle();
			return;
		}
	}

	public boolean isFlipped() {
		return flipped;
	}
}
