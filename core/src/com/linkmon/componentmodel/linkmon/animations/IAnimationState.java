package com.linkmon.componentmodel.linkmon.animations;

import com.linkmon.componentmodel.components.IAnimationComponent;
import com.linkmon.componentmodel.gameobject.GameObject;

public interface IAnimationState {
	
	// Animation State - Finite State Machine (FSM)
	
	public void update(IAnimationComponent animationComp, GameObject object);

}
