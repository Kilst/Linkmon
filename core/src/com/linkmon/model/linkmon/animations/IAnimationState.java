package com.linkmon.model.linkmon.animations;

import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;

public interface IAnimationState {
	
	// Animation State - Finite State Machine (FSM)
	
	public void update(IAnimationComponent animationComp, GameObject object);

}
