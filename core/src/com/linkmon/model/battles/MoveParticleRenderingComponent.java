package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.particles.ParticleFactory;
import com.linkmon.view.particles.ParticleIds;

public class MoveParticleRenderingComponent extends LibgdxRenderingComponent {
	
	public float angle;
	
	private double gradient;
	
	float distance;
	
	private int direction;
	
	ParticleEffect effect;
	
	public ParticleEffect getEffect() {
		return effect;
	}
	
	public void setAngle(float targetX, float targetY, GameObject move)
	{
		direction = move.direction;
		
		double dx;
		double dy;
		
	    dx = targetX - move.getX() + move.getWidth()/2;
		// Minus to correct for coord re-mapping
	    dy = targetY - move.getY() - move.getHeight()/2;

	    double inRads = Math.atan2(dy,dx);

	    // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
	    if (inRads < 0)
	        inRads = Math.abs(inRads);
	    else
	        inRads = 2*Math.PI - inRads;

	    angle = (float)Math.toDegrees(inRads);
	    
	    gradient = (dy/dx);
	    
	    double grad = Math.tan(inRads);
	    
		dx = Math.pow(move.getX() + move.getWidth()/2 - targetX, 2);

	    dy = Math.pow(move.getY()+move.getHeight()/2 - targetY, 2);
	    
	    distance = (float) Math.sqrt(dx-dy);
	    
	    distance = Vector2.dst(move.getX(), move.getY(), targetX, targetY);
	    
	    Gdx.app.log("BattleLinkmonRendering", "Angle: " + angle + "  Gradient: " + gradient + "  Gradient: " + grad + " Distance: " + distance + " Target Y: " + targetY);
	}
//	
//	public Vector2 getMatrixRotation(float xStrength, float yStrength, float angle) {
//		
//		double cosAngle = Math.cos(angle-90);
//		double sinAngle = Math.sin(angle-90);
//		
//		float x = (float) ((cosAngle*yStrength) + (-sinAngle*xStrength));
//		float y = (float) ((sinAngle*yStrength) + (cosAngle*xStrength));
//		
//		
//		return new Vector2(x, y);
//	}
	
	public void setParticleEffect(int particleId, float x, float y) {
		
		effect = new ParticleEffect();
		
		ParticleFactory.getParticleFromId(ParticleFactory.getParticleFromMoveType(particleId), effect);
		
		// Set angles, rotation, life and duration
		for(ParticleEmitter emitter : effect.getEmitters()) {
			
			// Set angle to target angle
			emitter.getAngle().setHigh(-angle - 45);
			emitter.getAngle().setHighMax(-angle + 45);
			emitter.getAngle().setLow(-angle);
			
			emitter.getRotation().setLow(-angle + 90);
			
//			Vector2 power = getMatrixRotation(500, 0, angle);
//			
//			// Set gravity and wind based on angle
//			emitter.getGravity().setHighMin(-power.x);
//			emitter.getGravity().setHighMax(power.x);
//			emitter.getWind().setHighMin(0);
//			emitter.getWind().setHighMax(-power.y*direction);
			
			// Calculate duration based on distance from points / velocity (*1000 to get to milliseconds)
			
			int duration = (int) (distance/(300)*1000);
			emitter.getDuration().setLow(duration);
			
			emitter.getLife().setLow(0);
			if(emitter.getLife().getHighMin() == 3000)
				emitter.getLife().setHigh(duration, duration);
		}
		
		effect.setPosition(x, y);
		effect.start();
	}
	
	@Override
	public void draw(Batch batch, GameObject object) {
		super.draw(batch, object);
		
        if(effect != null)
        	effect.draw(batch, Gdx.graphics.getDeltaTime());
	}

	public float getAngle() {
		// TODO Auto-generated method stub
		return angle;
	}

}
