package com.linkmon.view.screens.widgets.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.items.actions.IItemAction;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.ScreenType;

public class MessageBox extends Table {
	
public Table messageBox;
	
	public Table headingTable;
	public Table messageTable;
	
	public Table heading;
	public Label message;
	
	public Button okayButton;
	
	public String messageString;
	
	public float scale = 0;
	
	public Image darken;
	
	public Skin skin;
	public Skin skin2;
	
	private EventManager eManager;
	
	private int messageType;
	
	private IMessageAction action;
	
	public MessageBox(int messageType2, String titleText, String text, EventManager eManager2) {
		messageString = text;
		this.setTransform(true);
		messageBox = this;
		this.messageType = messageType2;
		this.eManager = eManager2;
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		
		
		LabelStyle labelStyle1 = new LabelStyle();
		labelStyle1.font = ResourceLoader.getLutFont("small");
		LabelStyle labelStyle2 = new LabelStyle();
		labelStyle2.font = ResourceLoader.getSampleFont("medium");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = ResourceLoader.getSampleFont("medium");
		
		this.setBackground(skin2.getDrawable("tableNoHeading"));
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setPosition(0, 0);
		this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.setSize((640/1.4f)*UIRenderer.scaleX, (480/1.4f)*UIRenderer.scaleX);
		
		
		this.setPosition((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		this.setOrigin((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.85f);
		
		heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		//heading.setSize(290*UIRenderer.scaleX, 136*UIRenderer.scaleX);
		Label title = new Label(titleText, labelStyle2);
		heading.add(title).padBottom(15);
		heading.pack();
		
		messageTable = new Table();
		messageTable.setBackground(skin2.getDrawable("tableNoHeading"));
		message = new Label(messageString , labelStyle1);
		messageTable.add(message).fill();
		
		
		this.add(heading).padTop((heading.getHeight()/2)*-1);
		this.row();
		this.add(messageTable).expand().fill();
		this.row();
		
		okayButton = new TextButton("Okay", buttonStyle);
		this.add(okayButton).align(Align.right);
		this.row();
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	
            	eManager.notify(new MessageEvent(MessageEvents.CLEAR_CURRENT_MESSAGE, messageType));
            	
            	removeAll();
            	
            	if(action != null)
            		action.doAction();
            }
		});	
	}
	
	public void addMessageAction(IMessageAction action) {
		this.action = action;
	}
	
	public void addToUI(Group gameUi) {
		gameUi.addActor(darken);
		gameUi.addActor(this);
		darken.toFront();
		this.toFront();
	}
	
	public void removeAll() {
		darken.remove();
		this.remove();
	}
	
	@Override
	public void act(float delta) {
		// Scale messageBox on show
		if(scale < 1)
			scale+=0.15;
		else
			scale = 1;
		
		this.setScale(scale);
	}
}
