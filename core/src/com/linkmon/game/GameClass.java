package com.linkmon.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.linkmon.controller.ControllerService;
import com.linkmon.controller.MiniGameController;
import com.linkmon.controller.SoundController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.HelpMessages;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.model.minigames.IMiniGame;
import com.linkmon.model.minigames.duckracing.DuckRacing;
import com.linkmon.view.LibgdxWorldRenderer;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.GameUi;
import com.linkmon.view.screens.LoadingScreen;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.minigames.DuckRacingUI;
import com.linkmon.view.screens.minigames.MiniGameUI;
import com.linkmon.view.screens.newgame.IntroScreen;

public class GameClass extends Game implements ApplicationListener, ScreenListener {
	SpriteBatch batch;
	
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
	
	MiniGameController game;
	
	private ControllerService service;
	
	public OrthographicCamera camera;
	public FitViewport viewport;
	
	private boolean gameLoaded = false;
	
	SoundController sound;
	
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
//		ResourceLoader.dispose();
		
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
//		while (!ResourceLoader.assetManager.update())
//        {
//			
//        }
	}
	
	@Override
	public void create () {
		
//		notificationsSent = false;
//		nHandler.clearNotification();
		
		eManager = new EventManager();
		
		eManager.addScreenListener(this);
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		viewport = new FitViewport(1280, 720, camera);
		viewport.apply();
		
		messages = new MessageManager(eManager);
		
		uiRenderer = new UIRenderer(messages, this, eManager);
		
		ResourceLoader.getInstance();
//		ResourceLoader.assetManager.load(ResourceLoader.UIAtlas, TextureAtlas.class);
//		ResourceLoader.assetManager.finishLoading();
		sound = new SoundController();
		sound.onNotify(new ScreenEvent(ScreenEvents.PLAY_THEME_MUSIC));
		this.setScreen(new LoadingScreen(this, uiRenderer.ui, eManager, sound));
		ResourceLoader.load();
//		while (!ResourceLoader.assetManager.update())
//        {
////			this.getScreen().render(Gdx.graphics.getDeltaTime());
//        }
		
		

	}
	
	public void startGame() {
		
		System.gc();
		saveLoaded = true;
		
		service = new ControllerService(this, uiRenderer.ui, eManager, sound);
		worldRenderer = new LibgdxWorldRenderer(service.getWorldController().getWorld());
		
		uiRenderer.addParticleLoader(service.getParticleController().getUILoader());
		worldRenderer.addParticleLoader(service.getParticleController().getWorldLoader());
		
		TopLayerInput input = new TopLayerInput(eManager, this);
		
		im = new InputMultiplexer();
		
		im.addProcessor(input.gd);
		im.addProcessor(uiRenderer.stage);
		im.addProcessor(service.getInputController().gd); // Controller added 2nd so ui clicks block world clicks
		
		Gdx.input.setInputProcessor(im);
		
		this.setScreen(new GameUi(uiRenderer.ui, this, eManager));
		
		gameLoaded = true;
	}

	@Override
	public void render () {
		
		camera.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		uiRenderer.stage.getBatch().setProjectionMatrix(camera.projection);
		batch.setProjectionMatrix(camera.combined);
		
		if(gameLoaded) {
			if(worldRenderer.getWorld() != service.getWorldController().getWorld())
				worldRenderer.setWorld(service.getWorldController().getWorld());
			
			service.update();
			
			batch.begin();
					worldRenderer.render(batch);
			batch.end();
			
			Gdx.app.log("Game", "Render calls: " + batch.renderCalls);
			
			GLProfiler.enable(); //Enable Profiling
			int binds = GLProfiler.textureBindings; // The amount of times a texture binding has happened since the last reset.
			Gdx.app.log("Game", "Texture Binds: " + binds);
			GLProfiler.reset(); // You must reset every frame in order to get frame-wise values
			
		}

		uiRenderer.render();
		
		this.getScreen().render(Gdx.graphics.getDeltaTime());
		
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		uiRenderer.resize(width, height);
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.OPEN_MINIGAME): {
//				game.setMiniGame(event.value);
//				miniGameRenderer = new LibgdxWorldRenderer(game.getWorld());
//				miniGameRenderer.addParticleLoader(service.getParticleController().getWorldLoader());
//				worldRenderer = miniGameRenderer;
				return false;
			}
			case(ScreenEvents.START_LOCAL_BATTLE): {
				
//				battleRenderer = new LibgdxWorldRenderer(service.getLocalBattleController().getWorld());
//				battleRenderer.addParticleLoader(service.getParticleController().getWorldLoader());
//				worldRenderer = battleRenderer;
				return false;
			}
			case(ScreenEvents.RETURN_TO_MAIN_GAME): {
//				miniGameRenderer = null;
//				worldRenderer = gameRenderer;
//				this.setScreen(new GameUi(uiRenderer.ui, this, eManager));
				return false;
			}
		}
		return false;
	}
}
