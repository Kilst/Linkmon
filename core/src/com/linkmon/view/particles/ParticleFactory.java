package com.linkmon.view.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.linkmon.model.linkmon.MoveElementalType;

public class ParticleFactory {
	
	public static int getParticleFromMoveType(int type) {
		int particleId = -1;
		switch(type) {
			case(MoveElementalType.ELECTRIC) : {
				particleId = ParticleIds.ELECTRIC_ATTACK;
				break;
			}
			case(MoveElementalType.EARTH) : {
				particleId = ParticleIds.EARTH_ATTACK;
				break;
			}
			case(MoveElementalType.FIRE) : {
				particleId = ParticleIds.FIRE_ATTACK;
				break;
			}
			case(MoveElementalType.WATER) : {
				particleId = ParticleIds.ICE_ATTACK;
				break;
			}
			case(MoveElementalType.NOTYPE) : {
				particleId = ParticleIds.PHYSICAL_ATTACK;
				break;
			}
			case(MoveElementalType.POISON) : {
				particleId = ParticleIds.POISON_ATTACK;
				break;
			}
		}
		
		return particleId;
	}

	public static void getParticleFromId(int id, ParticleEffect particleEffect) {
		switch(id) {
		
			case(ParticleIds.TAP) : {
				particleEffect.load(Gdx.files.internal("Particles/tap.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.DARKNESS) : {
				particleEffect.load(Gdx.files.internal("Particles/darkness.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.BOUNCE) : {
				particleEffect.load(Gdx.files.internal("Particles/bounce.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.SLEEP) : {
				particleEffect.load(Gdx.files.internal("Particles/sleep.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.LEAF_BOMB) : {
				particleEffect.load(Gdx.files.internal("Particles/leafBomb.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.SEED_CANNON) : {
				particleEffect.load(Gdx.files.internal("Particles/seedCannon.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.WATER_JET) : {
				particleEffect.load(Gdx.files.internal("Particles/waterJet.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.TORNADO) : {
				particleEffect.load(Gdx.files.internal("Particles/evolution.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.BUBBLES) : {
				particleEffect.load(Gdx.files.internal("Particles/bubbles.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.FIREBALL) : {
				particleEffect.load(Gdx.files.internal("Particles/fireBall.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.FAST_ICE) : {
				particleEffect.load(Gdx.files.internal("Particles/fast-ice.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.ICE_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/ice-attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.POISONED) : {
				particleEffect.load(Gdx.files.internal("Particles/poisoned.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.POISON_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/poison-attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.PHYSICAL_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/physical-attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.STAR) : {
				particleEffect.load(Gdx.files.internal("Particles/star.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.DEFEND) : {
				particleEffect.load(Gdx.files.internal("Particles/defend.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.DEATH) : {
				particleEffect.load(Gdx.files.internal("Particles/death.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.HEAL) : {
				particleEffect.load(Gdx.files.internal("Particles/heal.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.GREEN_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/greenAttack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.GREEN_ORANGE_STAR) : {
				particleEffect.load(Gdx.files.internal("Particles/green-star.particles"), Gdx.files.internal("Particles/"));
				
				// Probably not the best way to get multiple effects, but it works.
				ParticleEffect particleEffect2 = new ParticleEffect();
				particleEffect2.load(Gdx.files.internal("Particles/orange-star.particles"), Gdx.files.internal("Particles/"));
				particleEffect.getEmitters().add(particleEffect2.getEmitters().first());
				break;
			}
			case(ParticleIds.GREEN_STAR) : {
				particleEffect.load(Gdx.files.internal("Particles/green-star.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.ORANGE_STAR) : {
				particleEffect.load(Gdx.files.internal("Particles/orange-star.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.FLAME) : {
				particleEffect.load(Gdx.files.internal("Particles/flame.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.ROCK) : {
				particleEffect.load(Gdx.files.internal("Particles/rock-smash.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.MEAT) : {
				particleEffect.load(Gdx.files.internal("Particles/meat.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.ELECTRIC_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/lightning-attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.FIRE_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/fire-attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.EARTH_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/earth-attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
			case(ParticleIds.DAMAGED) : {
				particleEffect.load(Gdx.files.internal("Particles/attack.particles"), Gdx.files.internal("Particles/"));
				break;
			}
		}
	}
}
