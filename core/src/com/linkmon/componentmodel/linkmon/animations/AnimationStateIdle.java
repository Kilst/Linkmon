package com.linkmon.componentmodel.linkmon.animations;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

public class AnimationStateIdle implements IAnimationState {

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		animationComp.setState(this);
		if(((LinkmonExtraComponents)object.getExtraComponents()).getTimers().getWaveTimer().checkTimer() && !object.isMoving) {
			animationComp.setState(new AnimationStateWave());
			return;
		}
		else if(object.isMoving) {
			animationComp.setState(new AnimationStateWalk());
			return;
		}
	}
}
