package com.linkmon.componentmodel.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.animations.IAnimationState;

public abstract class LibgdxAnimationComponent implements IAnimationComponent {
	
	protected Animation currentAnimation;
	
	protected IAnimationState state;
	
	protected float elapsedTime = 0;
	
	protected boolean isPlaying = false;
	protected boolean looping = true;
    
	protected boolean flipped = false;
	
	protected abstract void updateCurrentAnimation();

	public Animation getCurrentAnimation() {
		// TODO Auto-generated method stub
		return currentAnimation;
	}
	
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		state.update(this, object);
		updateCurrentAnimation();
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		if(currentAnimation.isAnimationFinished(elapsedTime) && looping == true)
			elapsedTime = 0;
		
		if(object.getWidth() == 0){
			object.setWidth(currentAnimation.getKeyFrames()[0].getRegionWidth());
			object.setHeight(currentAnimation.getKeyFrames()[0].getRegionHeight());
		}
	}

	@Override
	public void setState(IAnimationState state) {
		// TODO Auto-generated method stub
		this.state = state;
	}

	public TextureRegion getCurrentKeyFrame() {
		// TODO Auto-generated method stub
		return currentAnimation.getKeyFrame(elapsedTime);
	}
	
	public boolean getCurrentAnimationEnded() {
		return currentAnimation.isAnimationFinished(elapsedTime);
	}
}
