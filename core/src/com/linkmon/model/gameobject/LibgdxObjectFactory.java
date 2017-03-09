package com.linkmon.model.gameobject;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.aonevonebattle.moves.MoveRenderingComponent;
import com.linkmon.model.battles.LocalBattleLinkmon;
import com.linkmon.model.components.CircleCollisionComponent;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.items.ItemType;
import com.linkmon.model.items.actions.AddToWorldItemAction;
import com.linkmon.model.items.actions.EnergyPotionItemAction;
import com.linkmon.model.items.actions.FeedItemAction;
import com.linkmon.model.items.actions.HealthPotionItemAction;
import com.linkmon.model.items.actions.MedicineItemAction;
import com.linkmon.model.items.actions.ReviveItemAction;
import com.linkmon.model.items.components.ItemComponent;
import com.linkmon.model.items.components.PoopaScoopaPhysicsComponent;
import com.linkmon.model.libgdx.LibgdxAnimationComponent;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.libgdx.LinkmonRenderingComponent;
import com.linkmon.model.libgdx.PoopaScoopaAnimationComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonInputComponent;
import com.linkmon.model.linkmon.LinkmonPhysicsComponent;
import com.linkmon.model.linkmon.RankIds;
import com.linkmon.model.minigames.coinroll.CoinRollItemPhysicsComponent;
import com.linkmon.view.particles.ParticleIds;

public class LibgdxObjectFactory implements IGameObjectFactory {
	
	// This is a Libgdx Factory. As you can see, we pass in a specific renderer and set sprites and animations here.
	// Different frameworks will pass in their own rendering component and create their versions of sprites and animations.
	
	private EventManager eManager;
	
	public GameObject createLinkmon(int id) {
		
		GameObject linkmon = new GameObject(id, ObjectType.LINKMON, new LinkmonRenderingComponent(), null,
				new LinkmonPhysicsComponent(), new LinkmonExtraComponents());
		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setBirthDate();
		
		linkmon.addInputComponent(new LinkmonInputComponent(eManager, linkmon, null));
		linkmon.setX(0);
		linkmon.setY(65);
		//linkmon.getPhysicsComponent().setMoveToX(700);
		linkmon.setName("Linkmon");
		
		return linkmon;
	}
	
	public LocalBattleLinkmon createLocalBattleLinkmon(int rank, int targetId) {
		int statPoints = 0;
		
		switch(rank) {
			case(RankIds.E) : {
				//statPoints = (int) (Math.random()*40 +1);
				statPoints = 40;
				break;
			}
		}
		
		int health = statPoints*10;
		
		int attack = 0;
		int defense = 0;
		int speed = 0;
		
		while(statPoints> 1) {
			int newAttack = (int) Math.ceil((Math.random()*5));
			attack += newAttack;
			statPoints-= newAttack;
			int newDefense = (int) Math.ceil((Math.random()*5));
			defense += newDefense;
			statPoints-= newDefense;
			int newSpeed = (int) Math.ceil((Math.random()*5));
			speed += newSpeed;
			statPoints-= newSpeed;
		}
		
		LocalBattleLinkmon linkmon = new LocalBattleLinkmon(1, attack+1, defense+1, speed+1, 11, 12, health, "opponent", false, targetId);
		return linkmon;
	}
	
	public GameObject getObjectFromId(int id) {
		switch(id) {
			case (ObjectId.POOP) : {
				GameObject poop = new GameObject(id, ObjectType.POOP, new LibgdxRenderingComponent(), null, null, null);
				((LibgdxRenderingComponent)poop.getRenderer()).setSprite(poop);
				poop.setName("Poop");
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
			case (ObjectId.HEALTH_POTION) : {
				ItemComponent itemComp = new ItemComponent(new HealthPotionItemAction(100), 1, 500, ItemType.RECOVERY, "Heals 100hp.");
				GameObject healthPot = new GameObject(id, ObjectType.ITEM, new LibgdxRenderingComponent(), null, null, itemComp);
				healthPot.setName("Health Potion");
				((LibgdxRenderingComponent)healthPot.getRenderer()).setSprite(healthPot);
				return healthPot;
			}
			case (ObjectId.ENERGY_POTION) : {
				ItemComponent itemComp = new ItemComponent(new EnergyPotionItemAction(25), 1, 500, ItemType.RECOVERY, "Adds 25 energy.");
				GameObject healthPot = new GameObject(id, ObjectType.ITEM, new LibgdxRenderingComponent(), null, null, itemComp);
				healthPot.setName("Energy Potion");
				((LibgdxRenderingComponent)healthPot.getRenderer()).setSprite(healthPot);
				return healthPot;
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
			case (ObjectId.MEDICINE) : {
				ItemComponent itemComp = new ItemComponent(new MedicineItemAction(), 1, 250, ItemType.POTION, "Use this if your Linkmon is sick.");
				GameObject medicine = new GameObject(id, ObjectType.ITEM, null, null, null, itemComp);
				medicine.setName("Medicine");
				return medicine;
			}
			case (ObjectId.FIREBALL) : {
				GameObject fallingPoop = new GameObject(id, ObjectType.FIREBALL, new MoveRenderingComponent(), null, new CoinRollItemPhysicsComponent(new CircleCollisionComponent()), null);
				fallingPoop.setName("FireBall");
				((MoveRenderingComponent)fallingPoop.getRenderer()).setSprite(fallingPoop);
				((MoveRenderingComponent)fallingPoop.getRenderer()).setParticleEffect(ParticleIds.FIREBALL, fallingPoop.getX()+fallingPoop.getWidth(), fallingPoop.getY()+fallingPoop.getHeight()/2);
				return fallingPoop;
			}
			case (ObjectId.RING) : {
				GameObject ring = new GameObject(id, ObjectType.ITEM, new LibgdxRenderingComponent(), null, new CoinRollItemPhysicsComponent(new CircleCollisionComponent()), null);
				ring.setName("Ring");
				((LibgdxRenderingComponent)ring.getRenderer()).setSprite(ring);
				return ring;
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
