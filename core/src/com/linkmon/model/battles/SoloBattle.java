package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.components.InputActionTarget;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.libgdx.LinkmonRenderingComponent;
import com.linkmon.model.linkmon.LinkmonIds;
import com.linkmon.model.linkmon.LinkmonInputComponent;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;

public class SoloBattle implements IBattle {
	
	private GameObject[] linkmons;
	
	private LocalBattleLinkmon[] battleLinkmons;
	
	private BattleManager battleManager;
	
	private int playerTarget;
	private int playerMove;

	public SoloBattle(LocalBattleLinkmon opp1, LocalBattleLinkmon opp2, LocalBattleLinkmon opp3, LocalBattleLinkmon p1,
			LocalBattleLinkmon p2, LocalBattleLinkmon p3, EventManager eManager) {
		// TODO Auto-generated constructor stub

		linkmons = new GameObject[6];
		
		linkmons[0] = new GameObject(0, ObjectType.LINKMON, new BattleLinkmonRenderingComponent(), null,
				new BattlePhysicsComponent(), new BattleExtraComponent(opp1, false, this));
		linkmons[0].addInputComponent(new LinkmonInputComponent(eManager, linkmons[0], new InputActionTarget()));
		((LibgdxRenderingComponent)linkmons[0].getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmons[0].getId()));
		linkmons[0].setX(50);
		linkmons[0].setY(400);
		linkmons[0].direction = Direction.RIGHT;
		((BattleExtraComponent)linkmons[0].getExtraComponents()).setOwner("opponent");
		((BattleExtraComponent)linkmons[0].getExtraComponents()).setLinkmon(linkmons[0]);
		
