package com.linkmon.model.linkmon;

import com.linkmon.model.aonevonebattle.moves.OneVMove;

public class LinkmonMoveComponent {
	
	private int basicAttack;
	private int mediumAttack1;
	private int mediumAttack2;
	
	public LinkmonMoveComponent() {
//		basicAttack = MoveFactory.getMoveFromId(MoveIds.FIREBALL);
//		mediumAttack1 = MoveFactory.getMoveFromId(MoveIds.LEAF_BOMB);
//		mediumAttack2 = MoveFactory.getMoveFromId(MoveIds.WATER_JET);
		basicAttack = MoveIds.FIREBALL;
		mediumAttack1 = MoveIds.LEAF_BOMB;
		mediumAttack2 = MoveIds.WATER_JET;
	}
	
	public int getMove1() {
		return basicAttack;
	}
	public void setMove1(int basicAttack) {
		this.basicAttack = basicAttack;
	}
	public int getMove2() {
		return mediumAttack1;
	}
	public void setMove2(int mediumAttack1) {
		this.mediumAttack1 = mediumAttack1;
	}
	public int getMove3() {
		return mediumAttack2;
	}
	public void setMove3(int mediumAttack2) {
		this.mediumAttack2 = mediumAttack2;
	}
	public void swapMove(int oldMove, int newMove) {
		// TODO Auto-generated method stub
		if(basicAttack == oldMove)
			basicAttack = newMove;
		
		if(mediumAttack1 == oldMove)
			mediumAttack1 = newMove;
		
		if(mediumAttack2 == oldMove)
			mediumAttack2 = newMove;
	}
}
