package com.linkmon.model.linkmon.animations;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class AnimationStateIdle extends BaseAnimationState {

	public AnimationStateIdle(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		animationComp.setState(this);
		if(((LinkmonExtraComponents)object.getExtraComponents()).getTimers().getWaveTimer().checkTimer() && object.isMoving) {
			animationComp.setState(new AnimationStateWave(animationComp));
			return;
		}
		 if(object.isMoving) {
			animationComp.setState(new AnimationStateWalk(animationComp));
			return;
		}
	}
}
