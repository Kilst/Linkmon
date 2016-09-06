package com.linkmon.componentmodel.items.components;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.PhysicsComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectType;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.poop.PoopComponent;
import com.linkmon.model.gameobject.Direction;

public class PoopaScoopaPhysicsComponent extends PhysicsComponent {
	
	int veloX = -1;

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		move(object);
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

	@Override
	public void testCollision(GameObject gameObject, List<GameObject> objects) {
		// TODO Auto-generated method stub
		List<GameObject> poopList = null;
		PoopComponent pC = null;
		
		for(GameObject object : objects) {
			if(object != gameObject) {
				if(object.getType() == ObjectType.LINKMON) {
					pC = ((LinkmonExtraComponents)object.getExtraComponents()).getPoopComponent();
					poopList = pC.getPoopList();
					
					for(GameObject poop : poopList) {
						if(gameObject.getAabb().contains(poop.getAabb())) {
							Gdx.app.log("PoopaScoopaPhysics", "Collision detected!\nPoop X: " + poop.getX()+ "\nScoopa X: " + gameObject.getX()
							+ "\nPoop Width: " + poop.getWidth() + "\nScoopa Width: " + gameObject.getWidth());
							pC.removePoop(poop);
						}
					}
				}
			}
		}
	}
}
