package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.model.aonevonebattle.OneVBattlePhysicsComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.battles.MoveParticleRenderingComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class ThrowObjectMoveAction implements IMoveAction {

	private boolean running = false;
	
	GameObject throwObject;
	
	int x1 = 150;
	int y1 = 120;
	Timer timer;

	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		
		if(running) {
			
//			player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.STAR, throwObject.getX()+throwObject.getWidth()/2, throwObject.getY()+throwObject.getHeight()/2));
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack))
				animComp.setState(new AnimationStateAttack(animComp));
			
			// apply damageplayer.getX()+x*player.direction), (int)player.getY()+y
			if(animComp.isCurrentAnimationEnded() && throwObject.getX() == (int)(player.getX()+((player.getWidth()/2)+x1)*player.direction) && throwObject.getY() == (int)(player.getY()+(player.getHeight()/2)+y1)) {
				
				((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).dashTo((int)target.getX(), (int)target.getY(), throwObject, 20);
				
//				Gdx.app.log("BattleLinkmonRendering", "Angle: " + throwObject.getX() + "   " + throwObject.getY()
//				+ "Angle: " + target.getX() + "   " + target.getY());
			}
			
			if(throwObject.getX() == (int)target.getX() && throwObject.getY() == (int)target.getY() && !timer.checkTimer()) {
				// apply damage
				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();
				player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.DAMAGED, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
				
				player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.GREEN_STAR, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));

				// Stop updating
				
				((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
				
				player.getWorld().removeObjectFromWorld(throwObject);

				timer.start();
				
				running = false;
				throwObject = null;
			}
		}
		
		if(timer.checkTimer()) {
			((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
			Gdx.app.log("Move1", "running = false");
		}
		
		return false;
	}

	@Override
	public void setRunning(boolean value) {
		// TODO Auto-generated method stub
		running = value;
		
	}

	@Override
	public boolean start(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp) {
		// TODO Auto-generated method stub
		if(throwObject == null)
			createParticleAttack(player, target);
		
		running = true;
		timer = new Timer(2, false);
		
		return false;
	}
	
	private void createParticleAttack(GameObject player, GameObject target) {
		throwObject = new GameObject(ObjectId.MEAT, 0, new MoveRenderingComponent(), null, new OneVBattlePhysicsComponent(), null);
		
		throwObject.direction = player.direction;
		throwObject.setPosition(player.getX()+(player.getWidth()/2)*player.direction, player.getY()+player.getHeight()/2);
		
//		((MoveRenderingComponent)throwObject.getRenderer()).setSprite(throwObject);
		
		player.getWorld().addObjectToWorld(throwObject);
		
		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).update(throwObject, null);
		
		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).dashTo((int)(player.getX()+((player.getWidth()/2)+x1)*player.direction), (int)(player.getY()+(player.getHeight()/2)+y1), throwObject, 20);
		
		((MoveRenderingComponent)throwObject.getRenderer()).setParticleEffect(((OneVOneBattleComponent)player.getExtraComponents()).getCurrentMove().getParticleId(), throwObject.getX()+throwObject.getWidth()/2, throwObject.getY()+throwObject.getHeight()/2);
	}

}
