package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.MyVector2;
import com.linkmon.model.aonevonebattle.moves.OneVMove;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.Move;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.battle.AnimationStateDamaged;
import com.linkmon.model.linkmon.animations.battle.AnimationStateDefend;
import com.linkmon.model.linkmon.animations.battle.AnimationStateHealed;
import com.linkmon.model.linkmon.animations.battle.AnimationStateWalkTo;
import com.linkmon.view.particles.ParticleFactory;
import com.linkmon.view.particles.ParticleIds;

public class BattleExtraComponent implements IExtraComponents {
	
	IBattle battle;
	
	LocalBattleLinkmon battleLinkmon;
	
	LinkmonStatsComponent stats;
	
	Move move1;
	Move move2;
	
	Move defend;
	
	GameObject linkmon;
	
	GameObject target;
	
	OneVMove currentMove;
	
	boolean isPlayerControlled;
	
	String owner;

	private int healingTaken;
	private int damageTaken;
	private int poisonDamageTaken;
	
	private int energy = 15;
	
	public final int maxEnergy = 50;
	
	private boolean playingTurn;
	
	private BattleStatus status;
	
	private float startX;
	private float startY;
	
	private boolean returnHome = false;
	
	private boolean isTargeted;

	public BattleExtraComponent(LocalBattleLinkmon linkmon, boolean isPlayerControlled, IBattle battle) {
		// TODO Auto-generated constructor stub
		stats = new LinkmonStatsComponent();
		stats.setHealth(linkmon.getHealth());
		stats.setMaxHealth(linkmon.getMaxHealth());
		stats.setAttack(linkmon.getAttack());
		stats.setDefense(linkmon.getDefense());
		stats.setSpeed(linkmon.getSpeed());
		
		this.isPlayerControlled = isPlayerControlled;
		
		this.battle = battle;
		
		status = new BattleStatus();
		
		battleLinkmon = linkmon;
	}
	
