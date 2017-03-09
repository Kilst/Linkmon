package com.linkmon.model.aonevonebattle;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.view.particles.ParticleIds;

public class OneVStatusComponent {

	private boolean isStunned = false;
	private int stunCounter = 0;
	private float stunEffectPercent = 1f;
	private boolean isAsleep = false;
	private int sleepCounter = 0;
	private boolean isPoisoned = false;
	private int poisonCounter = 0;
	private boolean isDarkness = false;
	private int darknessCounter = 0;
	private float darknessEffectPercent = 1f;
	private boolean isZombied = false;
	private int zombieCounter = 0;
	private boolean isConfused = false;
	private int confuseCounter = 0;
	private boolean confuseRoll = false;
	
	
	OneVOneBattleComponent comp;
	
	public OneVStatusComponent(OneVOneBattleComponent comp) {
		this.comp = comp;
	}
	
	public void preMoveChecks() {
		
		checkStunned();
		checkSleep();
		checkDarkness();
		
		if(isConfused) {
			Gdx.app.log("Status Comp", "isConfused!");
		}
	}
	
	public boolean isStunned() {
		return isStunned;
	}
	public void setStunned(boolean isStunned) {
		if(!this.isStunned && !this.isAsleep) {
			this.isStunned = isStunned;
			comp.addEffectAppliedMessage("Stunned!");
			stunCounter = 0;
		}
	}
	
	private void checkStunned() {
		if(isStunned) {
			if(stunCounter < 1) {
				stunEffectPercent = 0.6f;
				Gdx.app.log("Status Comp", "stunned!");
				stunCounter++;
			}
			else {
				isStunned = false;
				stunEffectPercent = 1f;
				comp.addWornOffMessage("Stun wore off");
			}
		}
	}
	
	public boolean isAsleep() {
		return isAsleep;
	}
	public void setAsleep(boolean isAsleep) {
		if(!this.isStunned && !this.isAsleep) {
			this.isAsleep = isAsleep;
			comp.addEffectAppliedMessage("Sleep!");
			sleepCounter = 0;
		}
	}
	
	private void checkSleep() {
		if(isAsleep) {
			if(sleepCounter < 3) {
				comp.setCurrentMove(MoveFactory.getMoveFromId(MoveIds.SLEEP));
				Gdx.app.log("Status Comp", "asleep!");
				sleepCounter++;
			}
			else {
				isAsleep = false;
				comp.addWornOffMessage("Sleep wore off");
			}
		}
	}
	
	public boolean isPoisoned() {
		return isPoisoned;
	}
	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}
	public boolean isDarkness() {
		return isDarkness;
	}
	public void setDarkness(boolean isDarkness) {
		if(!this.isDarkness) {
			this.isDarkness = isDarkness;
			comp.addEffectAppliedMessage("Darkness!");
			darknessCounter = 0;
		}
	}
	
	private void checkDarkness() {
		if(isDarkness) {
			if(darknessCounter < 3) {
				darknessEffectPercent = 0.01f;
				Gdx.app.log("Status Comp", "darkness!");
				darknessCounter++;
			}
			else {
				isDarkness = false;
				darknessEffectPercent = 1f;
				comp.addWornOffMessage("Darkness wore off");
			}
		}
	}
	
	public boolean isZombied() {
		return isZombied;
	}
	public void setZombied(boolean isZombied) {
		this.isZombied = isZombied;
	}
	public boolean isConfused() {
		return isConfused;
	}
	public void setConfused(boolean isConfused) {
		this.isConfused = isConfused;
	}
	
	public boolean getConfuseRoll() {
		return confuseRoll;
	}

	public boolean roll(float chance) {
		// TODO Auto-generated method stub
		if(Math.random() < chance)
			return true;
		
		return false;
	}

	public float getStunnedPercent() {
		// TODO Auto-generated method stub
		return stunEffectPercent;
	}

	public float getDarknessPercent() {
		// TODO Auto-generated method stub
		return darknessEffectPercent;
	}
}
