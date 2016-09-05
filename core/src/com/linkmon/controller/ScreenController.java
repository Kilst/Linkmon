package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.game.GameClass;
import com.linkmon.view.screens.BattleIntroScreen;
import com.linkmon.view.screens.ConnectScreen;
import com.linkmon.view.screens.DefenseTrainWindow;
import com.linkmon.view.screens.EvolveScreen;
import com.linkmon.view.screens.FeedWindow;
import com.linkmon.view.screens.GameUi;
import com.linkmon.view.screens.IntroScreen;
import com.linkmon.view.screens.NewGameScreen;
import com.linkmon.view.screens.OnlineBattleScreen;
import com.linkmon.view.screens.OnlineScreen;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.ShopWindow;
import com.linkmon.view.screens.SpeedTrainWindow;
import com.linkmon.view.screens.StatIncreaseWindow;
import com.linkmon.view.screens.StatsWindow;
import com.linkmon.view.screens.TrainWindow;
import com.linkmon.view.screens.interfaces.IBattleView;
import com.linkmon.view.screens.interfaces.ILinkmonAddedStats;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayableLinkmons;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.IShopItems;
import com.linkmon.view.screens.traingames.AttackGame;

public class ScreenController implements ControllerListener {
	
	private GameClass game;
	private Group uiGroup;
	
	private EventManager eManager;
	
	private int screenType;
	public boolean screenUpdated = false;
	
	private LinkmonController linkmonController;
	private PlayerController playerController;
	
	private OnlineBattleController onlineBattleController;
	
	private ShopController shopController;
	
	private GameUi gameUi;
	
	public ScreenController(GameClass game, LinkmonController linkmonController, PlayerController playerController, ShopController shopController, OnlineBattleController onlineBattleController, Group uiGroup, EventManager eManager) {
		this.game = game;
		this.uiGroup = uiGroup;
		this.eManager = eManager;
		eManager.addControllerListener(this);
		
		this.linkmonController = linkmonController;
		this.playerController = playerController;
		this.shopController = shopController;
		this.onlineBattleController = onlineBattleController;
		
		
	}
	
	private void swapScreen(int screenType) {
		switch(screenType) {
			case (ScreenType.INTRO_SCREEN) : {
				game.setScreen(new IntroScreen(game, uiGroup, eManager));
				break;
			}
			case (ScreenType.NEW_GAME_SCREEN) : {
				game.setScreen(new NewGameScreen(game, uiGroup, eManager));
				break;
			}
			case (ScreenType.MAIN_UI) : {
				if(gameUi == null)
					gameUi = new GameUi(uiGroup, game, eManager);
				game.setScreen(gameUi);
				break;
			}
//			case (ScreenType.FEED_WINDOW) : {
//				game.setScreen(new FeedWindow(uiGroup, this, eManager));
//				break;
//			}
//			case (ScreenType.SHOP_WINDOW) : {
//				game.setScreen(new ShopWindow(uiGroup, this, eManager));
//				break;
//			}
//			case (ScreenType.ONLINE_BATTLE_SCREEN) : {
//				game.setScreen(new OnlineBattleScreen(uiGroup, this, eManager));
//				break;
//			}
//			case (ScreenType.ONLINE_SCREEN) : {
//				game.setScreen(new OnlineScreen(uiGroup, this, eManager));
//				break;
//			}
			case (ScreenType.CONNECT_SCREEN) : {
				game.setScreen(new ConnectScreen(uiGroup, eManager));
				break;
			}
			case (ScreenType.TRAIN_WINDOW) : {
				game.setScreen(new TrainWindow(uiGroup, eManager));
				break;
			}
			case (ScreenType.TRAIN_ATTACK_SCREEN) : {
				game.setScreen(new AttackGame(uiGroup, eManager));
				break;
			}
			case (ScreenType.TRAIN_DEFENSE_SCREEN) : {
				game.setScreen(new DefenseTrainWindow(uiGroup, eManager));
				break;
			}
			case (ScreenType.TRAIN_SPEED_SCREEN) : {
				game.setScreen(new SpeedTrainWindow(uiGroup, eManager));
				break;
			}
//			case (ScreenType.STATS_WINDOW) : {
//				game.setScreen(new StatsWindow(uiGroup, this, eManager));
//				break;
//			}
			case (ScreenType.STAT_INCREASE_SCREEN) : {
				game.setScreen(new StatIncreaseWindow(uiGroup, this, linkmonController, eManager));
				break;
			}
			case (ScreenType.BATTLE_INTRO_SCREEN) : {
				game.setScreen(new BattleIntroScreen(uiGroup));
				break;
			}
			case (ScreenType.EVOLVE) : {
				Gdx.app.log("ScreenController", "Evolve screen");
				game.setScreen(new EvolveScreen(uiGroup, eManager));
				break;
			}
		}
	}
	
	public void update() {
		if(screenUpdated) {
			swapScreen(screenType);
			screenUpdated = false;
		}
	}

	@Override
	public boolean onNotify(ControllerEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ControllerEvents.SWAP_SCREEN): {
				if(screenType != event.screen) {
					screenType = event.screen;
					screenUpdated = true;
				}
				break;
			}
			
			case(ControllerEvents.UPDATE_LOAD): {
				updateLoad();
				break;
			}
		}
		return false;
	}

	public void updateOnlineBattleScreen(OnlineBattleScreen onlineBattleScreen) {
		// TODO Auto-generated method stub
		onlineBattleController.updateBattleScreen(onlineBattleScreen);
	}
	
	public void updateLoad() {
		linkmonController.updateLoad();
		playerController.updateLoad();
	}

	public void updateWindow(Screen window) {
		// TODO Auto-generated method stub
		
		if(window instanceof ILinkmonAddedStats) {
			((ILinkmonAddedStats)window).getAddedStats(
						linkmonController.getStats().getAddedHealth(),
						linkmonController.getStats().getAddedAttack(),
						linkmonController.getStats().getAddedDefense(),
						linkmonController.getStats().getAddedSpeed()
					);
		}
		
		if(window instanceof IPlayerStats) {
			((IPlayerStats)window).getPlayerStats(playerController.getName(), playerController.getGold());
		}
					
		if(window instanceof IShopItems)
			((IShopItems)window).getShopItems(shopController.getItems());
		if(window instanceof IPlayerItems)
			((IPlayerItems)window).getPlayerItems(playerController.getItems());
		
		if(window instanceof IPlayableLinkmons)
			((IPlayableLinkmons)window).updatePlayableLinkmons(playerController.getBattleLinkmon(), playerController.getSavedLinkmon());
		
		if(window instanceof IBattleView) {
			((IBattleView)window).getSprites(linkmonController.getId(), onlineBattleController.getOpponentId());
			((IBattleView)window).getMoves(linkmonController.getMove1(), linkmonController.getMove2());
		}
	}
}
