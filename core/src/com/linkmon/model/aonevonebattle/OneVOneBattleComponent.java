package com.linkmon.model.aonevonebattle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.model.aonevonebattle.moves.BeamAttackMoveAction;
import com.linkmon.model.aonevonebattle.moves.DefendMoveAction;
import com.linkmon.model.aonevonebattle.moves.FlameMoveAction;
import com.linkmon.model.aonevonebattle.moves.IMove;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.aonevonebattle.moves.OneTargetThrowObjectMoveAction;
import com.linkmon.model.aonevonebattle.moves.OneVMove;
import com.linkmon.model.aonevonebattle.moves.PhysicalMoveAction;
import com.linkmon.model.aonevonebattle.moves.ThrowObjectMoveAction;
import com.linkmon.model.aonevonebattle.moves.TrajectoryMoveAction;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonMoveComponent;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.Move;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;
import com.linkmon.view.particles.ParticleIds;

public class OneVOneBattleComponent implements IExtraComponents {
	
	private int id;
	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int rank;
	private String playerName;
	
	private int maxHealth;
	
	private int energy = 25;
	private int maxEnergy = 50;

	private IMove move1;
	private IMove move2;
	private IMove move3;
	private IMove defend;
	
	private IMove currentMove;
	
	private boolean isDefending = false;
	private boolean healing = false;
	
	private int damageTaken = 0;
	private int healingTaken = 0;
	
	private GameObject player;
	private GameObject opponent;
	
	EventManager eManager;
	
	private boolean missed = false;
	
	
	private boolean playingTurn;
	
	private boolean moveEnded = true;
	
	private List<GameObject> items;
	
	private OneVStatusComponent status;
	
	private List<String> effectAppliedMessages;
	private List<String> effectWornOffMessages;
	private Timer messageTimer;
	private boolean cycleMessages;
	private boolean cycleWornOffMessages;
	
	private boolean turnEnded;
	
	public OneVOneBattleComponent(GameObject linkmon, EventManager eManager) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		LinkmonMoveComponent moves = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getMoves();
		this.id = linkmon.getId();
		this.health = stats.getHealth();
		this.attack = stats.getAttack();
		this.defense = stats.getDefense();
		this.speed = stats.getSpeed();
		this.move1 = MoveFactory.getMoveFromId(moves.getMove1());
		this.move2 = MoveFactory.getMoveFromId(moves.getMove2());
		this.move3 = MoveFactory.getMoveFromId(moves.getMove3());
		this.defend = new OneVMove(MoveIds.DEFEND, 1, MoveType.DEFEND, 0, 10, "Defend", new DefendMoveAction(), null);
		this.rank = stats.getRank();
		this.playerName = "";
		this.eManager = eManager;
		
		maxHealth = stats.getHealth();
		
		items = new ArrayList<GameObject>();
		
		currentMove = move1;
		
		status = new OneVStatusComponent(this);
		
		effectAppliedMessages = new ArrayList<String>();
		
		effectWornOffMessages = new ArrayList<String>();
		
		messageTimer = new Timer(1, false);
		
