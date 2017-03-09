package com.linkmon.view.screens.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.linkmon.Move;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.particles.ParticleFactory;
import com.linkmon.view.particles.ParticleIds;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.interfaces.IBattleView;
import com.linkmon.view.screens.localbattle.BattleStats;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.LocalMessageBox;
import com.linkmon.view.screens.widgets.messages.MessageTable;

public class OnlineBattleScreen implements Screen, IBattleView, ModelListener {
	
	private Image background;
	private Table container;
	private Group uiGroup;
	volatile private TextButton attack1;
	volatile private TextButton attack2;
	volatile private TextButton attack3;
	volatile private TextButton attack4;
	volatile private TextButton defend;
	private Skin skin;
	
	public int playerMoveType;
	public int opponentMoveType;
	
	
	private BattleStats oppTable;
	private BattleStats playerTable;
	public Table buttonTable;
	
	Label oppHealth;
	Label oppName;
	
	Label health;
	Label name;
	
	private EventManager eManager;
	private boolean finishedLoading = false;
	
	public int playerHealth;
	public int opponentHealth;
	
	private AnimationWidget myLinkmonSprite;
	private AnimationWidget oppLinkmonSprite;
	
	private MessageTable battleMessage;
	
	
	private int move1;
	private int move2;
	private int move3;
	private int move4;
	
	private int playerUpdatedHealth;
	private int playerUpdatedEnergy;
	
	private int opponentUpdatedHealth;
	private int opponentUpdatedEnergy;
	
	private MessageTable firstChat;
	private MessageTable secondChat;
	
	private boolean first = false;
	
	private boolean playerUpdated = false;
	private boolean opponentUpdated = false;
	
	private Skin skin2;
	private boolean updateButtonTable;
	
	private boolean updated = false;
	
	public OnlineBattleScreen(Group group, EventManager eManager) {
		this.eManager = eManager;
		eManager.addModelListener(this);
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
	}
	
