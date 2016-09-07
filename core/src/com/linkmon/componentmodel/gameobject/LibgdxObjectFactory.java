package com.linkmon.componentmodel.gameobject;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.CollisionComponent;
import com.linkmon.componentmodel.items.FoodComponent;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.items.components.PoopaScoopaPhysicsComponent;
import com.linkmon.componentmodel.libgdx.LibgdxAnimationComponent;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;
import com.linkmon.componentmodel.libgdx.LinkmonRenderingComponent;
import com.linkmon.componentmodel.libgdx.PoopaScoopaAnimationComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonInputComponent;
import com.linkmon.componentmodel.linkmon.LinkmonPhysicsComponent;
import com.linkmon.eventmanager.EventManager;

public class LibgdxObjectFactory implements IGameObjectFactory {
	
	// This is a Libgdx Factory. As you can see, we pass in a specific renderer and set sprites and animations here.
	// Different frameworks will pass in their own rendering component and create their versions of sprites and animations.
	
	private EventManager eManager;
	
	public GameObject createLinkmon(int id) {
		
		GameObject linkmon = new GameObject(id, ObjectType.LINKMON, new LinkmonRenderingComponent(), null,
				new LinkmonPhysicsComponent(), new LinkmonExtraComponents());
		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon));
		
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setBirthDate();
		
		linkmon.addInputComponent(new LinkmonInputComponent(eManager, linkmon));
		linkmon.setX(0);
		linkmon.setY(45);
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
				ItemComponent foodComp = new FoodComponent();
				foodComp.setPrice(50);
				GameObject meat = new GameObject(id, ObjectType.ITEM, null, null, null, foodComp);
				meat.setName("Meat");
				return meat;
			}
			case (ObjectId.POOPA_SCOOPA) : {
				ItemComponent foodComp = new ItemComponent();
				GameObject poopaScoopa2001 = new GameObject(ObjectId.POOPA_SCOOPA, ObjectType.ITEM, new LibgdxRenderingComponent(), null, new PoopaScoopaPhysicsComponent(new CollisionComponent()), foodComp);
				poopaScoopa2001.setName("PoopaScoopa2001");
				poopaScoopa2001.setY(45);
				//((LibgdxRenderingComponent)poopaScoopa2001.getRenderer()).setSprite(poopaScoopa2001);
				((LibgdxRenderingComponent)poopaScoopa2001.getRenderer()).setAnimation(new PoopaScoopaAnimationComponent(poopaScoopa2001));
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
