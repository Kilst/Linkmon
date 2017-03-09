package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.particles.ParticleFactory;
import com.linkmon.view.particles.ParticleIds;

public class MoveRenderingComponent extends LibgdxRenderingComponent {
	
	ParticleEffect effect;
	int particleId = -1;
	
	public MoveRenderingComponent() {
		effect = new ParticleEffect();
	}
	
	public ParticleEffect getEffect() {
		return effect;
	}
	
	public void setParticleEffect(int particleId, float x, float y) {
		
		ParticleFactory.getParticleFromId(particleId, effect);
		
		effect.setPosition(x, y);
		//effect.start();
		
		this.particleId = particleId;
	}
	
	@Override
	public void draw(Batch batch, GameObject object) {
		if(particleId != -1) {
			effect.setPosition(object.getX()+object.getWidth()/2, object.getY()+object.getHeight()/2);
			effect.draw(batch, Gdx.graphics.getDeltaTime());
			//object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, particleId, object.getX()+object.getWidth()/2, object.getY()+object.getHeight()/2));
		}
			
		super.draw(batch, object);
	}
}
