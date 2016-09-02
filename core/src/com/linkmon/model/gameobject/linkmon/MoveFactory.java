package com.linkmon.model.gameobject.linkmon;

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
		}
		return null;
	}

}
