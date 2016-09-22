package com.linkmon.model.linkmon.animations;

import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxAnimationComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;

public class AnimationStateWalk extends BaseAnimationState {

	public AnimationStateWalk(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		animationComp.setState(this);
		
		if(!object.isMoving)
			animationComp.setState(new AnimationStateIdle(animationComp));
	}
}
