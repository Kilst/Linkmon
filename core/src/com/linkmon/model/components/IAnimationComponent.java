package com.linkmon.model.components;

import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.animations.IAnimationState;

public interface IAnimationComponent {
	
	public void updateAnimations(GameObject object);

	public void update(GameObject object);
	public void setState(IAnimationState state);
	public IAnimationState getState();
	
	public boolean getCurrentAnimationEnded();
}
