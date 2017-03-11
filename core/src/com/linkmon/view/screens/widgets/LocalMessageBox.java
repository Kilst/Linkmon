package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;

public class LocalMessageBox extends Table {
	
	public Table messageBox;
	
	public Table headingTable;
	public Table messageTable;
	
	public Table heading;
	public Label message;
	
	public Button okayButton;
	public Button cancelButton;
	
	public boolean choice;
	
	public String messageString;
	
	public float scale = 0.01f;
	
	public Image darken;
	
	public Skin skin;
	public Skin skin2;
	
	private Group gameUi;
	
	public LocalMessageBox(String titleText, String text, Group ui) {
		this.gameUi = ui;
		messageString = text;
		this.setTransform(true);
		messageBox = this;
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		ImageButtonStyle okayButtonStyle = new ImageButtonStyle();
		
		okayButtonStyle.down = skin2.getDrawable("okayButtonRed");
		okayButtonStyle.up = skin2.getDrawable("okayButtonGreen");
		
		this.setBackground(skin2.getDrawable("menuContainerPatch"));		
		this.setSize(640, 480);
		
		
		this.setPosition((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		this.setOrigin((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.85f);
		darken.setTouchable(Touchable.enabled);
		
		LabelStyle labelStyleLarge = new LabelStyle();
		labelStyleLarge.font = ResourceLoader.getLutFont("large");
		
		LabelStyle labelStyleMedium = new LabelStyle();
		labelStyleMedium.font = ResourceLoader.getLutFont("medium");
		
		heading = new Table();
		heading.setBackground(skin2.getDrawable("menuContainerPatch"));
		heading.setSize(290, 136);
		heading.setColor(0.5f, 0.5f, 0.7f, 1f);
		Label title = new Label(titleText, labelStyleLarge);
		heading.add(title).padRight(25).padLeft(25);
		
		messageTable = new Table();
		messageTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
		message = new Label(messageString, labelStyleMedium);
		message.setWrap(true);
		message.setAlignment(Align.center);
		messageTable.add(message).fill().width(640-80);
		
		
		this.add(heading).padTop(-50).padBottom(20);
		this.row();
		this.add(messageTable).expand().fill().padBottom(20);
		this.row();
		
		okayButton = new ImageButton(okayButtonStyle);
		this.add(okayButton).align(Align.right).size(okayButton.getWidth()*UIRenderer.scaleXY, okayButton.getHeight()*UIRenderer.scaleXY);
		this.row();
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            }
		});
		
		darken.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
		});
		
		gameUi.addActor(darken);
		gameUi.addActor(this);
	}
	
	public LocalMessageBox(boolean yesNoBox, String titleText, String text, Group ui) {
		this.gameUi = ui;
		messageString = text;
		this.setTransform(true);
		messageBox = this;
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		ImageButtonStyle okayButtonStyle = new ImageButtonStyle();
		
		okayButtonStyle.down = skin2.getDrawable("okayButtonRed");
		okayButtonStyle.up = skin2.getDrawable("okayButtonGreen");
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		
		this.setBackground(skin2.getDrawable("menuContainerPatch"));		
		this.setSize(640, 480);
		
		
		this.setPosition((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		this.setOrigin((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.85f);
		darken.setTouchable(Touchable.enabled);
		
		LabelStyle labelStyleLarge = new LabelStyle();
		labelStyleLarge.font = ResourceLoader.getLutFont("large");
		
		LabelStyle labelStyleMedium = new LabelStyle();
		labelStyleMedium.font = ResourceLoader.getLutFont("medium");
		
		heading = new Table();
		heading.setBackground(skin2.getDrawable("menuContainerPatch"));
		heading.setSize(290, 136);
		heading.setColor(0.5f, 0.5f, 0.7f, 1f);
		Label title = new Label(titleText, labelStyleLarge);
		heading.add(title).padRight(25).padLeft(25);
		
		messageTable = new Table();
		messageTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
		message = new Label(messageString, labelStyleMedium);
		message.setWrap(true);
		message.setAlignment(Align.center);
		messageTable.add(message).fill().width(640-80);
		
		
		this.add(heading).padTop(-50).padBottom(20).colspan(2);
		this.row();
		this.add(messageTable).expand().fill().padBottom(20).colspan(2);
		this.row();
		
		okayButton = new ImageButton(okayButtonStyle);
		this.add(okayButton).align(Align.left).size(okayButton.getWidth()*UIRenderer.scaleXY, okayButton.getHeight()*UIRenderer.scaleXY);
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	onOkay();
            }
		});
		
		cancelButton = new ImageButton(backButtonStyle);
		this.add(cancelButton).align(Align.right).size(cancelButton.getWidth()*UIRenderer.scaleXY, cancelButton.getHeight()*UIRenderer.scaleXY);
		this.row();
		
		cancelButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	
            	choice = false;
            }
		});
		
		darken.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
		});
		
		gameUi.addActor(darken);
		gameUi.addActor(this);
	}
	
	@Override
	public void act(float delta) {
		// Scale messageBox on show
		if(scale < 1)
			scale+=0.01+(0.5*scale);
		else
			scale = 1;
		
		if(scale > 1)
			scale = 1;
		
		this.setScale(scale);
	}
	
	public void onOkay() {
		
	}

	public Table getMessageTable() {
		// TODO Auto-generated method stub
		return messageTable;
	}

}
