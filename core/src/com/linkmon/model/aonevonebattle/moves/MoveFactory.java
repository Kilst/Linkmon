package com.linkmon.model.aonevonebattle.moves;

import com.linkmon.model.aonevonebattle.moves.status.DarknessStatus;
import com.linkmon.model.aonevonebattle.moves.status.NoStatus;
import com.linkmon.model.aonevonebattle.moves.status.SleepStatus;
import com.linkmon.model.aonevonebattle.moves.status.StunnedStatus;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;
import com.linkmon.view.particles.ParticleIds;

public class MoveFactory {
	

	public static OneVMove getMoveFromId(int id) {
		switch(id) {
		
			case(MoveIds.FIREBALL): {
				return new OneVMove(MoveIds.FIREBALL, ParticleIds.FIREBALL, MoveType.FIRE, 3, 5, "Fireball", new ThrowObjectMoveAction(), new StunnedStatus());
			}
			
			case(MoveIds.WATER_JET): {
				return new OneVMove(MoveIds.WATER_JET, ParticleIds.WATER_JET, MoveType.WATER, 5, -5, "Water Jet", new BeamAttackMoveAction(), new SleepStatus());
			}
			case(MoveIds.BUBBLE): {
				return new OneVMove(MoveIds.BUBBLE, ParticleIds.BUBBLES, MoveType.WATER, 5, -5, "Bubble", new ThrowObjectMoveAction(), new SleepStatus());
			}
			case(MoveIds.FROST): {
				return new OneVMove(MoveIds.FROST, ParticleIds.FAST_ICE, MoveType.WATER, 5, -5, "Frost", new BeamAttackMoveAction(), new NoStatus());
			}
			case(MoveIds.FLAME): {
				return new OneVMove(MoveIds.FLAME, ParticleIds.FLAME, MoveType.FIRE, 3, 5, "Flame", new FlameMoveAction(), new NoStatus());
			}
			case(MoveIds.FLAMETHROWER): {
				return new OneVMove(MoveIds.FLAMETHROWER, ParticleIds.FIRE_ATTACK, MoveType.FIRE, 3, 5, "Flame Thrower", new BeamAttackMoveAction(), new NoStatus());
			}
			case(MoveIds.SEED_CANNON): {
				return new OneVMove(MoveIds.SEED_CANNON, ParticleIds.SEED_CANNON, MoveType.GRASS, 5, -5, "Seed Cannon", new BeamAttackMoveAction(), new NoStatus());
			}
			case(MoveIds.LEAF_BOMB): {
				return new OneVMove(MoveIds.LEAF_BOMB, -1, MoveType.GRASS, 3, 5, "Leaf Bomb", new TrajectoryMoveAction(ParticleIds.LEAF_BOMB, ObjectId.LEAF_BALL), new DarknessStatus());
			}
			
			// Defend
			case(MoveIds.DEFEND): {
				return new OneVMove(MoveIds.FIREBALL, 1, MoveType.DEFEND, 0, 10, "Defend", new DefendMoveAction(), new NoStatus());
			}
			
			// Stunned
			case(MoveIds.SLEEP): {
				return new OneVMove(MoveIds.SLEEP, ParticleIds.SLEEP, MoveType.SPECIAL, 0, 10, "Stunned", new SleepMoveAction(), new NoStatus());
			}
			
			
//		
//			// Basic
//			case(MoveIds.HEADBUTT): {
//				return new Move(MoveIds.HEADBUTT, MoveElementalType.NOTYPE, MoveSlot.BASIC, 20, 3, 10, "Headbutt", MoveType.PHYSICAL);
//			}
//			case(MoveIds.KICK): {
//				return new Move(MoveIds.KICK, MoveElementalType.NOTYPE, MoveSlot.BASIC, 30, 0, 10, "Kick", MoveType.PHYSICAL);
//			}
//			case(MoveIds.FLARE): {
//				return new Move(MoveIds.FLARE, MoveElementalType.FIRE, MoveSlot.BASIC, 25, 4, 10, "Flare", MoveType.PHYSICAL);
//			}
//			case(MoveIds.SPLASH): {
//				return new Move(MoveIds.SPLASH, MoveElementalType.WATER, MoveSlot.BASIC, 25, 4, 10, "Splash", MoveType.PHYSICAL);
//			}
//			case(MoveIds.SHOCK): {
//				return new Move(MoveIds.SHOCK, MoveElementalType.ELECTRIC, MoveSlot.BASIC, 25, 4, 10, "Shock", MoveType.PHYSICAL);
//			}
//			case (MoveIds.MUDSLING): {
//                return new Move(MoveIds.MUDSLING, MoveElementalType.EARTH, MoveSlot.BASIC, 25, 4, 10, "Mud Sling", MoveType.PHYSICAL);
//            }
//			
//			// Medium
//			case (MoveIds.FIREBALL): {
//                return new Move(MoveIds.FIREBALL, MoveElementalType.FIRE, MoveSlot.MEDIUM, 50, 0, -5, "Fireball", MoveType.MAGIC);
//            }
//			case (MoveIds.FIREWHIRL): {
//                return new Move(MoveIds.FIREWHIRL, MoveElementalType.FIRE, MoveSlot.MEDIUM, 15, 30, -5, "Firewhirl", MoveType.MAGIC);
//            }
//			case (MoveIds.WATERFALL): {
//                return new Move(MoveIds.WATERFALL, MoveElementalType.WATER, MoveSlot.MEDIUM, 50, 0, -5, "Waterfall", MoveType.MAGIC);
//            }
//			case (MoveIds.WATERCANNON): {
//                return new Move(MoveIds.WATERCANNON, MoveElementalType.WATER, MoveSlot.MEDIUM, 15, 30, -5, "Water Cannon", MoveType.MAGIC);
//            }
//			case (MoveIds.THUNDERBALL): {
//                return new Move(MoveIds.THUNDERBALL, MoveElementalType.ELECTRIC, MoveSlot.MEDIUM, 50, 0, -5, "Thunderball", MoveType.MAGIC);
//            }
//			case (MoveIds.THUNDER): {
//                return new Move(MoveIds.THUNDER, MoveElementalType.ELECTRIC, MoveSlot.MEDIUM, 15, 30, -5, "Thunder", MoveType.MAGIC);
//            }
//			case (MoveIds.ROCKCRUSH): {
//                return new Move(MoveIds.ROCKCRUSH, MoveElementalType.EARTH, MoveSlot.MEDIUM, 50, 0, -5, "Rock Crush", MoveType.MAGIC);
//            }
//			case (MoveIds.QUAKE): {
//                return new Move(MoveIds.QUAKE, MoveElementalType.EARTH, MoveSlot.MEDIUM, 15, 30, -5, "Quake", MoveType.MAGIC);
//            }
//			case (MoveIds.CHARGEPUNCH): {
//                return new Move(MoveIds.CHARGEPUNCH, MoveElementalType.NOTYPE, MoveSlot.MEDIUM, 50, 0, -5, "Charge Punch", MoveType.PHYSICAL);
//            }
//			case (MoveIds.HIGHKICK): {
//                return new Move(MoveIds.HIGHKICK, MoveElementalType.NOTYPE, MoveSlot.MEDIUM, 15, 30, -5, "High Kick", MoveType.PHYSICAL);
//            }
//			case (MoveIds.POISON): {
//                return new Move(MoveIds.POISON, MoveElementalType.POISON, MoveSlot.MEDIUM, 15, 30, -5, "Poison", MoveType.POISON);
//            }
//			
//			// Special
//			case (MoveIds.ETERNALFIRE): {
//                return new Move(MoveIds.ETERNALFIRE, MoveElementalType.FIRE, MoveSlot.SPECIAL, 50, 30, -35, "Eternal Fire", MoveType.SPECIAL);
//            }
//			case (MoveIds.ETERNALWATER): {
//                return new Move(MoveIds.ETERNALWATER, MoveElementalType.WATER, MoveSlot.SPECIAL, 50, 30, -35, "Eternal Water", MoveType.SPECIAL);
//            }
//			case (MoveIds.ETERNALTHUNDER): {
//                return new Move(MoveIds.ETERNALTHUNDER, MoveElementalType.ELECTRIC, MoveSlot.SPECIAL, 50, 30, -35, "Eternal Thunder", MoveType.SPECIAL);
//            }
//			case (MoveIds.ETERNALQUAKE): {
//                return new Move(MoveIds.ETERNALQUAKE, MoveElementalType.EARTH, MoveSlot.SPECIAL, 50, 30, -35, "Eternal Quake", MoveType.SPECIAL);
//            }
//			case (MoveIds.ETERNALPUNCH): {
//                return new Move(MoveIds.ETERNALPUNCH, MoveElementalType.NOTYPE, MoveSlot.SPECIAL, 50, 30, -35, "Eternal Punch", MoveType.SPECIAL);
//            }
		}
		return null;
	}
}
