package com.linkmon.view.screens.widgets.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.items.ItemIds;
import com.linkmon.view.UIRenderer;

public class ItemMessage extends MessageBox {
	
	private Image item;
	
	private Button backbutton;
	private UIRenderer uiRenderer;

	public ItemMessage(int messageType, String messageString, UIRenderer uiRenderer, EventManager eManager) {
		super(messageType, messageString, uiRenderer, eManager);
		// TODO Auto-generated constructor stub
		
		this.uiRenderer = uiRenderer;
		
		this.setHeading("errorHeading");
		this.setText("Use a revive potion?");
		
		item = new Image(ResourceLoader.getItemRegionFromId(ItemIds.REVIVE_POTION));
		
		messageTable.row();
		messageTable.add(item);
		messageTable.row();
		this.setOkay();
		
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		
		backbutton = new ImageButton(back);
		this.add(backbutton).align(Align.right).size(backbutton.getWidth()*UIRenderer.scaleXY, backbutton.getHeight()*UIRenderer.scaleXY);
		
		backbutton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	geteManager().notify(new MessageEvent(MessageEvents.CLEAR_CURRENT_MESSAGE));
            	worldRenderer.messageBox = null;
            }
		});
		
		addToUi();
	}

}
