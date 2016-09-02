package com.linkmon.game;

import java.util.List;

public interface INotify {
	
	void sendNotification(List<PushNotification> pushList);
	
	void clearNotification();
}
