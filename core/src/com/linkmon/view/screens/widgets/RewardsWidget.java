package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.ObjectId;

public class RewardsWidget {

	LocalMessageBox messageBox;
	
	public RewardsWidget(Group ui, Skin skin, EventManager eManager, int trainingPoints) {
		
		eManager.notify(new ScreenEvent(ScreenEvents.PLAY_DING));
		
		messageBox = new LocalMessageBox("Daily Reward!", "Rewards:", ui);
		
		Table messagetable = messageBox.getMessageTable();
		
		LabelStyle style = new LabelStyle();
		style.font = ResourceLoader.getLutFont("medium");
		Table goldTable = new Table();
		goldTable.add(new Image(skin.getDrawable("coins"))).expand();
		goldTable.add(new Label(": 200g", style)).expand();
		messagetable.row();
		Table itemTable = new Table();
		itemTable.add(new Image(ResourceLoader.getItemRegionFromId(ObjectId.MEAT))).expand();
		itemTable.add(new Label(" x1", style)).expand();
		
		messagetable.row();
		
		messagetable.add(goldTable).expand();
		messagetable.row();
		messagetable.add(itemTable).expand();
		messagetable.row();
		messagetable.add(new Label("Training Points: " + trainingPoints, style)).expand();
		
	}
}
