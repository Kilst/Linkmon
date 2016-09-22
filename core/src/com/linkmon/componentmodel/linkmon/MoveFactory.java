package com.linkmon.componentmodel.linkmon;

public class MoveFactory {
	
	public static Move getMoveFromId(int id) {
		switch(id) {
			case(MoveIds.HEADBUTT): {
				return new Move(MoveIds.HEADBUTT, MoveIds.ATTACK, 30, "Headbutt");
			}
			case(MoveIds.RECOVER): {
				return new Move(MoveIds.RECOVER, MoveIds.HEAL, 20, "Recover");
			}
			case(MoveIds.KICK): {
				return new Move(MoveIds.KICK, MoveIds.ATTACK, 20, "Kick");
			}
			case(MoveIds.FIREBALL): {
				return new Move(MoveIds.FIREBALL, MoveIds.ATTACK, 20, "Fireball");
			}
		}
		return null;
	}

}
