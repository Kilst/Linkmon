package com.linkmon.view.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	   ParticleEffect effect;

	   public ParticleEffectActor(ParticleEffect effect) {
	      this.effect = effect;
	      for(ParticleEmitter emitter : effect.getEmitters()) {
	    	  emitter.setContinuous(true);
	      }
		  effect.start(); //need to start the particle spawning
	   }

	   @Override
	   public void draw(Batch batch, float parentAlpha) {
	      effect.draw(batch); //define behavior when stage calls Actor.draw()
	   }
	   
	   public void setPosition(float x, float y) {
		   super.setPosition(x, y);
		   effect.setPosition(x, y); //set to whatever x/y you prefer
	   }
	   
	   @Override
	   public void act(float delta) {
	      super.act(delta);
	      effect.update(delta); //update it
	                
	   }

	   public ParticleEffect getEffect() {
	      return effect;
	   }
	}