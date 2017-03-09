package com.linkmon.model.battles;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.aonevonebattle.moves.OneVMove;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.Move;

public class LocalBattleLinkmon {
	
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
	
	private OneVMove currentMove;
	
	private LocalBattleLinkmon target;
	private int targetId;
	
	private boolean isPoisoned;
	private int poisonDamageTaken;
	private int poisonTick;
	private boolean poisonRemoved;
	
	private int damageTaken;
	private int healingTaken;
	
	private boolean isDefending;
	
	private boolean isDead;
	
	private int energy = 15;
	
	private final int maxEnergy = 50;
	private int rank;
	
	private EventManager eManager;
	
	private boolean isPlayerControlled;

	private String owner;
	
	private int damageDone;
	private int healingDone;

	private int opponentTargetId;
	
	public LocalBattleLinkmon(int linkmonId, int attack, int defense, int speed, int move1Id, int move2Id, int health, String playerName, boolean isPlayerControlled, int targetId) {
		this.id = linkmonId;
		this.attack = (attack);
		this.defense = (defense);
		this.speed = (speed);
		this.move1 = (move1Id);
		this.move2 = (move2Id);
		this.health = health;
		this.playerName = playerName;
		
		maxHealth = health;
		
		this.isPlayerControlled = isPlayerControlled;
		
		this.targetId = targetId;
	}
	
	public int getLinkmonId() {
		return id;
	}
	
	public LocalBattleLinkmon(GameObject linkmon, boolean isPlayerControlled, int targetId) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		this.id = linkmon.getId();
		this.health = stats.getHealth();
		this.attack = stats.getAttack();
		this.defense = stats.getDefense();
		this.speed = stats.getSpeed();
		this.move1 = 1;
		this.move2 = 2;
		this.rank = stats.getRank();
		this.playerName = "";
		eManager = linkmon.getWorld().geteManager();
		
		maxHealth = stats.getHealth();
		
		this.isPlayerControlled = isPlayerControlled;
		
		this.targetId = targetId;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
		
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
	
	@Override
	public String toString() {
		return "Fireboy " + id;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
		if(this.energy > maxEnergy)
			this.energy = maxEnergy;
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
		setCurrentMove(null);
		setTarget(null);
	}

	public OneVMove getCurrentMove() {
		return currentMove;
	}

	public void setCurrentMove(OneVMove oneVMove) {
		this.currentMove = oneVMove;
	}

	public LocalBattleLinkmon getTarget() {
		return target;
	}

	public void setTarget(LocalBattleLinkmon battleLinkmons) {
		this.target = battleLinkmons;
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

	public int getHealingTaken() {
		// TODO Auto-generated method stub
		return healingTaken;
	}

	public void setHealingTaken(int healingTaken) {
		// TODO Auto-generated method stub
		this.healingTaken = healingTaken;
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

	public int getTargetId() {
		// TODO Auto-generated method stub
		return targetId;
	}
	
	public int getOpponentTargetId() {
		// TODO Auto-generated method stub
		return opponentTargetId;
	}

	public void setOpponentTargetId(int targetId) {
		this.opponentTargetId = targetId;
	}
	
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getDamageDone() {
		return damageDone;
	}

	public void setDamageDone(int damageDone) {
		this.damageDone = damageDone;
	}

	public int getHealingDone() {
		return healingDone;
	}

	public void setHealingDone(int healingDone) {
		this.healingDone = healingDone;
	}

	public void setTargeted(boolean targeted) {
		// TODO Auto-generated method stub
		
	}
}
