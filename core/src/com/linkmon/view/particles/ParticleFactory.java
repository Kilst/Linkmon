package com.linkmon.view.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.linkmon.model.linkmon.MoveType;

public class ParticleFactory {
	
	public static int getParticleFromMoveType(int type) {
		int particleId = -1;
		switch(type) {
			case(MoveType.ELECTRIC) : {
				particleId = ParticleIds.ELECTRIC_ATTACK;
				break;
			}
			case(MoveType.EARTH) : {
				particleId = ParticleIds.ROCK;
				break;
			}
			case(MoveType.FIRE) : {
				particleId = ParticleIds.FIRE_ATTACK;
				break;
			}
			case(MoveType.WATER) : {
				particleId = ParticleIds.STAR;
				break;
			}
			case(MoveType.NOTYPE) : {
				particleId = ParticleIds.GREEN_ORANGE_STAR;
				break;
			}
		}
		
		return particleId;
	}

	public static void getParticleFromId(int id, ParticleEffect particleEffect) {
		switch(id) {
			case(ParticleIds.STAR) : {
				particleEffect.load(Gdx.files.internal("Particles/star.particles"), Gdx.files.internal("Particles/"));
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
				particleEffect.load(Gdx.files.internal("Particles/pulsating-circle1.particles"), Gdx.files.internal("Particles/"));
				
				// Probably not the best way to get multiple effects, but it works.
				ParticleEffect particleEffect2 = new ParticleEffect();
				particleEffect2.load(Gdx.files.internal("Particles/lightning-bolt1.particles"), Gdx.files.internal("Particles/"));
				particleEffect.getEmitters().add(particleEffect2.getEmitters().first());
				break;
			}
			case(ParticleIds.FIRE_ATTACK) : {
				particleEffect.load(Gdx.files.internal("Particles/fire.particles"), Gdx.files.internal("Particles/"));
				break;
			}
		}
	}
}
