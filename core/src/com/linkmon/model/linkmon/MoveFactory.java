package com.linkmon.model.linkmon;

public class MoveFactory {
	

	public static Move getMoveFromId(int id) {
		switch(id) {
		
			// Defend
			case(MoveIds.DEFEND): {
				return new Move(MoveIds.DEFEND, MoveType.NOTYPE, 0, 0, 0, -5, "Defend");
			}
		
			// Basic
			case(MoveIds.HEADBUTT): {
				return new Move(MoveIds.HEADBUTT, MoveType.NOTYPE, MoveSlot.BASIC, 6, 3, -5, "Headbutt");
			}
			case(MoveIds.KICK): {
				return new Move(MoveIds.KICK, MoveType.NOTYPE, MoveSlot.BASIC, 10, 0, 15, "Kick");
			}
			case(MoveIds.RECOVER): {
				return new Move(MoveIds.RECOVER, MoveType.NOTYPE, MoveSlot.BASIC, 20, 0, -5, "Recover");
			}
			case(MoveIds.FLARE): {
				return new Move(MoveIds.FLARE, MoveType.FIRE, MoveSlot.BASIC, 5, 4, 15, "Flare");
			}
			case(MoveIds.SPLASH): {
				return new Move(MoveIds.SPLASH, MoveType.WATER, MoveSlot.BASIC, 5, 4, 15, "Splash");
			}
			case(MoveIds.SHOCK): {
				return new Move(MoveIds.SHOCK, MoveType.ELECTRIC, MoveSlot.BASIC, 5, 4, 15, "Shock");
			}
			case (MoveIds.MUDSLING): {
                return new Move(MoveIds.MUDSLING, MoveType.EARTH, MoveSlot.BASIC, 5, 4, 15, "Mud Sling");
            }
			
			// Medium
			case (MoveIds.FIREBALL): {
                return new Move(MoveIds.FIREBALL, MoveType.FIRE, MoveSlot.MEDIUM, 50, 0, -5, "Fireball");
            }
			case (MoveIds.FIREWHIRL): {
                return new Move(MoveIds.FIREWHIRL, MoveType.FIRE, MoveSlot.MEDIUM, 15, 30, -5, "Firewhirl");
            }
			case (MoveIds.WATERFALL): {
                return new Move(MoveIds.WATERFALL, MoveType.WATER, MoveSlot.MEDIUM, 50, 0, -5, "Waterfall");
            }
			case (MoveIds.WATERCANNON): {
                return new Move(MoveIds.WATERCANNON, MoveType.WATER, MoveSlot.MEDIUM, 15, 30, -5, "Water Cannon");
            }
			case (MoveIds.THUNDERBALL): {
                return new Move(MoveIds.THUNDERBALL, MoveType.ELECTRIC, MoveSlot.MEDIUM, 50, 0, -5, "Thunderball");
            }
			case (MoveIds.THUNDER): {
                return new Move(MoveIds.THUNDER, MoveType.ELECTRIC, MoveSlot.MEDIUM, 15, 30, -5, "Thunder");
            }
			case (MoveIds.ROCKCRUSH): {
                return new Move(MoveIds.ROCKCRUSH, MoveType.EARTH, MoveSlot.MEDIUM, 50, 0, -5, "Rock Crush");
            }
			case (MoveIds.QUAKE): {
                return new Move(MoveIds.QUAKE, MoveType.EARTH, MoveSlot.MEDIUM, 15, 30, -5, "Quake");
            }
			case (MoveIds.CHARGEPUNCH): {
                return new Move(MoveIds.CHARGEPUNCH, MoveType.NOTYPE, MoveSlot.MEDIUM, 50, 0, -5, "Charge Punch");
            }
			case (MoveIds.HIGHKICK): {
                return new Move(MoveIds.HIGHKICK, MoveType.NOTYPE, MoveSlot.MEDIUM, 15, 30, -5, "High Kick");
            }
			
			// Special
		}
		return null;
}

}
