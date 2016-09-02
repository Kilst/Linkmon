package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.model.Player;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.networking.NetworkService;
import com.linkmon.networking.Packet;
import com.linkmon.networking.PacketType;
import com.linkmon.view.screens.ScreenType;

public class NetworkController implements ControllerListener {
	
	NetworkService service;
	private EventManager eManager;
	Player player;
	
	public NetworkController(EventManager eManager, Player player) {
//		this.service = new NetworkService();
		this.eManager = eManager;
		eManager.addControllerListener(this);
		this.player = player;
		
	}
	
	private void connect() {

		if(service == null)
			service = new NetworkService(eManager);
		service.connect();
	}
	
	private void searchOpponents() {
		Gdx.app.log("NetworkController", "Searching!");
		player.createBattleLinkmon();
		service.searchOpponent(player.getBattleLinkmon());
	}
	
	private void cancelSearch() {
		Gdx.app.log("NetworkController", "Searching!");
		service.cancelSearch();
	}
	
	private void sendMove(int moveId) {
		service.sendMove(moveId);
	}
	
	private void closeConnection() {
		// TODO Auto-generated method stub
		if(service.getClient() != null) {
			service.getClient().disconnect("Connection closed!");
			service = null;
		}
	}
	
	private void getMysteryGift() {
		// TODO Auto-generated method stub
		service.getMysteryGift();
	}

	@Override
	public boolean onNotify(ControllerEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ControllerEvents.CONNECT_TO_SERVER): {
				connect();
				break;
			}
			case(ControllerEvents.SEARCH_FOR_OPPONENT): {
				searchOpponents();
				break;
			}
			case(ControllerEvents.SEND_MOVE): {
				sendMove(event.move.getId());
				break;
			}
			case(ControllerEvents.CLOSE_CONNECTION): {
				closeConnection();
				break;
			}
			case(ControllerEvents.GET_MYSTERY_GIFT): {
				getMysteryGift();
				break;
			}
			case(ControllerEvents.CANCEL_SEARCH): {
				cancelSearch();
				break;
			}
		}
		return false;
	}
}
