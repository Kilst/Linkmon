package com.linkmon.view.screens.widgets.messages;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.linkmon.RankIds;
import com.linkmon.view.UIRenderer;

public class RankUpMessage extends MessageBox {

	public Image rankImage;
	
	public RankUpMessage(int messageType, String messageString, int rank, UIRenderer worldRenderer, EventManager eManager) {
		super(messageType, messageString, worldRenderer, eManager);
		// TODO Auto-generated constructor stub

		this.setHeading("rankUpHeading");
		this.setText("Your Linkmons rank has increased!");
		
		rankImage = new Image(skin2.getDrawable("rank-s"));
		switch(rank) {
		case(RankIds.E) : {
			rankImage.setDrawable(skin2.getDrawable("rank-e"));
			break;
		}
		case(RankIds.D) : {
			rankImage.setDrawable(skin2.getDrawable("rank-d"));
			break;
		}
		case(RankIds.C) : {
			rankImage.setDrawable(skin2.getDrawable("rank-c"));
			break;
		}
		case(RankIds.B) : {
			rankImage.setDrawable(skin2.getDrawable("rank-b"));
			break;
		}
		case(RankIds.A) : {
			rankImage.setDrawable(skin2.getDrawable("rank-a"));
			break;
		}
		case(RankIds.S) : {
			rankImage.setDrawable(skin2.getDrawable("rank-s"));
			break;
		}
	}
		messageTable.row();
		messageTable.add(rankImage);
		messageTable.row();
		this.setOkay();
		addToUi();
	}
}
