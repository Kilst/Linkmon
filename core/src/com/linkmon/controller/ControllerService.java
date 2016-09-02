package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.game.GameClass;
import com.linkmon.model.ModelService;
import com.linkmon.model.Player;

public class ControllerService {
	
	private ModelService modelService;
	private PlayerController playerController;
	private LinkmonController linkmonController;
	private WorldController worldController;
	private ShopController shopController;
	private NetworkController networkController;
	private OnlineBattleController onlineBattleController;
	private ScreenController screenController;
	
	private EventManager eManager;

	public ControllerService(GameClass game, Group gameUi, float screenWidth, float screenHeight, EventManager eManager) {
		
		this.eManager = eManager;
		
		modelService = new ModelService(screenWidth, screenHeight, this.eManager);
		
		playerController = new PlayerController(modelService.getPlayer(), eManager);
		
		linkmonController = new LinkmonController(modelService.getPlayer().getLinkmon(), eManager);
		
		worldController = new WorldController(eManager, modelService.getWorld());
		
		shopController = new ShopController(eManager);
		
		onlineBattleController = new OnlineBattleController(modelService, eManager);
		
		networkController = new NetworkController(eManager, modelService.getPlayer());
		
		screenController = new ScreenController(game, linkmonController, playerController, shopController, onlineBattleController, gameUi, eManager);
	}
	
	public ControllerService(String playerName, int eggChoice, GameClass game, Group gameUi, float screenWidth, float screenHeight, EventManager eManager) {
		// TODO Auto-generated constructor stub
		
		this.eManager = eManager;
		
		modelService = new ModelService(playerName, eggChoice, screenWidth, screenHeight, this.eManager);
		
		playerController = new PlayerController(modelService.getPlayer(), eManager);
		
		linkmonController = new LinkmonController(modelService.getPlayer().getLinkmon(), eManager);
		
		worldController = new WorldController(eManager, modelService.getWorld());
		
		shopController = new ShopController(eManager);
		
		onlineBattleController = new OnlineBattleController(modelService, eManager);
		
		networkController = new NetworkController(eManager, modelService.getPlayer());
		
		screenController = new ScreenController(game, linkmonController, playerController, shopController, onlineBattleController, gameUi, eManager);
	}

	public ControllerService(Player player, GameClass game, Group gameUi, float screenWidth, float screenHeight, EventManager eManager) {
		// TODO Auto-generated constructor stub
		this.eManager = eManager;
		
		modelService = new ModelService(player, screenWidth, screenHeight, this.eManager);
		
		playerController = new PlayerController(modelService.getPlayer(), eManager);
		
		linkmonController = new LinkmonController(modelService.getPlayer().getLinkmon(), eManager);
		
		worldController = new WorldController(eManager, modelService.getWorld());
		
		shopController = new ShopController(eManager);
		
		onlineBattleController = new OnlineBattleController(modelService, eManager);
		
		networkController = new NetworkController(eManager, modelService.getPlayer());
		
		screenController = new ScreenController(game, linkmonController, playerController, shopController, onlineBattleController, gameUi, eManager);
	}

	public void newGame(GameClass game, Group gameUi, float screenWidth, float screenHeight, EventManager eManager) {
		
	}
	
	public void loadGame() {
		
	}
	
	public void update() {
		modelService.update();
		screenController.update();
		linkmonController.update();
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public LinkmonController getLinkmonController() {
		return linkmonController;
	}

	public NetworkController getNetworkController() {
		return networkController;
	}

	public OnlineBattleController getOnlineBattleController() {
		return onlineBattleController;
	}

	public ScreenController getScreenController() {
		return screenController;
	}
}
