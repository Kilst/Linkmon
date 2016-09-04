package com.linkmon.componentmodel.linkmon.animations;

import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxAnimationComponent;

public class AnimationStateAngry implements IAnimationState {

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		animationComp.setState(this);
		
		if(animationComp.getCurrentAnimationEnded() == true) {
			animationComp.setState(new AnimationStateIdle());
		}
	}

}
