package com.linkmon.game;

import java.util.List;

public interface INotifications {
	
	void sendNotification(List<PushNotification> pushList);
	
	void clearNotification();
}