	public void playTurn() {
		
		Gdx.app.log("BattleExtraComponent", "Starting turn");
		
		battle.playTurn(battleLinkmon);
		
		if(isDead()) {
			Gdx.app.log("BattleExtraComponent", "Linkmon dead");
			setPlayingTurn(false);
			return;
		}
		
		Gdx.app.log("BattleExtraComponent", "Calculations finished!");
		
		target = battle.getLinkmonArray()[battleLinkmon.getOpponentTargetId()];
		
		Gdx.app.log("BattleExtraComponent", "TargetID: " + battleLinkmon.getOpponentTargetId() + "   " + target.getX());
		currentMove = battleLinkmon.getCurrentMove();
		
		setPlayingTurn(true);
		
		setDefending(false);
		status.setDefending(false);
		status.setAttacking(false);
		status.setCasting(false);
		
		if(isPoisoned()) {
			
			if(battleLinkmon.isPoisonRemoved()) {
				setPoisoned(battleLinkmon.isPoisoned());
				battleLinkmon.setPoisonRemoved(false);
				((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setParticleEffect(ParticleIds.STAR, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY()+linkmon.getHeight()/2);
			}
			else {
				((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setParticleEffect(ParticleIds.POISONED, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY()+linkmon.getHeight()/2);
				setPoisonDamageTaken(battleLinkmon.getPoisonDamageTaken());
				setPoisonTick(battleLinkmon.getPoisonTick());
			}
		}
		
		// Defend
		if(battleLinkmon.getCurrentMove().getType() == MoveType.DEFEND) {
			setDefending(battleLinkmon.isDefending());
			LinkmonAnimationComponent linkmonAnim = (LinkmonAnimationComponent) ((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent();
			linkmonAnim.setState(new AnimationStateDefend(linkmonAnim));
			return;
		}
		// Poison
		else if(battleLinkmon.getCurrentMove().getType() == MoveType.POISON) {
			((BattleExtraComponent)getTarget().getExtraComponents()).setPoisoned(battleLinkmon.getTarget().isPoisoned());
			status.setAttacking(true);
		}
		// Casting
		else if(battleLinkmon.getCurrentMove().getType() == MoveType.MAGIC) {
			status.setCasting(true);
			 // Cast heal magic
			if(battleLinkmon.getCurrentMove().getId() == MoveIds.HEAL) {
				((BattleExtraComponent)getTarget().getExtraComponents()).setHealingTaken(battleLinkmon.getTarget().getHealingTaken());
				Gdx.app.log("BattleExtraComponent", "HealingTaken: " + battleLinkmon.getTarget().getHealingTaken());
			}
			// Cast attack magic
			else
				((BattleExtraComponent)getTarget().getExtraComponents()).setDamageTaken(battleLinkmon.getTarget().getDamageTaken());
		}
		// Attack
		else { 
			status.setAttacking(true);
			((BattleExtraComponent)getTarget().getExtraComponents()).setDamageTaken(battleLinkmon.getTarget().getDamageTaken());
		}

		// Set moveTo position, and start walkTo animation
		LinkmonAnimationComponent linkmonAnim = (LinkmonAnimationComponent) ((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent();
		MyVector2 linkmonMoveBy = new MyVector2(150*linkmon.direction, 0);
		linkmonAnim.setState(new AnimationStateWalkTo(linkmonAnim, linkmon.getX()+linkmonMoveBy.x, linkmon.getY()+linkmonMoveBy.y, false));
		((BattlePhysicsComponent)linkmon.getPhysicsComponent()).setMoveTo(linkmon.getX()+linkmonMoveBy.x, linkmon.getY()+linkmonMoveBy.y);
	}
	
	public void setLinkmon(GameObject object) {
		linkmon = object;
		// Set home position
		setStartX(linkmon.getX());
		setStartY(linkmon.getY());
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	public boolean isPlayerControlled() {
		// TODO Auto-generated method stub
		return isPlayerControlled;
	}

	public void setCurrentMove(OneVMove move) {
		// TODO Auto-generated method stub
		this.currentMove = move;
	}

	public void setTarget(GameObject target) {
		// TODO Auto-generated method stub
		this.target = target;
	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		stats.setHealth(battleLinkmon.getHealth());
		// Check health
		if(stats.getHealth() < 1) {
			// If isDead not set
			if(!status.isDead()) {
				// Create death particle
				linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.DEATH, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY()));
				// Set dead
				status.setDead(true);
				// Remove from world
				linkmon.getWorld().removeObjectFromWorld(linkmon);
			}
		}
		return status.isDead();
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public LinkmonStatsComponent getStats() {
		// TODO Auto-generated method stub
		return stats;
	}

	public void setDefending(boolean defend) {
		// TODO Auto-generated method stub
		status.setDefending(defend);
		if(defend)
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setParticleEffect(ParticleIds.DEFEND, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY());
	}

	public boolean isPoisoned() {
		// TODO Auto-generated method stub
		return status.isPoisoned();
	}
	
	public void setPoisoned(boolean poisoned) {
		// TODO Auto-generated method stub
		status.setPoisoned(poisoned);
		status.setPoisonTick(1);
		if(poisoned)
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setParticleEffect(ParticleIds.POISONED, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY()+linkmon.getHeight()/2);
	}

	public int getPoisonTick() {
		return status.getPoisonTick();
	}

	public void setPoisonTick(int poisonTick) {
		status.setPoisonTick(poisonTick);
	}

	public OneVMove getCurrentMove() {
		// TODO Auto-generated method stub
		return currentMove;
	}

	public GameObject getTarget() {
		// TODO Auto-generated method stub
		return target;
	}

	public boolean isDefending() {
		// TODO Auto-generated method stub
		return status.isDefending();
	}

	public void setDamageTaken(int damage) {
		// TODO Auto-generated method stub
		damageTaken = damage;
	}
	
	public int getDamageTaken() {
		// TODO Auto-generated method stub
		return damageTaken;
	}

	public void setPoisonDamageTaken(int poisonDamage) {
		// TODO Auto-generated method stub
		poisonDamageTaken = poisonDamage;
		setPoisonTick(status.getPoisonTick()+1);
	}
	
	public void updatePoisonDamage() {
		
		if(poisonDamageTaken > 0) {
			// Play damage particles
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setParticleEffect(ParticleIds.POISONED, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY()+linkmon.getHeight()/2);
			// Set damaged animation
			((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent().setState(new AnimationStateDamaged(((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent()));
			// Set damage to renderer (we get damage text rendered if we do)
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setDamage("-"+poisonDamageTaken);
			
			poisonDamageTaken = 0;
		}
		
		// Check if dead
		isDead();
		
		healthCheck();
	}
	
	public void updatePoison() {
		if(this.getBattleLinkmon().isPoisoned()) {
			//this.getBattleLinkmon().setHealth(this.getBattleLinkmon().getHealth() - this.getPoisonDamageTaken());
			this.getStats().setHealth(this.getStats().getHealth() - this.getPoisonDamageTaken());
			this.getBattleLinkmon().setPoisonDamageTaken(0);
		}
		
		// Check if dead
		isDead();
		
		healthCheck();
		
		// Update view
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_UPDATE_HEALTH));
	}
	
	public void updateHealing() {
		//this.getBattleLinkmon().setHealth(this.getBattleLinkmon().getHealth() + this.getBattleLinkmon().getHealingTaken());
		this.getStats().setHealth(this.getStats().getHealth() + this.getHealingTaken());
		
		if(healingTaken > 0) {
			// Heal particle
			linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.HEAL, linkmon.getX()+(linkmon.getWidth()/2), linkmon.getY()+(linkmon.getHeight()/2)));
			// Heal animation
			((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent().setState(new AnimationStateHealed(((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent()));
			// Heal text
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setDamage("+"+healingTaken);
			
			healingTaken = 0;
		}
		
		// Check if dead
		isDead();
		
		healthCheck();
		
		// Update view
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_UPDATE_HEALTH));
	}
	
	private void healthCheck() {
		if(this.getStats().getHealth() > this.getStats().getMaxHealth())
			this.getStats().setHealth(this.getStats().getMaxHealth());
		else if(this.getStats().getHealth() < 0)
			this.getStats().setHealth(0);
	}
	
	public void updateDamage() {
		this.getStats().setHealth(this.getStats().getHealth() - this.getDamageTaken());
		
		if(damageTaken > 0) {
			// Play damage particles
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setParticleEffect(ParticleIds.DAMAGED, linkmon.getX()+linkmon.getWidth()/2, linkmon.getY()+linkmon.getHeight()/2);
			// Set damaged animation
			((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent().setState(new AnimationStateDamaged(((LibgdxRenderingComponent)linkmon.getRenderer()).getAnimationComponent()));
			// Set damage to renderer (we get damage text rendered if we do)
			((BattleLinkmonRenderingComponent)linkmon.getRenderer()).setDamage("-"+damageTaken);
			
			damageTaken = 0;
		}
		
		// Check if dead
		isDead();
		
		healthCheck();
		
		// Update view
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_UPDATE_HEALTH));
	}

	public boolean isPlayingTurn() {
		return playingTurn;
	}

	public void setPlayingTurn(boolean playingTurn) {
		this.playingTurn = playingTurn;
	}

	public BattleStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public boolean isReturnHome() {
		return returnHome;
	}

	public void setReturnHome(boolean returnHome) {
		this.returnHome = returnHome;
	}

	public int getEnergy() {
		return energy;
	}

	public void updateEnergy(int energy) {
		this.energy = energy;
	}

	public LocalBattleLinkmon getBattleLinkmon() {
		// TODO Auto-generated method stub
		return battleLinkmon;
	}

	public int getPoisonDamageTaken() {
		// TODO Auto-generated method stub
		return poisonDamageTaken;
	}

	public IBattle getBattle() {
		// TODO Auto-generated method stub
		return battle;
	}

	public int getHealingTaken() {
		return healingTaken;
	}

	public void setHealingTaken(int healingTaken) {
		this.healingTaken = healingTaken;
	}

	public boolean isTargeted() {
		return isTargeted;
	}

	public void setTargeted(boolean isTargeted) {
		this.isTargeted = isTargeted;
	}
}
