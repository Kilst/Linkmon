package com.linkmon.componentmodel.linkmon.animations;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

public class AnimationStateIdle extends BaseAnimationState {

	public AnimationStateIdle(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		animationComp.setState(this);
		if(((LinkmonExtraComponents)object.getExtraComponents()).getTimers().getWaveTimer().checkTimer() && !object.isMoving) {
			animationComp.setState(new AnimationStateWave(animationComp));
			return;
		}
		else if(object.isMoving) {
			animationComp.setState(new AnimationStateWalk(animationComp));
			return;
		}
	}
}
