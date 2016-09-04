package com.linkmon.componentmodel.linkmon.animations;

import com.linkmon.componentmodel.components.IAnimationComponent;

public abstract class BaseAnimationState implements IAnimationState {
	
	public BaseAnimationState(IAnimationComponent animComp) {
		animComp.setState(this);
	}

}