		linkmons[1] = new GameObject(LinkmonIds.FIRE_BOY, ObjectType.LINKMON, new BattleLinkmonRenderingComponent(), null,
				new BattlePhysicsComponent(), new BattleExtraComponent(opp2, false, this));
		((LibgdxRenderingComponent)linkmons[1].getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmons[1].getId()));
		linkmons[1].addInputComponent(new LinkmonInputComponent(eManager, linkmons[1], new InputActionTarget()));
		linkmons[1].setX(200);
		linkmons[1].setY(300);
		linkmons[1].direction = Direction.RIGHT;
		((BattleExtraComponent)linkmons[1].getExtraComponents()).setOwner("opponent");
		((BattleExtraComponent)linkmons[1].getExtraComponents()).setLinkmon(linkmons[1]);
		
		linkmons[2] = new GameObject(0, ObjectType.LINKMON, new BattleLinkmonRenderingComponent(), null,
				new BattlePhysicsComponent(), new BattleExtraComponent(opp3, false, this));
		linkmons[2].addInputComponent(new LinkmonInputComponent(eManager, linkmons[2], new InputActionTarget()));
		((LibgdxRenderingComponent)linkmons[2].getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmons[2].getId()));
		linkmons[2].setX(50);
		linkmons[2].setY(200);
		linkmons[2].direction = Direction.RIGHT;
		((BattleExtraComponent)linkmons[2].getExtraComponents()).setOwner("opponent");
		((BattleExtraComponent)linkmons[2].getExtraComponents()).setLinkmon(linkmons[2]);
		
		linkmons[3] = new GameObject(0, ObjectType.LINKMON, new BattleLinkmonRenderingComponent(), null,
				new BattlePhysicsComponent(), new BattleExtraComponent(p1, false, this));
		linkmons[3].addInputComponent(new LinkmonInputComponent(eManager, linkmons[3], new InputActionTarget()));
		((LibgdxRenderingComponent)linkmons[3].getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmons[3].getId()));
		linkmons[3].setX(150+800);
		linkmons[3].setY(400);
		((BattleExtraComponent)linkmons[3].getExtraComponents()).setOwner("player");
		((BattleExtraComponent)linkmons[3].getExtraComponents()).setLinkmon(linkmons[3]);
		
		linkmons[4] = new GameObject(LinkmonIds.FIRE_BOY, ObjectType.LINKMON, new BattleLinkmonRenderingComponent(), null,
				new BattlePhysicsComponent(), new BattleExtraComponent(p2, true, this));
		linkmons[4].addInputComponent(new LinkmonInputComponent(eManager, linkmons[4], new InputActionTarget()));
		((LibgdxRenderingComponent)linkmons[4].getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmons[4].getId()));
		linkmons[4].setX(50+800);
		linkmons[4].setY(300);
		((BattleExtraComponent)linkmons[4].getExtraComponents()).setOwner("player");
		((BattleExtraComponent)linkmons[4].getExtraComponents()).setLinkmon(linkmons[4]);
		
		linkmons[5] = new GameObject(0, ObjectType.LINKMON, new BattleLinkmonRenderingComponent(), null,
				new BattlePhysicsComponent(), new BattleExtraComponent(p3, false, this));
		linkmons[5].addInputComponent(new LinkmonInputComponent(eManager, linkmons[5], new InputActionTarget()));
		((LibgdxRenderingComponent)linkmons[5].getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmons[5].getId()));
		linkmons[5].setX(150+800);
		linkmons[5].setY(200);
		((BattleExtraComponent)linkmons[5].getExtraComponents()).setOwner("player");
		((BattleExtraComponent)linkmons[5].getExtraComponents()).setLinkmon(linkmons[5]);
		
		
		
		battleLinkmons = new LocalBattleLinkmon[6];
		battleLinkmons[0] = opp1;
		battleLinkmons[1] = opp2;
		battleLinkmons[2] = opp3;
		battleLinkmons[3] = p1;
		battleLinkmons[4] = p2;
		battleLinkmons[5] = p3;
	}
	
	@Override
	public void playTurn(LocalBattleLinkmon linkmon) {
		// TODO Auto-generated method stub
		Gdx.app.log("SoloBattle", "Start turn");
		
		linkmon.startOfTurn();
		
		if(linkmon.isDead()) {
			Gdx.app.log("SoloBattle", "DEAD");
			return;
		}
		
		getTarget(linkmon);
		
		if(linkmon.isPoisoned()) {
			int poisonDropPercent = 80;
			
			if((Math.random()*50)+10*linkmon.getPoisonTick() > poisonDropPercent) {
				linkmon.setPoisoned(false);
				linkmon.setPoisonRemoved(true);
				linkmon.setPoisonTick(0);
				Gdx.app.log("SoloBattle", "Poison Removed");
			}
			else {
				linkmon.setPoisonDamageTaken(DamageFormula.poisonDamage(1, linkmon.getPoisonTick(), 30));
				linkmon.setPoisonTick(linkmon.getPoisonTick()+1);
			}
		}
		
		// Defend
		if(linkmon.getCurrentMove().getType() == MoveType.DEFEND) {
			linkmon.setDefending(true);
		}
		// Poison
		else if(linkmon.getCurrentMove().getType() == MoveType.POISON) {
			linkmon.getTarget().setPoisoned(true);
		}
		// Casting
		else if(linkmon.getCurrentMove().getType() == MoveType.MAGIC) {
			 // Cast heal magic
			if(linkmon.getCurrentMove().getId() == MoveIds.HEAL) {
				linkmon.getTarget().setHealingTaken(DamageFormula.damage(1, linkmon.getAttack(), 1, 50, false));
				Gdx.app.log("SoloBattle","Healing");
			}
			// Cast attack magic
			else {
				linkmon.getTarget().setDamageTaken(DamageFormula.damage(1, linkmon.getAttack(), linkmon.getTarget().getDefense(), linkmon.getCurrentMove().getDamage(), linkmon.getTarget().isDefending()));
				Gdx.app.log("SoloBattle","Casting");
			}
		}
		// Attack
		else {
			linkmon.getTarget().setDamageTaken(DamageFormula.damage(1, linkmon.getAttack(), linkmon.getTarget().getDefense(), linkmon.getCurrentMove().getDamage(), linkmon.getTarget().isDefending()));
		}
		
		linkmon.setEnergy(linkmon.getEnergy() + linkmon.getCurrentMove().getEnergy());
		if(linkmon.getTarget() != null)
			Gdx.app.log("SoloBattle", "Target Damage: " + linkmon.getTarget().getDamageTaken());
		
		Gdx.app.log("SoloBattle", "TURN ENDED");
	}

	@Override
	public int calculateDamage(BattleExtraComponent battleComponent) {
		// TODO Auto-generated method stub
		return DamageFormula.damage(1, battleComponent.getStats().getAttack(), ((BattleExtraComponent)battleComponent.getTarget().getExtraComponents()).getStats().getDefense(), battleComponent.getCurrentMove().getDamage(), ((BattleExtraComponent)battleComponent.getTarget().getExtraComponents()).isDefending());
	}

	@Override
	public void getTarget(LocalBattleLinkmon linkmon) {
		// TODO Auto-generated method stub
		// Player controlled target
		if(linkmon.isPlayerControlled()) {
			int targetAdd = 0;
			if(linkmon.getOwner().contentEquals("player")) {
				linkmon.setCurrentMove(MoveFactory.getMoveFromId(playerMove));
				linkmon.setTarget(battleLinkmons[playerTarget]);
				linkmon.setOpponentTargetId(playerTarget);
				Gdx.app.log("SoloBattle", "Player");
				
				if(((BattleExtraComponent)linkmons[playerTarget].getExtraComponents()).isDead()) {
					int target = (int) Math.floor(Math.random()*3+targetAdd);
					while(((BattleExtraComponent)linkmons[target].getExtraComponents()).isDead())
						target = (int) Math.floor(Math.random()*3+targetAdd);
					
					linkmon.setTarget(battleLinkmons[target]);
					linkmon.setOpponentTargetId(target);
				}
			}					
		}
		// AI target
		else {
			// Player AI
			if(linkmon.getOwner().contentEquals("player")) {
				BattleAI.runAI(linkmon, battleLinkmons, 0);
				Gdx.app.log("SoloBattle", "PlayerAI");
			}
			// Opponent AI
			else {
				BattleAI.runAI(linkmon, battleLinkmons, 3);
				Gdx.app.log("SoloBattle", "OpponentAI");
			}
		}
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setup(LocalBattleLinkmon[] myTeam, LocalBattleLinkmon[] oppTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameObject[] getLinkmonArray() {
		// TODO Auto-generated method stub
		return linkmons;
	}

	@Override
	public void setPlayerTarget(int target) {
		// TODO Auto-generated method stub
		if(linkmons[playerTarget] != null)
			((BattleLinkmonRenderingComponent)linkmons[playerTarget].getRenderer()).setTargeted(false);
		
		playerTarget = target;
		((BattleLinkmonRenderingComponent)linkmons[playerTarget].getRenderer()).setTargeted(true);
	}

	@Override
	public void setOpponentTarget(int target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayerMove(int move) {
		// TODO Auto-generated method stub
		playerMove = move;
	}

	@Override
	public void setOpponentMove(int move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEnergy(BattleExtraComponent battleComponent) {
		// TODO Auto-generated method stub
		battleComponent.updateEnergy(battleComponent.getCurrentMove().getEnergy());
	}
	
	private void checkHealth(BattleExtraComponent battleComponent) {
		
		// Component (gameObject) Health checks
		if(battleComponent.getStats().getHealth() > battleComponent.getStats().getMaxHealth())
			battleComponent.getStats().setHealth(battleComponent.getStats().getMaxHealth());
		
		else if(battleComponent.getStats().getHealth() < 0)
			battleComponent.getStats().setHealth(0);
		
		// BattleLinkmon Health Checks
		if(battleComponent.getBattleLinkmon().getHealth() > battleComponent.getBattleLinkmon().getMaxHealth())
			battleComponent.getBattleLinkmon().setHealth(battleComponent.getBattleLinkmon().getMaxHealth());
		
		else if(battleComponent.getBattleLinkmon().getHealth() < 1)
			battleComponent.getBattleLinkmon().setDead(true);
	}
	
	@Override
	public void updatePoison(BattleExtraComponent battleComponent) {
		if(battleComponent.getBattleLinkmon().isPoisoned()) {
			battleComponent.getBattleLinkmon().setHealth(battleComponent.getBattleLinkmon().getHealth() - battleComponent.getPoisonDamageTaken());
		}
		battleComponent.updatePoison();
		checkHealth(battleComponent);
	}
	
	@Override
	public void updateHealing(BattleExtraComponent battleComponent) {
		battleComponent.getBattleLinkmon().setHealth(battleComponent.getBattleLinkmon().getHealth() + battleComponent.getBattleLinkmon().getHealingTaken());
		battleComponent.updateHealing();
		checkHealth(battleComponent);
	}
	
	@Override
	public void updateDamage(BattleExtraComponent battleComponent) {
		battleComponent.getBattleLinkmon().setHealth(battleComponent.getBattleLinkmon().getHealth() - battleComponent.getBattleLinkmon().getDamageTaken());
		battleComponent.updateDamage();
		checkHealth(battleComponent);
	}

	@Override
	public void updateStatus(BattleExtraComponent battleComponent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addManager(BattleManager battle) {
		// TODO Auto-generated method stub
		battleManager = battle;
	}

	@Override
	public BattleManager getBattleManager() {
		return battleManager;
	}

}
