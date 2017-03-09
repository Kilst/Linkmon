package com.linkmon.model.aonevonebattle.moves;

import com.linkmon.model.aonevonebattle.OneVStatusComponent;
import com.linkmon.model.aonevonebattle.moves.status.IStatusEffect;
import com.linkmon.model.gameobject.GameObject;

public interface IMove {
	public void update(GameObject object);
	public void useMove(GameObject player, GameObject opponent);
	public int getDamage();
	public int getType();
	public int getId();
	public String getName();
	public int getEnergy();
	public int getParticleId();
	public void applyStatusEffect(OneVStatusComponent targetStatus);
	public IStatusEffect getStatusEffect();
	public void statusSuccess(boolean roll);
}
