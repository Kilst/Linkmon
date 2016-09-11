package com.linkmon.view;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.linkmon.controller.LinkmonAnimationController;
import com.linkmon.controller.LinkmonController;
import com.linkmon.helpers.ResourceLoader;

public class GameSprite  extends Actor {
	
	
	public LinkmonAnimationController anim;
	
	private Sprite sprite;
	
	TextureRegion[] idleAnimationFrames;
    Animation idleAnimation;
    TextureRegion[] walkAnimationFrames;
    Animation walkAnimation;
    TextureRegion[] waveAnimationFrames;
    Animation waveAnimation;
    TextureRegion[] sleepAnimationFrames;
    Animation sleepAnimation;
    TextureRegion[] angryAnimationFrames;
    Animation angryAnimation;
    float elapsedTime;
    
    TextureRegion currentFrame;
    
    LinkmonController linkmonController;
    
    public float imageOffsetX = 80;
    
    public float imageOffsetY = 0;
    
	
	public GameSprite(int id) {
		Array<AtlasRegion>[] animations = new Array[3];
		animations = ResourceLoader.getLinkmonAnimFromId(id);
		
		idleAnimationFrames = new TextureRegion[animations[0].size];
		int i = 0;
		for(AtlasRegion region : animations[0]) {
			idleAnimationFrames[i] = region;
			i++;
		}
		idleAnimation = new Animation(2f/76f,idleAnimationFrames);
		
		walkAnimationFrames = new TextureRegion[animations[1].size];
		i = 0;
		for(AtlasRegion region : animations[1]) {
			walkAnimationFrames[i] = region;
			i++;
		}
		walkAnimation = new Animation(2f/76f,walkAnimationFrames);
		
		waveAnimationFrames = new TextureRegion[animations[2].size];
		i = 0;
		for(AtlasRegion region : animations[2]) {
			waveAnimationFrames[i] = region;
			i++;
		}
		waveAnimation = new Animation(2f/76f,waveAnimationFrames);
		
		sleepAnimationFrames = new TextureRegion[animations[3].size];
		i = 0;
		for(AtlasRegion region : animations[3]) {
			sleepAnimationFrames[i] = region;
			i++;
		}
		sleepAnimation = new Animation(2f/76f,sleepAnimationFrames);
		
		angryAnimationFrames = new TextureRegion[animations[4].size];
		i = 0;
		for(AtlasRegion region : animations[4]) {
			angryAnimationFrames[i] = region;
			i++;
		}
		angryAnimation = new Animation(2f/76f,angryAnimationFrames);
		
		
		anim = new LinkmonAnimationController(idleAnimation, walkAnimation, waveAnimation, sleepAnimation, angryAnimation);
		
		
		sprite = new Sprite(animations[0].first());
		sprite.setScale(UIRenderer.scaleX, UIRenderer.scaleY);
		sprite.setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		sprite.setY(45);
		this.setY(45);
	}
	
	public void evolveLinkmon(int id) {
		Array<AtlasRegion>[] animations;
		animations = ResourceLoader.getLinkmonAnimFromId(id);
		
		idleAnimationFrames = new TextureRegion[animations[0].size];
		int i = 0;
		for(AtlasRegion region : animations[0]) {
			idleAnimationFrames[i] = region;
			i++;
		}
		idleAnimation = new Animation(2f/76f,idleAnimationFrames);
		
		walkAnimationFrames = new TextureRegion[animations[1].size];
		i = 0;
		for(AtlasRegion region : animations[1]) {
			walkAnimationFrames[i] = region;
			i++;
		}
		walkAnimation = new Animation(2f/76f,walkAnimationFrames);
		
		waveAnimationFrames = new TextureRegion[animations[2].size];
		i = 0;
		for(AtlasRegion region : animations[2]) {
			waveAnimationFrames[i] = region;
			i++;
		}
		waveAnimation = new Animation(2f/76f,waveAnimationFrames);
		
		sleepAnimationFrames = new TextureRegion[animations[3].size];
		i = 0;
		for(AtlasRegion region : animations[3]) {
			sleepAnimationFrames[i] = region;
			i++;
		}
		sleepAnimation = new Animation(2f/76f,sleepAnimationFrames);
		
		angryAnimationFrames = new TextureRegion[animations[4].size];
		i = 0;
		for(AtlasRegion region : animations[4]) {
			angryAnimationFrames[i] = region;
			i++;
		}
		angryAnimation = new Animation(2f/76f,angryAnimationFrames);
		
		
		anim = new LinkmonAnimationController(idleAnimation, walkAnimation, waveAnimation, sleepAnimation, angryAnimation);
		
		sprite = new Sprite(animations[0].first());
		sprite.setScale(UIRenderer.scaleX, UIRenderer.scaleY);
		sprite.setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		sprite.setY(45);
		this.setY(45);
	}

	
    public void draw(Batch batch, float alpha){
    	
    	currentFrame = anim.getCurrentFrame(Gdx.graphics.getDeltaTime());

		if(anim.isFlipped())
			batch.draw(currentFrame, getX()+currentFrame.getRegionWidth(), getY(), -currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		else
			batch.draw(currentFrame, getX(), getY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }
    
    public void draw(Batch batch, float alpha, float x, float y){
    	
    	currentFrame = anim.getCurrentFrame(Gdx.graphics.getDeltaTime());

		if(anim.isFlipped())
			batch.draw(currentFrame, x+currentFrame.getRegionWidth(), y, -currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		else
			batch.draw(currentFrame, x, y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
	}
}
