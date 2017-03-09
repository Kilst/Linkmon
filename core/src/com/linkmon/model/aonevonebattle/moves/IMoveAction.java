package com.linkmon.model.aonevonebattle.moves;

import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;

public interface IMoveAction {
	
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove oneVMove);
	public void setRunning(boolean value);
	
	public boolean start(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp);

}
