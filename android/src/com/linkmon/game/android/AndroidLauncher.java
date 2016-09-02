package com.linkmon.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.linkmon.game.GameClass;
import com.linkmon.game.INotify;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		NotificationHandler nHandler = new NotificationHandler(this);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GameClass((INotify)nHandler), config);
	}
}
