package com.linkmon.model.battles;

public class BattleStatus {
	
	private boolean isDead;
	private boolean isPoisoned;
	private int poisonTick = 0;
	private boolean isDefending;
	private boolean isCasting;
	private boolean isAttacking;

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}

	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}

	public int getPoisonTick() {
		return poisonTick;
	}

	public void setPoisonTick(int poisonTick) {
		this.poisonTick = poisonTick;
	}

	public boolean isDefending() {
		return isDefending;
	}

	public void setDefending(boolean isDefending) {
		this.isDefending = isDefending;
	}

	public boolean isCasting() {
		return isCasting;
	}

	public void setCasting(boolean isCasting) {
		this.isCasting = isCasting;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

}