	public void update() {
		eManager.notify(new ScreenEvent(ScreenEvents.UPDATE_ONLINE_BALLTE, this));
		if(updateButtonTable) {
			buttonTable.setVisible(true);
			updateButtonTable = false;
		}
		if(updated) {
			
			if(firstChat.isFinished()) {

				if(!first && !playerUpdated) {
					playerTable.updateHealth(playerUpdatedHealth);
					oppTable.updateEnergy(opponentUpdatedEnergy);
					playerUpdated = true;
					uiGroup.addActor(secondChat);
					eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleFactory.getParticleFromMoveType(opponentMoveType), myLinkmonSprite.getX()+(myLinkmonSprite.getWidth()/2), myLinkmonSprite.getY()+(myLinkmonSprite.getHeight()/2)));
				}
				else if(first && !opponentUpdated) {
					oppTable.updateHealth(opponentUpdatedHealth);
					playerTable.updateEnergy(playerUpdatedEnergy);
					opponentUpdated = true;
					uiGroup.addActor(secondChat);
					eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleFactory.getParticleFromMoveType(playerMoveType), oppLinkmonSprite.getX()+(oppLinkmonSprite.getWidth()/2), oppLinkmonSprite.getY()+(oppLinkmonSprite.getHeight()/2)));
				}
				
				if(secondChat.isFinished()) {
					if(!playerUpdated) {
						playerTable.updateHealth(playerUpdatedHealth);
						oppTable.updateEnergy(opponentUpdatedEnergy);
						playerUpdated = true;
						updated = false;
						buttonTable.setVisible(true);
						eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleFactory.getParticleFromMoveType(opponentMoveType), myLinkmonSprite.getX()+(myLinkmonSprite.getWidth()/2), myLinkmonSprite.getY()+(myLinkmonSprite.getHeight()/2)));
					}
					else if(!opponentUpdated) {
						oppTable.updateHealth(opponentUpdatedHealth);
						playerTable.updateEnergy(playerUpdatedEnergy);
						opponentUpdated = true;
						updated = false;
						buttonTable.setVisible(true);
						eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleFactory.getParticleFromMoveType(playerMoveType), oppLinkmonSprite.getX()+(oppLinkmonSprite.getWidth()/2), oppLinkmonSprite.getY()+(oppLinkmonSprite.getHeight()/2)));
					}
				}
			}
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		background = new Image(skin2.getDrawable("statsBackground"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		oppTable = new BattleStats();
		
		container.add(oppTable).expand().align(Align.bottomLeft);
		container.row();
		
		
		playerTable = new BattleStats();

		container.add(playerTable).expand().align(Align.bottomRight);
		container.row();
		
		buttonTable = new Table();
		buttonTable.setBackground(skin2.getDrawable("tableNoHeading"));
		
		battleMessage = new MessageTable(skin2);
		battleMessage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/5f);
		battleMessage.setVisible(false);
		
		attack1 = new TextButton("Attack1", buttonStyle);
		buttonTable.add(attack1).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		attack2 = new TextButton("Attack2", buttonStyle);
		buttonTable.add(attack2).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		attack3 = new TextButton("Attack3", buttonStyle);
		buttonTable.add(attack3).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		attack4 = new TextButton("Attack4", buttonStyle);
		buttonTable.add(attack4).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		
		defend = new TextButton("Defend", buttonStyle);
		buttonTable.add(defend).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		
		container.add(buttonTable).height(Gdx.graphics.getHeight()/5f).expandX().fillX();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_ONLINE_STATS, this));
		eManager.notify(new ScreenEvent(ScreenEvents.GET_ONLINE_SPRITES, this));
		
		uiGroup.addActor(background);
		uiGroup.addActor(oppLinkmonSprite);
		uiGroup.addActor(myLinkmonSprite);
		uiGroup.addActor(container);
		uiGroup.addActor(battleMessage);
		uiGroup.toFront();
		addListeners();
		finishedLoading  = true;
	}
	
	private void addListeners() {
		
		attack1.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	try {
		            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, move1));
		            	playerMoveType = move1;
		            	buttonTable.setVisible(false);
	            	}
	            	catch (Exception e){
	            		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            	}
	            }
			});
		
		attack2.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	
            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, move2));
            	playerMoveType = move2;
            	buttonTable.setVisible(false);
            }
		});
		
		attack3.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	
            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, move3));
            	playerMoveType = move3;
            	buttonTable.setVisible(false);
            }
		});
		
		attack4.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	
            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, move4));
            	playerMoveType = move4;
            	buttonTable.setVisible(false);
            }
		});
		
		defend.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	
            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, MoveIds.DEFEND));
            	buttonTable.setVisible(false);
            }
		});
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		background.remove();
		oppLinkmonSprite.remove();
		myLinkmonSprite.remove();
		container.remove();
		eManager.removeModelListener(this);
		firstChat.remove();
		secondChat.remove();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getSprites(int myLinkmonId, int opponentLinkmonId) {
		// TODO Auto-generated method stub
		myLinkmonSprite = new AnimationWidget(myLinkmonId, 2/76f);
		oppLinkmonSprite = new AnimationWidget(opponentLinkmonId, 2/76f);
		myLinkmonSprite.setPosition(Gdx.graphics.getWidth()-myLinkmonSprite.getWidth()-100, Gdx.graphics.getHeight()/5f);
		oppLinkmonSprite.setPosition(100, Gdx.graphics.getHeight()-myLinkmonSprite.getHeight());
	}

	@Override
	public void updateHealths(boolean first, int myMoveType, int myNewHealth, int myEnergy, int oppMoveType, int oppNewHealth, int oppEnergy, String[][] messages) {
		// TODO Auto-generated method stub
		
		playerMoveType = myMoveType;
		
		opponentMoveType = oppMoveType;
		
		playerUpdatedHealth = myNewHealth;
		playerUpdatedEnergy = myEnergy;
		
		opponentUpdatedHealth = oppNewHealth;
		opponentUpdatedEnergy = oppEnergy;
		
		if(messages[0] != null && messages[1] != null) {
			firstChat = new MessageTable(skin2);
			firstChat.setText("ATTACK", messages[0]);
			firstChat.setHeight(buttonTable.getHeight());
			secondChat = new MessageTable(skin2);
			secondChat.setText("ATTACK", messages[1]);
			secondChat.setHeight(buttonTable.getHeight());
			
			uiGroup.addActor(firstChat);
			
			updated = true;
			
			playerUpdated = false;
			opponentUpdated = false;
			
			this.first = first;
		}
	}

	@Override
	public void getMoves(int move1Id, String move1Name, int move2Id, String move2Name,
			int move3Id, String move3Name) {
		// TODO Auto-generated method stub
		this.move1 = move1Id;
		attack1.setText(move1Name);
		this.move2 = move2Id;
		attack2.setText(move2Name);
		this.move3 = move3Id;
		attack3.setText(move3Name);
	}

	@Override
	public void getStats(int myNewHealth, String name, int oppNewHealth, String opponentName) {
		// TODO Auto-generated method stub
		playerTable.setStats(name, myNewHealth);
		oppTable.setStats(opponentName, oppNewHealth);
		buttonTable.setVisible(true);
	}

	@Override
	public void battleEnded() {
		// TODO Auto-generated method stub
//		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.ONLINE_SCREEN));
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.NOT_ENOUGH_ENERGY): {
				updateButtonTable = true;
				uiGroup.addActor(new LocalMessageBox("Failed", "Not enough energy to use that move", uiGroup));
				break;
			}
		}
	}
}
