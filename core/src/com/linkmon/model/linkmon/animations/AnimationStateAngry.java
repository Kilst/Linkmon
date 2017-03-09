package com.linkmon.model.linkmon.animations;

import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxAnimationComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;

public class AnimationStateAngry extends BaseAnimationState {

	public AnimationStateAngry(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		
		if(animationComp.isCurrentAnimationEnded()) {
			animationComp.setState(new AnimationStateIdle(animationComp));
		}
	}

}
