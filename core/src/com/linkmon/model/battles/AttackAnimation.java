package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.MyVector2;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.AnimationStateWalk;
import com.linkmon.model.linkmon.animations.AnimationStateWave;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.model.linkmon.animations.battle.AnimationStateDamaged;
import com.linkmon.model.linkmon.animations.battle.AnimationStateWalkTo;

public class AttackAnimation {
	
	boolean isPlaying = false;
	boolean isFinished = false;
	
	boolean walkToIsFinished = true;
	
	boolean castIsFinished = true;
	boolean attackIsFinished = true;
	
	boolean walkHomeIsFinished = true;
	
	GameObject linkmon;
	GameObject target;
	
	MyVector2 linkmonMoveBy;
	
	int direction = 1;
	
	private boolean takenDamage  = false;
	
	long time;
	
	MyVector2 startPos;
	
	public AttackAnimation(GameObject linkmon, GameObject target) {
		this.linkmon = linkmon;
		this.target = target;

		isPlaying = true;
		isFinished = false;
		
		direction = linkmon.direction;
		
		linkmonMoveBy = new MyVector2(150*direction, 0);
		
		startPos = new MyVector2(linkmon.getX(), linkmon.getY());
		
		this.linkmon.getPhysicsComponent().setMoveTo(linkmon.getX()+linkmonMoveBy.x, linkmon.getY()+linkmonMoveBy.y);
		linkmon.isMoving = true;
		
		time = System.currentTimeMillis();
		
		LinkmonAnimationComponent linkmonAnim = (LinkmonAnimationComponent) ((LibgdxRenderingComponent)this.linkmon.getRenderer()).getAnimationComponent();
		linkmonAnim.setState(new AnimationStateWalkTo(linkmonAnim, linkmon.getX()+linkmonMoveBy.x, linkmon.getY()+linkmonMoveBy.y, false));
		
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
		if(isPlaying && walkToIsFinished) {
			LinkmonAnimationComponent linkmonAnim = (LinkmonAnimationComponent) ((LibgdxRenderingComponent)this.linkmon.getRenderer()).getAnimationComponent();
			linkmonAnim.setState(new AnimationStateAttack(linkmonAnim));
		}
		if(isPlaying && castIsFinished) {
			
		}
		if(isPlaying && attackIsFinished) {
			
		}
		if(isPlaying && walkHomeIsFinished) {
			isPlaying = false;
			isFinished = true;
			linkmon.direction = direction;
		}
	}
}
