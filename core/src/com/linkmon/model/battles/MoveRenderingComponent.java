package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.particles.ParticleIds;

public class MoveRenderingComponent extends LibgdxRenderingComponent {
	
	Sprite tail;
	
	public MoveRenderingComponent() {
		
		
	}
	
	public void setTailPosition(GameObject object) {
		
		tail = new Sprite(ResourceLoader.getRegionFromId(ObjectId.ATTACK_BEAM_TAIL));
		tail.setColor(0, 1, 0, 1);
		tail.setSize(tail.getWidth()/2, tail.getHeight()/2);
		
		if(object.direction == Direction.RIGHT)
			tail.setX(object.getX()-(tail.getWidth()/2));
		else
			tail.setX(object.getX()+object.getWidth()/2-(tail.getWidth()/2));
		tail.setY(object.getY()+(object.getHeight()/2)-(tail.getHeight()/2));
		
//		tail.setOrigin(tail.getWidth()/2, tail.getHeight()/2);
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
//		tail.setBounds(tail.getX(), tail.getY(), tail.getWidth(), tail.getHeight());
//		sprite.setBounds(object.getX(), object.getY(), object.getWidth(), object.getHeight());
	}
	
	public void draw(Batch batch, GameObject object) {
		// TODO Auto-generated method stub
		if(sprite != null) {
			
//			if(object.direction == Direction.RIGHT)
//				sprite.flip(true, true);
//			
//			if(tail != null)
//				drawTail(batch, object);
//			
//			batch.draw(sprite, object.getX(), object.getY(), (object.getWidth()/2), (object.getHeight()/2), object.getWidth(), object.getHeight(), 1, 1, -((MovePhysicsComponent)object.getPhysicsComponent()).getAngle());
		}
		else if(animation != null) {
			if(animation.getCurrentAnimation() != null) {
				if(flipped)
					batch.draw(animation.getCurrentKeyFrame(), object.getX(), object.getY(), animation.getCurrentKeyFrame().getRegionWidth(), animation.getCurrentKeyFrame().getRegionHeight());
				else
					batch.draw(animation.getCurrentKeyFrame(), object.getX()+animation.getCurrentKeyFrame().getRegionWidth(), object.getY(), -animation.getCurrentKeyFrame().getRegionWidth(), animation.getCurrentKeyFrame().getRegionHeight());	
			}
		}
		
		object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.GREEN_ATTACK, object.getX()+object.getWidth()/2, object.getY()+object.getHeight()/2));
	}
	
	private void drawTail(Batch batch, GameObject object) {
		double dx;
		// Minus to correct for coord re-mapping
		double dy;
		
		if(object.direction == Direction.RIGHT)
		    dx = Math.pow(tail.getX() - object.getX()-object.getWidth(), 2);
		else
			dx = Math.pow(tail.getX() - object.getX(), 2);

	    dy = Math.pow(tail.getY() - object.getY()-object.getHeight()/2, 2);
		    
		float distance = (float) Math.sqrt(dx-dy);
		
		batch.draw(tail, object.getX()+object.getWidth()/2, object.getY()+(object.getHeight()/2)-tail.getHeight()/2, (tail.getWidth()/2), (tail.getHeight()/2), -distance, tail.getHeight(), 1, 1, -((MovePhysicsComponent)object.getPhysicsComponent()).getAngle());
	}
}
