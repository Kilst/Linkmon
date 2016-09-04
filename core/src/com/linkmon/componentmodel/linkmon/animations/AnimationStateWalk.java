package com.linkmon.componentmodel.linkmon.animations;

import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxAnimationComponent;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;

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
