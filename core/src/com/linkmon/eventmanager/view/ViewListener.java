package com.linkmon.eventmanager.view;

import com.linkmon.eventmanager.GameListener;

public interface ViewListener extends GameListener {
	
	public void onNotify(ViewEvent event);
}
