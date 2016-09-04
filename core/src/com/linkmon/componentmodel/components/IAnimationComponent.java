package com.linkmon.componentmodel.components;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.animations.IAnimationState;

public interface IAnimationComponent {
	
	public void updateAnimations(GameObject object);

	public void update(GameObject object);
	public void setState(IAnimationState state);
	public IAnimationState getState();
	
	public boolean getCurrentAnimationEnded();
}
