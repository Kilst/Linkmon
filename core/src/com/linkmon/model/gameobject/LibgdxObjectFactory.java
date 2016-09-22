package com.linkmon.model.gameobject;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.aminigame.EggPhysicsComponent;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.items.AddToWorldItemAction;
import com.linkmon.model.items.FeedItemAction;
import com.linkmon.model.items.ItemComponent;
import com.linkmon.model.items.ItemType;
import com.linkmon.model.items.ReviveItemAction;
import com.linkmon.model.items.components.PoopaScoopaPhysicsComponent;
import com.linkmon.model.libgdx.LibgdxAnimationComponent;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.libgdx.LinkmonRenderingComponent;
import com.linkmon.model.libgdx.PoopaScoopaAnimationComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonInputComponent;
import com.linkmon.model.linkmon.LinkmonPhysicsComponent;

public class LibgdxObjectFactory implements IGameObjectFactory {
	
	// This is a Libgdx Factory. As you can see, we pass in a specific renderer and set sprites and animations here.
	// Different frameworks will pass in their own rendering component and create their versions of sprites and animations.
	
	private EventManager eManager;
	
	public GameObject createLinkmon(int id) {
		
		GameObject linkmon = new GameObject(id, ObjectType.LINKMON, new LinkmonRenderingComponent(), null,
				new LinkmonPhysicsComponent(), new LinkmonExtraComponents());
		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setBirthDate();
		
		linkmon.addInputComponent(new LinkmonInputComponent(eManager, linkmon));
		linkmon.setX(0);
		linkmon.setY(65);
		//linkmon.getPhysicsComponent().setMoveToX(700);
		
		return linkmon;
	}
	
	public GameObject getObjectFromId(int id) {
		switch(id) {
			case (ObjectId.POOP) : {
				GameObject poop = new GameObject(id, ObjectType.POOP, new LibgdxRenderingComponent(), null, null, null);
				((LibgdxRenderingComponent)poop.getRenderer()).setSprite(poop);
				return poop;
			}
			case (ObjectId.MEAT) : {
				ItemComponent foodComp = new ItemComponent(new FeedItemAction(100), 1, 50, ItemType.FOOD, "Adds to Linkmons fullness");
				GameObject meat = new GameObject(id, ObjectType.ITEM, null, null, null, foodComp);
				meat.setName("Meat");
				return meat;
			}
			case (ObjectId.REVIVE_POTION) : {
				ItemComponent itemComp = new ItemComponent(new ReviveItemAction(), 1, 5000, ItemType.POTION, "Revives your Linkmon if dead.");
				GameObject revive = new GameObject(id, ObjectType.ITEM, null, null, null, itemComp);
				revive.setName("Revive Potion");
				return revive;
			}
			case (ObjectId.POOPA_SCOOPA) : {
				ItemComponent foodComp = new ItemComponent(new AddToWorldItemAction(), 1, 500, ItemType.PLACEABLE, "Automatically cleans poop!");
				GameObject poopaScoopa2001 = new GameObject(ObjectId.POOPA_SCOOPA, ObjectType.ITEM, new LibgdxRenderingComponent(), null, new PoopaScoopaPhysicsComponent(new CollisionComponent()), foodComp);
				poopaScoopa2001.setName("PoopaScoopa2001");
				poopaScoopa2001.setY(65);
				//((LibgdxRenderingComponent)poopaScoopa2001.getRenderer()).setSprite(poopaScoopa2001);
				((LibgdxRenderingComponent)poopaScoopa2001.getRenderer()).setAnimation(new PoopaScoopaAnimationComponent(poopaScoopa2001));
				return poopaScoopa2001;
			}
			case (77) : {
				GameObject poopaScoopa2001 = new GameObject(2, ObjectType.ITEM, new LibgdxRenderingComponent(), null, new EggPhysicsComponent(new CollisionComponent()), null);
				poopaScoopa2001.setName("Egg");
				((LibgdxRenderingComponent)poopaScoopa2001.getRenderer()).setSprite(poopaScoopa2001);
				return poopaScoopa2001;
			}
		}
		return null;
	}

	@Override
	public void setEventManager(EventManager eManager) {
		// TODO Auto-generated method stub
		this.eManager = eManager;
	}
}
