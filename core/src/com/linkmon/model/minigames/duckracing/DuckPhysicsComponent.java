package com.linkmon.model.minigames.duckracing;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.minigames.PlayablePhysicsComponent;

public class DuckPhysicsComponent extends PlayablePhysicsComponent {

	public DuckPhysicsComponent(CollisionComponent collisionComponent, boolean gravity, boolean friction) {
		super(collisionComponent, gravity, friction);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void collisionTest(GameObject object, List<GameObject> objects) {
		if(collisionComponent != null) {
			collisionComponent.testCollision(object, objects);
			for(GameObject collideObject : collisionComponent.getCollisionList()) {
				if(collideObject.getType() == ObjectType.GOAL) {
					// Setting clicked removes the poop completely next frame when PoopComonent updates.
					if(object.getType() == ObjectType.PLAYER)
						object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.MINIGAME_WIN));
					else
						object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.MINIGAME_LOSE));
					Gdx.app.log("DuckPhysics", "Hit Finish Line!");
				}
			}
		}
	}

}
