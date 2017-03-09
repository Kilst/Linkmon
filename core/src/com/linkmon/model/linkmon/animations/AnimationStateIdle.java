package com.linkmon.model.linkmon.animations;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class AnimationStateIdle extends BaseAnimationState {
	
	float time = 0;

	public AnimationStateIdle(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
		time = System.currentTimeMillis();
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		animationComp.setState(this);
		if(System.currentTimeMillis()- time > 45 && object.isMoving) {
			animationComp.setState(new AnimationStateWave(animationComp));
			return;
		}
		 if(object.isMoving) {
			animationComp.setState(new AnimationStateWalk(animationComp));
			return;
		}
	}
}
