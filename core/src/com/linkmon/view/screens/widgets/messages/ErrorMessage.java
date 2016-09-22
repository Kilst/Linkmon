package com.linkmon.view.screens.widgets.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.GameUi;
import com.linkmon.view.screens.ScreenType;

public class ErrorMessage extends MessageBox {
	
	private IMessageAction action;

	public ErrorMessage(int messageType, String messageString, UIRenderer worldRenderer, EventManager eManager) {
		super(messageType, messageString, worldRenderer, eManager);
		// TODO Auto-generated constructor stub
		this.setHeading("errorHeading");
		this.setText(messageString);
		
		this.setOkay();
		
		addToUi();
	}
	
	public ErrorMessage(IMessageAction action, int messageType, String messageString, UIRenderer worldRenderer, EventManager eManager) {
		super(messageType, messageString, worldRenderer, eManager);
		// TODO Auto-generated constructor stub
		
		this.action = action;
		this.setHeading("errorHeading");
		this.setText(messageString);
		
		this.setOkay();
		
		addToUi();
	}
	
	@Override
	public void setOkay() {
		okayButton = new ImageButton(skin2.getDrawable("okayButton"));
		this.add(okayButton).align(Align.right).size(okayButton.getWidth()*UIRenderer.scaleXY, okayButton.getHeight()*UIRenderer.scaleXY);
		this.row();
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	geteManager().notify(new MessageEvent(MessageEvents.CLEAR_CURRENT_MESSAGE, messageType));
            	worldRenderer.messageBox = null;
            	
            	
            	if(messageType == MessageType.NETWORK_MESSAGE)
            		geteManager().notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
		});
	}
}
