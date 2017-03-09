package com.linkmon.model.battles;

import java.util.List;
import com.linkmon.model.components.PhysicsComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;

public class BattlePhysicsComponent extends PhysicsComponent {
	
	private GameObject linkmon;
	
	public BattlePhysicsComponent() {
		moveY = 65;
	}
	
	@Override
	public void update(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		super.update(object, objects);
		if(linkmon == null) {
			linkmon = object;
		}
	}
	
	@Override
	public void move(GameObject object) {
		// Move X
		if(object.getX() > moveX) {
			object.setX(object.getX() - 1);
			object.isMoving = true;
			object.direction = Direction.LEFT;
		}
		else if(object.getX() < moveX) {
			object.setX(object.getX() + 1);
			object.isMoving = true;
			object.direction = Direction.RIGHT;
		}
		
		// Move Y
		if(object.getY() > moveY) {
			object.setY(object.getY() - 1);
			object.isMoving = true;
		}
		else if(object.getY() < moveY) {
			object.setY(object.getY() + 1);
			object.isMoving = true;
		}
		if(moveY == object.getY() && moveX == object.getX()) {
			move = false;
			object.isMoving = false;
		}
	}
	
//	public void dashTo(GameObject object) {
//		// Move X
//		if(object.getX() > moveX) {
//			object.setX(object.getX() - 5);
//			object.isMoving = true;
//			object.direction = Direction.LEFT;
//		}
//		else if(object.getX() < moveX) {
//			object.setX(object.getX() + 5);
//			object.isMoving = true;
//			object.direction = Direction.RIGHT;
//		}
//		
//		// Move Y
//		if(object.getY() > moveY) {
//			object.setY(object.getY() - 5);
//			object.isMoving = true;
//		}
//		else if(object.getY() < moveY) {
//			object.setY(object.getY() + 5);
//			object.isMoving = true;
//		}
//		if(moveY == object.getY() && moveX == object.getX()) {
//			move = false;
//			object.isMoving = false;
//		}
//	}

	@Override
	protected void collisionTest(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		
	}

}
