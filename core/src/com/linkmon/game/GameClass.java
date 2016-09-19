package com.linkmon.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.linkmon.componentcontroller.ControllerService;
import com.linkmon.componentcontroller.MiniGameController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.HelpMessages;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.view.LibgdxWorldRenderer;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.minigames.MiniGameUI;

public class GameClass extends Game implements ApplicationListener, ScreenListener {
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
	
	private LibgdxWorldRenderer worldRenderer;
	private LibgdxWorldRenderer miniGameRenderer;
	
	MiniGameController game;
	
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
		service.saveGame();
//		service.close();
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
		
		System.gc();
		
		ResourceLoader.getInstance();
		while (!ResourceLoader.assetManager.update())
        {
			
        }
	}
	
	@Override
	public void create () {
		
//		notificationsSent = false;
//		nHandler.clearNotification();
		
		eManager = new EventManager();
		
		eManager.addScreenListener(this);
		
		batch = new SpriteBatch();
		img = new Texture("background.png");
		
		messages = new MessageManager(eManager);
		
		uiRenderer = new UIRenderer(messages, this, eManager);
		
		
		ResourceLoader.getInstance();
		while (!ResourceLoader.assetManager.update())
        {
			
        }
		System.gc();
		saveLoaded = true;
		
		game = new MiniGameController(eManager);
		service = new ControllerService(this, uiRenderer.ui, eManager);
//		MiniGameController miniGame = new MiniGameController(eManager);
		worldRenderer = new LibgdxWorldRenderer(service.getWorldController().getWorld());
		
		im = new InputMultiplexer();
		
		im.addProcessor(service.getInputController().gd);
		
		im.addProcessor(uiRenderer.stage);
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		service.update();
		
		batch.begin();
			if(miniGameRenderer != null) {
				miniGameRenderer.render(batch);
				game.update();
			}
			else
				worldRenderer.render(batch);
			
		batch.end();

		uiRenderer.render();
		this.getScreen().render(0);
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.START_MINIGAME): {
				miniGameRenderer = new LibgdxWorldRenderer(game.getWorld());
				this.setScreen(new MiniGameUI(eManager, uiRenderer.ui));
				return false;
			}
		}
		return false;
	}
}
