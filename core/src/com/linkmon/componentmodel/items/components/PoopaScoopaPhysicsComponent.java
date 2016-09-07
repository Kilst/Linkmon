package com.linkmon.componentmodel.items.components;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.CollisionComponent;
import com.linkmon.componentmodel.components.ICollisionComponent;
import com.linkmon.componentmodel.components.PhysicsComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectType;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.poop.PoopComponent;
import com.linkmon.componentmodel.linkmon.poop.PoopInputComponent;
import com.linkmon.model.gameobject.Direction;

public class PoopaScoopaPhysicsComponent extends PhysicsComponent {
	
	public PoopaScoopaPhysicsComponent(CollisionComponent collisionComponent) {
		super(collisionComponent);
		// TODO Auto-generated constructor stub
		move = true;
	}

	int veloX = -1;

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		if(collisionComponent != null)
			collisionComponent.testCollision(object, objects);
		
		if(move)
			move(object);
		
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
