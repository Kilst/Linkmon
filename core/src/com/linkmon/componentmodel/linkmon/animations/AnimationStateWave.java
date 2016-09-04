package com.linkmon.componentmodel.linkmon.animations;

import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxAnimationComponent;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;

public class AnimationStateWave extends BaseAnimationState {

	public AnimationStateWave(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		
		if(animationComp.getCurrentAnimationEnded() == true) {
			animationComp.setState(new AnimationStateIdle(animationComp));
		}
	}

}
