package com.linkmon.model.linkmon;

public class LinkmonMoveComponent {
	
	private Move basicAttack;
	private Move mediumAttack1;
	private Move mediumAttack2;
	private Move specialAttack;
	
	public LinkmonMoveComponent() {
		basicAttack = MoveFactory.getMoveFromId(MoveIds.KICK);
		mediumAttack1 = MoveFactory.getMoveFromId(MoveIds.FIREWHIRL);
		mediumAttack2 = MoveFactory.getMoveFromId(MoveIds.FIREBALL);
		specialAttack = MoveFactory.getMoveFromId(MoveIds.HEADBUTT);
	}
	
	public Move getBasicAttack() {
		return basicAttack;
	}
	public void setBasicAttack(Move basicAttack) {
		this.basicAttack = basicAttack;
	}
	public Move getMediumAttack1() {
		return mediumAttack1;
	}
	public void setMediumAttack1(Move mediumAttack1) {
		this.mediumAttack1 = mediumAttack1;
	}
	public Move getMediumAttack2() {
		return mediumAttack2;
	}
	public void setMediumAttack2(Move mediumAttack2) {
		this.mediumAttack2 = mediumAttack2;
	}
	public Move getSpecialAttack() {
		return specialAttack;
	}
	public void setSpecialAttack(Move specialAttack) {
		this.specialAttack = specialAttack;
	}

	public void swapMove(int oldMove, int newMove) {
		// TODO Auto-generated method stub
		if(basicAttack.getId() == oldMove)
			basicAttack = MoveFactory.getMoveFromId(newMove);
		
		if(mediumAttack1.getId() == oldMove)
			mediumAttack1 = MoveFactory.getMoveFromId(newMove);
		
		if(mediumAttack2.getId() == oldMove)
			mediumAttack2 = MoveFactory.getMoveFromId(newMove);
		
		if(specialAttack.getId() == oldMove)
			specialAttack = MoveFactory.getMoveFromId(newMove);
	}
}
