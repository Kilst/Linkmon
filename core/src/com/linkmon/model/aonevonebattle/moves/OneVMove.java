package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.aonevonebattle.OneVStatusComponent;
import com.linkmon.model.aonevonebattle.moves.status.IStatusEffect;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.AnimationStateWalk;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.model.linkmon.animations.battle.AnimationStateWalkTo;
import com.linkmon.view.particles.ParticleIds;

public class OneVMove implements IMove {
	
	private GameObject player;
	private GameObject target;
	
	private int id;
	private int particleId;
	private int damage;
	private int type;
	private int energy = 5;
	private String name;
	
	AnimationStateAttack anim;
	LinkmonAnimationComponent linkmonAnim;
	
	IMoveAction action;
	
	GameObject itemObject;
	
	IStatusEffect statusEffect;
	
	boolean statusHit = false;
	
	public OneVMove(int id, int particleId, int moveType, int damage, int energy, String name, IMoveAction action, IStatusEffect statusEffect) {
		this.id = id;
		this.particleId = particleId;
		this.type = moveType;
		this.damage = damage;
		this.energy = energy;
		
		this.action = action;
		
		this.statusEffect = statusEffect;
		
		this.name = name;
	}
	
	public void setItemObject(GameObject item) {
		itemObject = item;
	}
	
	public void update(GameObject object) {
		
		if(linkmonAnim == null) {
			linkmonAnim = (LinkmonAnimationComponent) ((LibgdxRenderingComponent)object.getRenderer()).getAnimationComponent();
		}
		
		action.doAction(object, target, energy, linkmonAnim, this);
		
//		if(((OneVOneBattleComponent)player.getExtraComponents()).isMoveEnded()) {
//			applyStatusEffect(((OneVOneBattleComponent)target.getExtraComponents()).getStatus());
//			statusHit = false;
//		}
	}
	
	public void useMove(GameObject player, GameObject opponent) {
		action.start(player, opponent, energy, linkmonAnim);
		this.player = player;
		target = opponent;
	}

	public int getId() {
		return id;
	}
	
	public int getParticleId() {
		return particleId;
	}

	public int getDamage() {
		return damage;
	}

	public int getType() {
		return type;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getEnergy() {
		// TODO Auto-generated method stub
		return energy;
	}

	public GameObject getObject() {
		// TODO Auto-generated method stub
		return itemObject;
	}

	@Override
	public void applyStatusEffect(OneVStatusComponent targetStatus) {
		// TODO Auto-generated method stub
//		if(statusHit) {
//			statusEffect.setStatus(targetStatus);
//			statusHit = false;
//		}
		statusEffect.setStatus(targetStatus);
	}

	@Override
	public IStatusEffect getStatusEffect() {
		// TODO Auto-generated method stub
		return statusEffect;
	}

	@Override
	public void statusSuccess(boolean roll) {
		// TODO Auto-generated method stub
		statusHit = roll;
	}

}
