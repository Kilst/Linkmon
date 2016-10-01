package com.linkmon.view.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Pool;

public class ParticleFactory {

	public static ParticleEffect getParticleFromId(int id, ParticleEffect particleEffect) {
		ParticleEffect effect = null;
		switch(id) {
			case(ParticleIds.STAR) : {
				particleEffect.load(Gdx.files.internal("Particles/star.particles"), Gdx.files.internal("Particles/"));
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
		}
		
		return effect;
	}
}
