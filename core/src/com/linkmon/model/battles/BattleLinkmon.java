package com.linkmon.model.battles;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonMoveComponent;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.Move;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;
import com.linkmon.view.particles.ParticleIds;

public class BattleLinkmon {
	
	public boolean updated;
	
	private String playerName;
	
	private int id;

	private int maxHealth;
	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int move1;
	private int move2;
	private int move3;
	
	private Move currentMove;
	
	private BattleLinkmon target;
	
	private IBattle battle;
	
	private boolean isPoisoned;
	private int poisonDamageTaken;
	private int poisonTick;
	private boolean poisonRemoved;
	
	private int damageTaken;
	
	private boolean isDefending;
	
	private boolean isDead;
	
	private int energy = 25;
	
	private final int maxEnergy = 50;
	private int rank;
	
	private EventManager eManager;
	
	private boolean isPlayerControlled;

	private String owner;
	
	public BattleLinkmon(int linkmonId, int attack, int defense, int speed, int move1Id, int move2Id, int health, String playerName) {
		this.id = linkmonId;
		this.attack = (attack);
		this.defense = (defense);
		this.speed = (speed);
		this.move1 = (move1Id);
		this.move2 = (move2Id);
		this.health = health;
		this.playerName = playerName;
		
		maxHealth = health;
	}
	
	public int getLinkmonId() {
		return id;
	}
	
	public BattleLinkmon(GameObject linkmon) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		LinkmonMoveComponent moves = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getMoves();
		this.id = linkmon.getId();
		this.health = stats.getHealth();
		this.attack = stats.getAttack();
		this.defense = stats.getDefense();
		this.speed = stats.getSpeed();
		this.move1 = moves.getMove1();
		this.move2 = moves.getMove2();
		this.move3 = moves.getMove3();
		this.rank = stats.getRank();
		this.playerName = "";
		eManager = linkmon.getWorld().geteManager();
		
		maxHealth = stats.getHealth();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
		if(this.health < 0)
			this.health = 0;
	}
	
	public int getId() {
		return id;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpeed() {
		return speed;
	}

	public int getMove1() {
		return move1;
	}

	public int getMove2() {
		return move2;
	}
	
	public int getMove3() {
		return move3;
	}
	
	@Override
	public String toString() {
		return "Fireboy " + id;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean checkEnergy(int energy) {
		// TODO Auto-generated method stub
		if(this.energy + energy < 0) {
			eManager.notify(new ModelEvent(ModelEvents.NOT_ENOUGH_ENERGY));
			return false;
		}
		else
			return true;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void startOfTurn() {
		setDefending(false);
		setDamageTaken(0);
		setPoisonDamageTaken(0);
	}

	public Move getCurrentMove() {
		return currentMove;
	}

	public void setCurrentMove(Move currentMove) {
		this.currentMove = currentMove;
	}

	public BattleLinkmon getTarget() {
		return target;
	}

	public void setTarget(BattleLinkmon target) {
		this.target = target;
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}

	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}

	public int getPoisonDamageTaken() {
		return poisonDamageTaken;
	}

	public void setPoisonDamageTaken(int poisonDamageTaken) {
		this.poisonDamageTaken = poisonDamageTaken;
	}

	public int getDamageTaken() {
		return damageTaken;
	}

	public void setDamageTaken(int damageTaken) {
		this.damageTaken = damageTaken;
	}

	public boolean isDefending() {
		return isDefending;
	}

	public void setDefending(boolean isDefending) {
		this.isDefending = isDefending;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public void playTurn() {
		// TODO Auto-generated method stub
		if(isDead()) return;
		
		setDefending(false);
		
		if(isPoisoned()) {
			int poisonDropPercent = 80;
			
			if((Math.random()*50)+10*getPoisonTick() > poisonDropPercent) {
				setPoisoned(false);
				poisonRemoved = true;
			}
			else {
				setPoisonDamageTaken(DamageFormula.poisonDamage(1, getPoisonTick(), 30));
				setPoisonTick(getPoisonTick()+1);
			}
		}
		
		// Defend
		if(getCurrentMove().getType() == MoveType.DEFEND) {
			setDefending(true);
		}
		// Poison
		else if(getCurrentMove().getType() == MoveType.POISON) {
			target.setPoisoned(true);
		}
		// Casting
		else if(getCurrentMove().getType() == MoveType.MAGIC) {
			 // Cast heal magic
			if(getCurrentMove().getId() == MoveIds.HEAL)
				target.setDamageTaken(DamageFormula.damage(1, attack, 1, 50, false)*-1);
			// Cast attack magic
			else 
				target.setDamageTaken(DamageFormula.damage(1, attack, target.getDefense(), getCurrentMove().getDamage(), target.isDefending()));
		}
		// Attack
		else { 
			target.setDamageTaken(DamageFormula.damage(1, attack, target.getDefense(), getCurrentMove().getDamage(), target.isDefending()));
		}
		
		//Update targets health
		target.setHealth(target.getHealth() - target.getDamageTaken());
		//Update my health from poison
		if(isPoisoned) {
			setHealth(getHealth() - getPoisonDamageTaken());
		}
	}

	public int getPoisonTick() {
		return poisonTick;
	}

	public void setPoisonTick(int poisonTick) {
		this.poisonTick = poisonTick;
	}

	public boolean isPlayerControlled() {
		return isPlayerControlled;
	}

	public void setPlayerControlled(boolean isPlayerControlled) {
		this.isPlayerControlled = isPlayerControlled;
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return maxHealth;
	}

	public boolean isPoisonRemoved() {
		return poisonRemoved;
	}

	public void setPoisonRemoved(boolean poisonRemoved) {
		this.poisonRemoved = poisonRemoved;
	}

	
}
