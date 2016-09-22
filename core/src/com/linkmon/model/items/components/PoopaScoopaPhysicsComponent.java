package com.linkmon.model.items.components;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.components.ICollisionComponent;
import com.linkmon.model.components.PhysicsComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.poop.PoopComponent;
import com.linkmon.model.linkmon.poop.PoopInputComponent;

public class PoopaScoopaPhysicsComponent extends PhysicsComponent {
	
	public PoopaScoopaPhysicsComponent(CollisionComponent collisionComponent) {
		super(collisionComponent);
		// TODO Auto-generated constructor stub
		move = true;
	}

	int veloX = -1;

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		super.update(object, objects);
		
		if(collisionComponent != null)
			for(GameObject collideObject : collisionComponent.getCollisionList()) {
				if(collideObject.getType() == ObjectType.POOP) {
					// Setting clicked removes the poop completely next frame when PoopComonent updates.
					((PoopInputComponent)collideObject.getInputComponent()).setClicked(true);
				}
			}
	}
	
	@Override
	public void move(GameObject object) {
		// Move X
		if(object.getX()+object.getWidth() > object.getWorld().getWidth()) {
			veloX = -1;
			object.isMoving = true;
			object.direction = Direction.LEFT;
		}
		else if(object.getX() < 0) {
			veloX = 1;
			object.isMoving = true;
			object.direction = Direction.RIGHT;
		}
		
		object.setX(object.getX() + veloX);
	}
}
