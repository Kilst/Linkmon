package com.linkmon.view.particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Pool;

public class ParticlePool {
	
	private int id;
	
	public ParticlePool(int id) {
		this.id = id;
	}
	
	// Array containing active particles.
	public List<ParticleEffect> activeParticles = new ArrayList<ParticleEffect>();
	// Particle Pool
	public final Pool<ParticleEffect> particlePool = new Pool<ParticleEffect>() {
	    @Override
	    protected ParticleEffect newObject() { // Called if Pool.obtain() doesn't return an old object
	    	ParticleEffect pe = new ParticleEffect();
	    	ParticleFactory.getParticleFromId(id, pe);
	        return pe;
	    }
    };
    
    public void freeCompletedParticles() {
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