		messageTimer.start();
	}
	
	public GameObject getPlayer() {
		return player;
	}
	
	public void setPlayer(GameObject object) {
		player = object;
	}
	
	public void setOpponent(GameObject object) {
		opponent = object;
	}
	
	public void updateHealth() {
		// Called at the end of most moves
		
		// Check if we set miss in the Battle precalculate method
		if(missed) {
			// Display missed message
			((OneVBattleRenderingComponent)player.getRenderer()).setDamage("Miss!");
			// Set damage to 0
			damageTaken = 0;
			// Reset missed here (was just going to move it to the start of the turn, but that would overwrite the precalculate)
			missed = false;
		}
		// If we took damage
		else if(damageTaken > 0) {
			// If we are defending(half damage) display brackets showing how much was defended (will always be the same number)
			if(isDefending)
				((OneVBattleRenderingComponent)player.getRenderer()).setDamage("-"+damageTaken+"("+damageTaken+")");
			else // Just display damage
				((OneVBattleRenderingComponent)player.getRenderer()).setDamage("-"+damageTaken);
			
			health -= damageTaken;// Remove health
			damageTaken = 0;// Reset damageTaken
			
			// Make sure health doesn't go negative
			if(health < 0)
				health = 0;
		}
	}
	
	public void updateHealing() {
		// Called at the end of some moves
		if(healingTaken > 0) {
			// Display health message
			((OneVBattleRenderingComponent)player.getRenderer()).setDamage("+"+healingTaken);
			health += healingTaken; // Add health
			healingTaken = 0; // Reset healingTaken
			
			// Check against maxHealth
			if(health > maxHealth)
				health = maxHealth;
		}
	}
	
	public void addEnergy(int energy) {
		// Display energy update text
		((OneVBattleRenderingComponent)player.getRenderer()).setDamage("+"+energy+ " Energy");
		// Add energy
		setEnergy(energy);
	}
	
	public void setDamage(int damage) {
		// Set via Battle in precalculate
		damageTaken = damage;
	}
	
	public boolean missCheck(float oppSpeed) {
		// Miss Check (Called from Battle precalculate)
		// 10% base chance, modified by speed/oppSpeed
		if(Math.random() <= 0.1f*(speed/oppSpeed)) {
			return true;
		}
		return false;
	}
	
	public void setMiss(boolean missed) {
		this.missed = missed;
	}

	public void playTurn() {
		// Called from Battle class once per round. Starts the move and resets variables
		isDefending = false;
		moveEnded = false; // bool to update the move each frame from this.update()
		turnEnded = false; // bool used to set things on the last frame of our turn in this.update() (currently setting opponent cycleMessages and Darkness particles)
		
		// Set defending since we just reset it (aesthetics only) [though.. I didn't think about how I set energy..]
		// Will need to precalculate energy too since I'm pretty sure they are on the moves.
		// I can probably get away with just comparing online vs local energy at some point in the round
		if(currentMove == defend)
			isDefending = true;
		
		// Use move. Major code behind each move. Physics, object creation, particles, timers etc
		// The moves set the pace of the battle
		currentMove.useMove(player, opponent);
	}

	public boolean isDefending() {
		return isDefending;
	}

	public void setDefending(boolean isDefending) {
		this.isDefending = isDefending;
	}
	
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	public int getAttack() {
		// TODO Auto-generated method stub
		return attack;
	}

	public int getDefense() {
		// TODO Auto-generated method stub
		return defense;
	}

	public int getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	public IMove getCurrentMove() {
		// TODO Auto-generated method stub
		return currentMove;
	}
	
	public IMove getMove1() {
		// TODO Auto-generated method stub
		return move1;
	}
	
	public IMove getMove2() {
		// TODO Auto-generated method stub
		return move2;
	}
	
	public IMove getMove3() {
		// TODO Auto-generated method stub
		return move3;
	}
	
	public boolean setCurrentMove(int choice) {
		// Choice is passed down from the UI, if we have enough energy the round will start
		// Else, it displays a messageBox to the UI and does nothing after that
		if(choice == 1)
			currentMove = move1;
		else if(choice == 2)
			currentMove = move2;
		else if (choice == 3) {
			currentMove = move3;
		}
		else if (choice == 4) {
			currentMove = defend;
		}
		
		return checkEnergy(currentMove.getEnergy());
	}
	
	public void setCurrentMove(OneVMove oneVMove) {
		// TODO Auto-generated method stub
		currentMove = oneVMove;
	}
	
	public boolean checkEnergy(int energy) {
		// TODO Auto-generated method stub
		if(this.energy + energy < 0)
			return false;
		else
			return true;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		if(playingTurn && !moveEnded) {
			// This is IMPORTANT, as it dictates if the turn ends, how long it lasts, sets particles, creates objects etc.
			// If the move doesn't complete, the game breaks and loops forever
			// Currently the moves generally complete via x and y target positions
			currentMove.update(object); // Updates the move used/created in playTurn(), it will set moveEnded to true once it has completed
			if(!getStatus().isDarkness())
				((OneVBattleRenderingComponent)this.getPlayer().getRenderer()).clearContinuousEffect();
		}
		else if(!turnEnded && moveEnded) { // Move ended
			// End of turn after this frame
			turnEnded = true;
			// Move has hit the target, the target can now display any effect messages
			((OneVOneBattleComponent)opponent.getExtraComponents()).setCycleMessages(true);
			// The Darkness particles were getting added at the start of the turn, so I've put them here for now until I can think of a better way
			if(((OneVOneBattleComponent)opponent.getExtraComponents()).getStatus().isDarkness())
				((OneVBattleRenderingComponent)opponent.getRenderer()).setContinuousParticleEffect(ParticleIds.DARKNESS, opponent.getX()+opponent.getHalfWidth(), opponent.getY()+opponent.getHalfHeight());
		}
		
		// Cycle through the messages any time we set cycleMessage/cycleWornOffMessages to true
		// Messages are cycled at the start of our turn, and the end of our opponents
		cycleMessages = cycleEffectMessages(effectAppliedMessages, cycleMessages);
		cycleWornOffMessages = cycleEffectMessages(effectWornOffMessages, cycleWornOffMessages);
	}
	
	public void addWornOffMessage(String message) {
		effectWornOffMessages.add(message);
	}
	
	public void setCycleWornOffMessages(boolean cycleWornOffMessages) {
		// TODO Auto-generated method stub
		this.cycleWornOffMessages = cycleWornOffMessages;
	}
	
	private boolean cycleEffectMessages(List<String> messages, boolean cycleMessages) {
		// This method is called each frame from the update() method
		
		// Check if we cycle through and display messages
		if(cycleMessages) {
			// Check if List isn't empty (otherwise nullpointer). Cycle through messages on a timer. Display in 1 second intervals.
			if(!messages.isEmpty() && messageTimer.checkTimer()) {
				// Set 1 message to render next frame, and then wait for the timer
				((OneVBattleRenderingComponent)player.getRenderer()).setMessages(messages.get(0));
				// Remove the message we just displayed.
				messages.remove(0);
				// Restart timer (the loop will run until it is empty, then set cycleMessage to false)
				messageTimer.restart();
				return true;
			}
			else
				return false; // Set cycleMessages to false (Stops looping through until we set it to true again)
		}
		return false; // cycleMessages is already false at this point
	}
	
	public void addEffectAppliedMessage(String message) {
		effectAppliedMessages.add(message);
	}
	
	public void setCycleMessages(boolean cycleMessages) {
		this.cycleMessages = cycleMessages;
	}

	public void setPlayingTurn(boolean value) {
		// TODO Auto-generated method stub
		playingTurn = value; 
	}

	public boolean isPlayingTurn() {
		// TODO Auto-generated method stub
		return playingTurn;
	}

	public int getEnergy() {
		// TODO Auto-generated method stub
		return energy;
	}

	public void setEnergy(int energy) {
		// TODO Auto-generated method stub
		this.energy += energy;
		if(this.energy > maxEnergy)
			this.energy = maxEnergy;
	}

	public boolean isMoveEnded() {
		return moveEnded;
	}

	public void setMoveEnded(boolean moveEnded) {
		this.moveEnded = moveEnded;
	}

	public void setHealing(int healAmount) {
		// TODO Auto-generated method stub
		healingTaken = healAmount;
		healing = true;
	}

	public OneVStatusComponent getStatus() {
		return status;
	}

	public boolean missed() {
		// TODO Auto-generated method stub
		return missed;
	}

	
}
