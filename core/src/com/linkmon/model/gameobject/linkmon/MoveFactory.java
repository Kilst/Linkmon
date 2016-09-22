package com.linkmon.model.gameobject.linkmon;

public class MoveFactory {
	
	public static Move getMoveFromId(int id) {
		switch(id) {
			case(MoveIds.HEADBUTT): {
				return new Move(MoveIds.HEADBUTT, MoveType.NOTYPE, 50, 0, -5, "Headbutt");
			}
			case(MoveIds.RECOVER): {
				return new Move(MoveIds.RECOVER, MoveType.NOTYPE, 20, 0, -5, "Recover");
			}
			case(MoveIds.KICK): {
				return new Move(MoveIds.KICK, MoveType.NOTYPE, 10, 0, 15, "Kick");
			}
			case (MoveIds.FIREBALL):
            {
                return new Move(MoveIds.FIREBALL, MoveType.FIRE, 15, 30, -5, "Fireball");
            }
		}
		return null;
	}

}
