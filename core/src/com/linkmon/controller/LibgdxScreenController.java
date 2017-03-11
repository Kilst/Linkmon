package com.linkmon.controller;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.game.GameClass;
import com.linkmon.view.screens.BattleTowerMenu;
import com.linkmon.view.screens.ConnectScreen;
import com.linkmon.view.screens.CryoScreen;
import com.linkmon.view.screens.DebuggingScreen;
import com.linkmon.view.screens.EvolveScreen;
import com.linkmon.view.screens.FeedWindow;
import com.linkmon.view.screens.GameUi;
import com.linkmon.view.screens.Item;
import com.linkmon.view.screens.ItemWindow;
import com.linkmon.view.screens.Menu;
import com.linkmon.view.screens.MenuScreen;
import com.linkmon.view.screens.MiniGameSelectScreen;
import com.linkmon.view.screens.Move;
import com.linkmon.view.screens.MoveSwap;
import com.linkmon.view.screens.MovesScreen;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.SettingsScreen;
import com.linkmon.view.screens.Shop;
import com.linkmon.view.screens.ShopWindow;
import com.linkmon.view.screens.StatsWindow;
import com.linkmon.view.screens.TrainWindow;
import com.linkmon.view.screens.localbattle.LocalBattleScreen;
import com.linkmon.view.screens.minigames.CoinRollUI;
import com.linkmon.view.screens.minigames.DuckRacingUI;
import com.linkmon.view.screens.minigames.MiniGameRewardScreen;
import com.linkmon.view.screens.networking.BattleIntroScreen;
import com.linkmon.view.screens.networking.OnlineBattleScreen;
import com.linkmon.view.screens.networking.OnlineScreen;
import com.linkmon.view.screens.newgame.IntroScreen;
import com.linkmon.view.screens.newgame.NewGameScreen;
import com.linkmon.view.screens.traingames.AttackGame;
import com.linkmon.view.screens.traingames.DefenseTrainWindow;
import com.linkmon.view.screens.traingames.SpeedTrainWindow;
import com.linkmon.view.screens.traingames.StatIncreaseWindow;

public class LibgdxScreenController implements ScreenListener {
	
	private GameClass game;
	private Group uiGroup;
	
	private Stack<Screen> screenStack; // Used so we can go back to the previous screen
	
	private EventManager eManager;
	
	private int screenType;
	public boolean screenUpdated = false;
	
	private GameUi gameUi;
	
	private int value;
	
	ControllerService service;
	
	public LibgdxScreenController(GameClass game, Group uiGroup, EventManager eManager, ControllerService service) {
		this.game = game;
		this.uiGroup = uiGroup;
		this.eManager = eManager;
		this.service = service;
		
		screenStack = new Stack<Screen>();
		
		screenStack.push(gameUi);
	}
	
	private void swapScreen(int screenType) {
		// Push every new screen to the stack, so we can just call swapScreen.previous event to go back
		switch(screenType) {
			case (ScreenType.SETTINGS) : {
				Screen screen = new SettingsScreen(uiGroup, eManager, service.getSettingsController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.LOCAL_BATTLE) : {
				Screen screen = new LocalBattleScreen(uiGroup, eManager, service.getLocalBattleController(), service.getPlayerController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.MINIGAME_SELECT_SCREEN) : {
				Screen screen = new MiniGameSelectScreen(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.BATTLE_TOWER) : {
				Screen screen = new BattleTowerMenu(uiGroup, eManager, service.getPlayerController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.DUCK_RACING_UI) : {
				Screen screen = new DuckRacingUI(eManager, uiGroup);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.COIN_ROLL_UI) : {
				Screen screen = new CoinRollUI(eManager, uiGroup);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.REWARD_SCREEN) : {
				Screen screen = new MiniGameRewardScreen(uiGroup, eManager, value);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.MENU) : {
				service.getWorldController().getGameWorld().setActive(false);
				Screen screen = new Menu(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.MOVE) : {
				Screen screen = new Move(uiGroup, eManager, service.getLinkmonController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.MOVE_SWAP) : {
				Screen screen = new MoveSwap(uiGroup, eManager, value);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.SHOP) : {
				Screen screen = new Shop(uiGroup, eManager, service.getPlayerController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.ITEM) : {
				Screen screen = new Item(uiGroup, eManager, service.getPlayerController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.INTRO_SCREEN) : {
				game.setScreen(new IntroScreen(game, uiGroup, eManager));
				break;
			}
			case (ScreenType.NEW_GAME_SCREEN) : {
				game.setScreen(new NewGameScreen(game, uiGroup, eManager));
				break;
			}
			case (ScreenType.MAIN_UI) : {
				// No buttons should actually call this
				// Though might not be a bad idea to do from Menu, as it will clear the stack
				service.getWorldController().getGameWorld().setActive(true); // If we are at the main_ui, the world is active
				gameUi = new GameUi(uiGroup, game, eManager, service.getPlayerController());
				screenStack.clear(); // Clear stack since we are at the base screen
				game.setScreen(gameUi);
				screenStack.push(gameUi);
				break;
			}
			case (ScreenType.FEED_WINDOW) : {
				Screen screen = new FeedWindow(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.SHOP_WINDOW) : {
				Screen screen = new ShopWindow(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
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
				Screen screen = new TrainWindow(uiGroup, eManager, service.getPlayerController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.STATS_WINDOW) : {
				Screen screen = new StatsWindow(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
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
				game.setScreen(new EvolveScreen(uiGroup, eManager));
				break;
			}
			case (ScreenType.DEBUGGING_SCREEN) : {
				game.setScreen(new DebuggingScreen(uiGroup, eManager));
				break;
			}
			case (ScreenType.ITEM_WINDOW) : {
				Screen screen = new ItemWindow(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.CRYO_SCREEN) : {
				Screen screen = new CryoScreen(uiGroup, eManager, service.getPlayerController(), service.getLinkmonController());
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.MOVES_SCREEN) : {
				Screen screen = new MovesScreen(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.MENU_SCREEN) : {
				Screen screen = new MenuScreen(uiGroup, eManager);
				game.setScreen(screen);
				screenStack.push(screen);
				break;
			}
			case (ScreenType.PREVIOUS) : {
				screenStack.pop(); // Pop current screen off stack
				Screen screen = screenStack.peek(); // Peek previous screen
				game.setScreen(screen); // Set previous screen
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
				screenType = event.value;
				screenUpdated = true;
				value = event.value2;
				Gdx.app.log("ScreenController", "onNotify");
				return false;
			}
		}
		return false;
	}
}
