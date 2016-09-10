package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.battles.BattleLinkmon;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.networking.INetworkService;
import com.linkmon.networking.TcpService;
import com.linkmon.view.screens.interfaces.INetworkScreen;

public class NetworkController implements ScreenListener {
	
	private INetworkService service;
	private EventManager eManager;
	private Player player;
	
	private BattleLinkmon bLinkmon;
	
	public NetworkController(EventManager eManager, Player player) {
//		this.service = new NetworkService();
		this.eManager = eManager;
		this.player = player;
		eManager.addScreenListener(this);
		
		bLinkmon = new BattleLinkmon(player.getLinkmon());
	}
	
	private void connect() {

		if(service == null)
			service = new TcpService(eManager);
		service.connect();
	}
	
	private void searchOpponents() {
		Gdx.app.log("NetworkController", "Searching!");
		
		service.searchOpponent(bLinkmon);
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
	
	private void getServerWelcome(INetworkScreen screen) {
		screen.setServerWelcome(service.getClient().getServerMessage());
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.CONNECT_TO_SERVER): {
				connect();
				break;
			}
			case(ScreenEvents.SEARCH_FOR_OPPONENT): {
				searchOpponents();
				break;
			}
			case(ScreenEvents.SEND_MOVE): {
				sendMove(event.value);
				break;
			}
			case(ScreenEvents.CLOSE_CONNECTION): {
				closeConnection();
				break;
			}
			case(ScreenEvents.GET_MYSTERY_GIFT): {
				getMysteryGift();
				break;
			}
			case(ScreenEvents.CANCEL_SEARCH): {
				cancelSearch();
				break;
			}
			case(ScreenEvents.GET_SERVER_WELCOME): {
				getServerWelcome((INetworkScreen)event.screen);
				break;
			}
		}
		return false;
	}
}
