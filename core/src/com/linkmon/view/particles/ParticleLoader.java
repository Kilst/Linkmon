package com.linkmon.view.particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ParticleLoader {
	
	ParticleEffect particleEffect;
	// Array containing active particles.
    private List<ParticleEffect> activeParticles = new ArrayList<ParticleEffect>();
    // Particle Pool
    private final Pool<ParticleEffect> particlePool = new Pool<ParticleEffect>() {
	    @Override
	    protected ParticleEffect newObject() { // Called if Pool.obtain() doesn't return an old object
	        return new ParticleEffect();
	    }
    };
    
    
	
	public ParticleLoader() {
		particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("Particles/star.particles"), Gdx.files.internal("Particles/"));
	}
	
	public void addParticle(int particleId, float x, float y) {
		ParticleEffect particleEffect = particlePool.obtain();
        ParticleFactory.getParticleFromId(particleId, particleEffect);
        particleEffect.setPosition(x, y);
        particleEffect.start();
        activeParticles.add(particleEffect);
	}
	
	public void createStarParticles(float x, float y) {
		ParticleEffect particleEffect = particlePool.obtain();
        particleEffect.load(Gdx.files.internal("Particles/star.particles"), Gdx.files.internal("Particles/"));
        particleEffect.setPosition(x, y);
        particleEffect.start();
        activeParticles.add(particleEffect);
	}
	
	public void createRockSmashParticles(float x, float y) {
		ParticleEffect particleEffect = particlePool.obtain();
        particleEffect.load(Gdx.files.internal("Particles/rock-smash.particles"), Gdx.files.internal("Particles/"));
        particleEffect.setPosition(x, y);
        particleEffect.start();
        activeParticles.add(particleEffect);
	}
	
	public void createFlameParticles(float x, float y) {
		ParticleEffect particleEffect = particlePool.obtain();
        particleEffect.load(Gdx.files.internal("Particles/flame.particles"), Gdx.files.internal("Particles/"));
        particleEffect.setPosition(x, y);
        particleEffect.start();
        activeParticles.add(particleEffect);
	}
	
	private void freeCompletedParticles() {
		Iterator<ParticleEffect> iter = activeParticles.iterator();

        while (iter.hasNext()) {
        	ParticleEffect pe = iter.next();
			if (pe.isComplete()) {
				pe.reset();
				particlePool.free(pe);
				iter.remove();
			}
		}
	}
	
	public void render(Batch batch) {
		Iterator<ParticleEffect> iter = activeParticles.iterator();

        while (iter.hasNext()) {
        	ParticleEffect pe = iter.next();

        	//pe.setPosition(x, y);
        	pe.update(Gdx.graphics.getDeltaTime());
        	pe.draw(batch);
        }
        
        freeCompletedParticles();
	}
}
