package com.linkmon.model.linkmon.animations.battle;

import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.BaseAnimationState;

public class AnimationStateDamaged extends BaseAnimationState {
	
	
	public AnimationStateDamaged(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
		//((BattleExtraComponent)object.getExtraComponents()).updateHealth(moveId);
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		
		if(animationComp.isCurrentAnimationEnded() == true) {
			animationComp.setState(new AnimationStateIdle(animationComp));
		}
	}

}
