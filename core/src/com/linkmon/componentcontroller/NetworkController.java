package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.battles.BattleLinkmon;
import com.linkmon.componentmodel.battles.OnlineBattle;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.networking.INetworkService;
import com.linkmon.networking.TcpService;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.interfaces.IBattleView;
import com.linkmon.view.screens.interfaces.INetworkScreen;

public class NetworkController implements ScreenListener, NetworkListener {
	
	private INetworkService service;
	private EventManager eManager;
	private Player player;
	
	private BattleLinkmon bLinkmon;
	
	private OnlineBattle battle;
	
	public NetworkController(EventManager eManager, Player player) {
//		this.service = new NetworkService();
		this.eManager = eManager;
		this.player = player;
		eManager.addScreenListener(this);
		eManager.addNetworkListener(this);
	}
	
	public void update() {
		
	}
	
	private void connect() {

		if(service == null)
			service = new TcpService(eManager);
		service.connect();
	}
	
	private void searchOpponents() {
		Gdx.app.log("NetworkController", "Searching!");
		bLinkmon = new BattleLinkmon(player.getLinkmon());
		service.searchOpponent(bLinkmon);
	}
	
	private void cancelSearch() {
		Gdx.app.log("NetworkController", "Cancelling Searching!");
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
				if(player.checkGiftTime())
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
			case(ScreenEvents.GET_ONLINE_SPRITES): {
				((IBattleView)event.screen).getSprites(battle.getPlayer().getId(), player.getName(), battle.getOpponent().getId(), battle.getOpponentName());
				break;
			}
			case(ScreenEvents.UPDATE_ONLINE_BALLTE): {
				((IBattleView)event.screen).updateHealths(battle.getPlayer().getHealth(), battle.getOpponent().getHealth());
				break;
			}
		}
		return false;
	}
	
	@Override
	public boolean onNotify(NetworkEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(NetworkEvents.BATTLE_UPDATE): {
				battle.updateBattle(event.values[0], event.values[1], event.values[2], event.values[3], event.values[4],
						event.values[5], event.values[6], event.values[7], event.values[8], event.values[9]);
				break;
			}
			case(NetworkEvents.RECIEVE_GIFT): {
				player.receiveGift(event.value);
				break;
			}
			case(NetworkEvents.SET_OPPONENT): {
				battle = new OnlineBattle(bLinkmon, event.battleLinkmon, eManager);
				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.ONLINE_BATTLE_SCREEN));
				break;
			}
		}
		return false;
	}
}
