package com.linkmon.model.linkmon.animations;

import com.linkmon.model.components.IAnimationComponent;

public abstract class BaseAnimationState implements IAnimationState {
	
	public BaseAnimationState(IAnimationComponent animComp) {
		animComp.setState(this);
	}

}
