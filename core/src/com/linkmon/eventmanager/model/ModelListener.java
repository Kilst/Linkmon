package com.linkmon.eventmanager.model;

import com.linkmon.eventmanager.GameListener;

public interface ModelListener extends GameListener {
	
	public void onNotify(ModelEvent event);
}
