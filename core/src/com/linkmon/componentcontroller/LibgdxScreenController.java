package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.game.GameClass;
import com.linkmon.view.screens.BattleIntroScreen;
import com.linkmon.view.screens.ConnectScreen;
import com.linkmon.view.screens.DebuggingScreen;
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

public class LibgdxScreenController implements ScreenListener {
	
	private GameClass game;
	private Group uiGroup;
	
	private EventManager eManager;
	
	private int previousScreenType = ScreenType.MAIN_UI;
	private int screenType;
	public boolean screenUpdated = false;
	
	private GameUi gameUi;
	
	public LibgdxScreenController(GameClass game, Group uiGroup, EventManager eManager) {
		this.game = game;
		this.uiGroup = uiGroup;
		this.eManager = eManager;	
		
		gameUi = new GameUi(uiGroup, game, eManager);
		
		game.setScreen(gameUi);
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
			case (ScreenType.FEED_WINDOW) : {
				game.setScreen(new FeedWindow(uiGroup, eManager));
				break;
			}
			case (ScreenType.SHOP_WINDOW) : {
				game.setScreen(new ShopWindow(uiGroup, eManager));
				break;
			}
			case (ScreenType.ONLINE_BATTLE_SCREEN) : {
				game.setScreen(new OnlineBattleScreen(uiGroup, eManager));
				break;
			}
			case (ScreenType.ONLINE_SCREEN) : {
				game.setScreen(new OnlineScreen(uiGroup, eManager));
				break;
			}
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
			case (ScreenType.STATS_WINDOW) : {
				game.setScreen(new StatsWindow(uiGroup, eManager));
				break;
			}
			case (ScreenType.STAT_INCREASE_SCREEN) : {
				game.setScreen(new StatIncreaseWindow(uiGroup, eManager));
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
			case (ScreenType.DEBUGGING_SCREEN) : {
				Gdx.app.log("ScreenController", "Evolve screen");
				game.setScreen(new DebuggingScreen(uiGroup, eManager));
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
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.SWAP_SCREEN): {
				previousScreenType = screenType;
				screenType = event.value;
				screenUpdated = true;
				return false;
			}
			case(ScreenEvents.SWAP_SCREEN_PREVIOUS): {
				screenType = previousScreenType;
				screenUpdated = true;
				return false;
			}
		}
		return false;
	}
}
