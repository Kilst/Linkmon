package com.linkmon.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.linkmon.componentcontroller.ControllerService;
import com.linkmon.componentcontroller.LibgdxInputController;
import com.linkmon.componentmodel.MService;
import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.linkmon.LinkmonInputComponent;
import com.linkmon.componentmodel.linkmon.LinkmonPhysicsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.componentmodel.linkmon.LinkmonTimerComponent;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.helpers.GameSave;
import com.linkmon.helpers.HelpMessages;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.TimerLengths;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.model.Player;
import com.linkmon.model.gameobject.linkmon.LinkmonTimerLengths;
import com.linkmon.view.LibgdxWorldRenderer;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.GameUi;
import com.linkmon.view.screens.IntroScreen;
import com.linkmon.view.screens.ScreenType;

public class GameClass extends Game implements ApplicationListener {
	SpriteBatch batch;
	Texture img;
	
	UIRenderer uiRenderer;
	
	GestureDetector gd;
	InputMultiplexer im;
	
	private EventManager eManager;
	
	private ControllerService controllerService;
	
	public MessageManager messages;
	
	private boolean saveLoaded = true;
	
	private HelpMessages help;
	
	private INotifications nHandler;
	
	private boolean notificationsSent = false;
	
	private LibgdxWorldRenderer libgdxWorldRenderer;
	
	private ControllerService service;
	
	public GameClass() {
		super();
	}
	
	public GameClass(INotifications nHandler) {
		super();
		// TODO Auto-generated constructor stub
		
		this.nHandler = nHandler;
	}

	@Override
	public void pause() {
//		GameSave.saveGame(controllerService.getPlayerController().getPlayer());
//		if(eManager != null)
//		eManager.notify(new ControllerEvent(ControllerEvents.SAVE_GAME));
//		if(!notificationsSent && controllerService != null) {
//			List<PushNotification> pushList = new ArrayList<PushNotification>();
//			pushList.add(NotificationCreator.getPushNotification(NotificationIds.POOP_NOTIFICATION, (LinkmonTimerLengths.POOP_SECONDS*TimerLengths.MILLI_FROM_SECOND) - (controllerService.getLinkmonController().getElapsedPoop()/TimerLengths.NANO_FROM_MILLI)));
//			pushList.add(NotificationCreator.getPushNotification(NotificationIds.HUNGRY_NOTIFICATION, (controllerService.getLinkmonController().getHungerLevel()*TimerLengths.MILLI_FROM_SECOND*LinkmonTimerLengths.HUNGER_SECONDS)));
//			pushList.add(NotificationCreator.getPushNotification(NotificationIds.DEAD_NOTIFICATION, 86400000000000L));
//			if(System.currentTimeMillis() - controllerService.getPlayerController().getGiftTime() < 1800000000000L);
//				pushList.add(NotificationCreator.getPushNotification(NotificationIds.MYSTERY_GIFT__NOTIFICATION, (controllerService.getPlayerController().getGiftTime()+1800000000000L)));
//			nHandler.sendNotification(pushList);
//			notificationsSent = true;
//			Gdx.app.log("NOTIFY", "Notification sent");
//		}
	}
	
	@Override
	public void resume() {
//		notificationsSent = false;
//		nHandler.clearNotification();
//		eManager.notify(new ControllerEvent(ControllerEvents.SAVE_GAME));
//		//GameSave.saveGame(player);
////		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.INTRO_SCREEN));
//		ResourceLoader.getInstance();
//		while (!ResourceLoader.assetManager.update())
//        {
//			
//        }
//		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	}
	
	@Override
	public void create () {
		
//		notificationsSent = false;
//		nHandler.clearNotification();
		
		eManager = new EventManager();
		
		
		
		batch = new SpriteBatch();
		img = new Texture("background.png");
		
		messages = new MessageManager(eManager);
		
		uiRenderer = new UIRenderer(messages, eManager);
		
		
		ResourceLoader.getInstance();
		while (!ResourceLoader.assetManager.update())
        {
			
        }
		System.gc();
		saveLoaded = true;
		
		
		service = new ControllerService(this, uiRenderer.ui, eManager);
		libgdxWorldRenderer = new LibgdxWorldRenderer(service.getWorldController().getWorld());
		
		im = new InputMultiplexer();
		
		im.addProcessor(service.getInputController().gd);
		
		im.addProcessor(uiRenderer.stage);
		Gdx.input.setInputProcessor(im);
		
		if(!saveLoaded) {
			this.setScreen(new IntroScreen(this, uiRenderer.ui, eManager));
			//startGame("Kilst", 1);
		}
		else {
//			controllerService = new ControllerService(this, world.ui, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), eManager);
//			world.addLinkmonToWorld(controllerService.getLinkmonController());
//			eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
			//loadGame(player);
			
		}
	}
	
	public void loadGame(Player player) {
//		controllerService = new ControllerService(player, this, world.ui, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), eManager);
//		world.addLinkmonToWorld(controllerService.getLinkmonController());
//		controllerService.getScreenController().screenUpdated = true;
	}
	
	public void startGame(String playerName, int eggChoice) {
//		help = new HelpMessages(eManager);
//		help.showIntroMessage();
//		controllerService = new ControllerService(playerName, eggChoice, this, world.ui, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), eManager);
//		world.addLinkmonToWorld(controllerService.getLinkmonController());
//		controllerService.getScreenController().screenUpdated = true;
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		service.update();
		
		batch.begin();
			libgdxWorldRenderer.render(batch);
		batch.end();

		uiRenderer.render();
		this.getScreen().render(0);
	}
}
