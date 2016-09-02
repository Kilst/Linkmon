package com.linkmon.messagesystem.messages;

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
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.GameUi;
import com.linkmon.view.screens.ScreenType;

public class ErrorMessage extends MessageBox {
	
	private boolean returnToMain;

	public ErrorMessage(String messageString, WorldRenderer worldRenderer, EventManager eManager, boolean returnToMain) {
		super(messageString, worldRenderer, eManager);
		// TODO Auto-generated constructor stub
		this.setHeading("errorHeading");
		this.setText(messageString);
		
		this.returnToMain = returnToMain;
		
		this.setOkay();
		
		addToUi();
	}
	
	@Override
	public void setOkay() {
		okayButton = new ImageButton(skin2.getDrawable("okayButton"));
		this.add(okayButton).align(Align.right).size(okayButton.getWidth()*WorldRenderer.scaleXY, okayButton.getHeight()*WorldRenderer.scaleXY);
		this.row();
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	if(returnToMain)
            		geteManager().notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            	geteManager().notify(new MessageEvent(MessageEvents.CLEAR_CURRENT_MESSAGE));
            	worldRenderer.messageBox = null;
            }
		});
	}
}
