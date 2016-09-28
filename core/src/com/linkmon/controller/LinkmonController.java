package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonMoveComponent;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.LinkmonStatusComponent;
import com.linkmon.model.linkmon.Move;
import com.linkmon.model.linkmon.MoveFactory;
import com.linkmon.model.linkmon.MoveSlot;
import com.linkmon.view.screens.DebuggingScreen;
import com.linkmon.view.screens.interfaces.ILinkmonAddedStats;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IMovesScreen;

public class LinkmonController implements ScreenListener {
	
	private GameObject linkmon;
	
	public LinkmonController(GameObject linkmon) {
		this.linkmon = linkmon;
	}
	
	private void train(int statType) {
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats().train(statType);
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().addExhaustionLevel(-10);
	}
	
	// Screen Updates
	
	public void getLinkmonAddedStats(ILinkmonAddedStats window) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		window.getAddedStats(
				stats.getAddedHealth(),
				stats.getAddedAttack(),
				stats.getAddedDefense(),
				stats.getAddedSpeed()
				);
	}
	
	public void getLinkmonMoves(IMovesScreen window) {
		LinkmonMoveComponent moves = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getMoves();
		
		// Basic
		window.setLinkmonMoves(moves.getBasicAttack().getId(), moves.getBasicAttack().getName(), moves.getBasicAttack().getType(), moves.getBasicAttack().getSlot(), moves.getBasicAttack().getDamage(),
				moves.getBasicAttack().getIgnoreDamage(), moves.getBasicAttack().getEnergy());
		// Mediums
		window.setLinkmonMoves(moves.getMediumAttack1().getId(), moves.getMediumAttack1().getName(), moves.getMediumAttack1().getType(), moves.getMediumAttack1().getSlot(), moves.getMediumAttack1().getDamage(),
				moves.getMediumAttack1().getIgnoreDamage(), moves.getMediumAttack1().getEnergy());
		
		window.setLinkmonMoves(moves.getMediumAttack2().getId(), moves.getMediumAttack2().getName(), moves.getMediumAttack2().getType(), moves.getMediumAttack2().getSlot(), moves.getMediumAttack2().getDamage(),
				moves.getMediumAttack2().getIgnoreDamage(), moves.getMediumAttack2().getEnergy());
		// Special
		window.setLinkmonMoves(moves.getSpecialAttack().getId(), moves.getSpecialAttack().getName(), moves.getSpecialAttack().getType(), moves.getSpecialAttack().getSlot(), moves.getSpecialAttack().getDamage(),
				moves.getSpecialAttack().getIgnoreDamage(), moves.getSpecialAttack().getEnergy());
	}
	
	public void getChoosableMoves(IMovesScreen window, int slot) {

		Gdx.app.log("LINKMONCONTROLLER", "Getting Moves");
		
		LinkmonMoveComponent moves = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getMoves();

		if(slot == MoveSlot.BASIC) {
			for(int i = 10; i < 17; i++) {
				Gdx.app.log("LINKMONCONTROLLER", "Getting Basic Moves");
				Move move = MoveFactory.getMoveFromId(i);
				if(move.getId() != moves.getBasicAttack().getId()) {
					window.setChoosableMoves(move.getId(), move.getName(), move.getType(), move.getSlot(), move.getDamage(),
							move.getIgnoreDamage(), move.getEnergy());
				}
			}
		}
		
		else if(slot == MoveSlot.MEDIUM) {
			for(int i = 101; i < 111; i++) {
				Gdx.app.log("LINKMONCONTROLLER", "Getting Medium Moves");
				Move move = MoveFactory.getMoveFromId(i);
				if(move.getId() != moves.getMediumAttack1().getId() && move.getId() != moves.getMediumAttack2().getId()) {
					window.setChoosableMoves(move.getId(), move.getName(), move.getType(), move.getSlot(), move.getDamage(),
							move.getIgnoreDamage(), move.getEnergy());
				}
			}
		}
		
		else if(slot == MoveSlot.SPECIAL) {
			for(int i = 201; i < 206; i++) {
				Gdx.app.log("LINKMONCONTROLLER", "Getting Special Moves");
				Move move = MoveFactory.getMoveFromId(i);
				if(move.getId() != moves.getSpecialAttack().getId()) {
					window.setChoosableMoves(move.getId(), move.getName(), move.getType(), move.getSlot(), move.getDamage(),
							move.getIgnoreDamage(), move.getEnergy());
				}
			}
		}
	}
	
	public void swapMove(int oldMove, int newMove) {
		LinkmonMoveComponent moves = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getMoves();
		moves.swapMove(oldMove, newMove);
	}
	
	public void getLinkmonStats(ILinkmonStats window) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		LinkmonStatusComponent status = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus();
		window.getLinkmonStats(
				linkmon.getId(),
				stats.getHealth(),
				stats.getAttack(),
				stats.getDefense(),
				stats.getSpeed(),
				status.getCareMistakes(),
				status.getBirthDate(),
				stats.getRank()
				);
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.TRAIN_LINKMON): {
				train(event.value);
				return false;
			}
			case(ScreenEvents.GET_LINKMON_STATS): {
				getLinkmonStats((ILinkmonStats) event.screen);
				return false;
			}
			case(ScreenEvents.GET_LINKMON_MOVES): {
				getLinkmonMoves((IMovesScreen) event.screen);
				return false;
			}
			case(ScreenEvents.SWAP_MOVE): {
				swapMove(event.value, event.value2);
				return false;
			}
			case(ScreenEvents.GET_CHOOSABLE_MOVES): {
				getChoosableMoves((IMovesScreen) event.screen, event.value);
				return false;
			}
			case(ScreenEvents.GET_LINKMON_ADDED_STATS): {
				getLinkmonAddedStats((ILinkmonAddedStats) event.screen);
				return false;
			}
			case(ScreenEvents.DEBUGGING): {
				((DebuggingScreen)event.screen).updateLinkmonPosition(linkmon.getX(), linkmon.getY());
				return false;
			}
		}
		return false;
	}
}
